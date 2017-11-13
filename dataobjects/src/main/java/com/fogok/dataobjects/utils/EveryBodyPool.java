package com.fogok.dataobjects.utils;

import com.fogok.dataobjects.GameObject;
import com.fogok.dataobjects.GameObjectsType;
import com.fogok.dataobjects.gameobjects.ships.SimpleShipObject;
import com.fogok.dataobjects.gameobjects.weapons.SimpleBlusterObject;
import com.fogok.dataobjects.utils.libgdxexternals.Array;

public class EveryBodyPool extends Pool<GameObject> {

    private final Array<Array<GameObject>> typedObjects;
    /*

    typedObjects.get(type.ordinal()).add(responseGameObject);
    где Первый get выдаёт нам ArrayList с объектами, которые относятся
    только к определённому типу

    а дальше мы можем с ними уже работать
    * */

//    private IntArray objectsCount;
    private int typeObjectsCount;    //количество типов, в которых количество объектов не равно 0


    //двумерный массив, как работает рассказываю на примере:
//    private Array<IntArray> clientServerObjectsCount;     //oldRealization
    //двумерный массив, как работает рассказываю на примере:
    /*

    clientServerObjectCount.get(type.ordinal()).set($0/1$, count);
    где Первый get выдаёт нам IntArray с объектами, которые относятся
    только к определённому типу

    $0/1$ = тут мы можем указать количество серверных/клиентских объектов
    0 - количество серверных объектов
    1 - количество клиентских объектов

    * */

    public EveryBodyPool(int initialCapacity) {
        super(initialCapacity);
        typedObjects = new Array<Array<GameObject>>(false, initialCapacity);
        for (int i = 0; i < initialCapacity; i++) {
            //TODO: increment new arrays
            typedObjects.add(new Array<GameObject>(false, initialCapacity));
        }

//        objectsCount = new IntArray(initialCapacity);

//        clientServerObjectsCount = new Array<IntArray>(false, initialCapacity);
//        for (int i = 0; i < initialCapacity; i++) {
//            clientServerObjectsCount.add(new IntArray(false, 2));
//            clientServerObjectsCount.peek().add(0);
//            clientServerObjectsCount.peek().add(0);
//        }

    }

    @Override
    protected GameObject newObject() {
        return null;
    }

    protected GameObject newObject(com.fogok.dataobjects.GameObjectsType type){
        switch (type) {
            case SimpleBluster:
                return new SimpleBlusterObject();
            case SimpleShip:
                return new SimpleShipObject();
        }
        return null;
    }

    public GameObject obtain(com.fogok.dataobjects.GameObjectsType type){

        GameObject responseGameObject = null;
        boolean needNew = true;
        for (int i = 0; i < freeObjects.size; i++) {
            GameObject gameObject = freeObjects.get(i);
            if (type.ordinal() == gameObject.getType()) {
                freeObjects.removeIndex(i);
                responseGameObject = gameObject;
                needNew = false;
                break;
            }
        }
        if (needNew)
            responseGameObject = newObject(type);

        responseGameObject.setInsideField(false);

//        clientServerObjectsCount.get(type.ordinal()).incr(isServer ? 0 : 1, 1);
//        objectsCount.incr(type.ordinal(), 1);
        if (typedObjects.get(type.ordinal()).size == 0)
            typeObjectsCount++;
        typedObjects.get(type.ordinal()).add(responseGameObject);

        return responseGameObject;

    }

    public void free(GameObjectsType gameObjectsType) {
        free(typedObjects.get(gameObjectsType.ordinal()).peek());
    }

    @Override
    public void free(GameObject object) {
        super.free(object);
        object.setInsideField(true);
        if (typedObjects.get(object.getType()).removeValue(object, false)){
//            objectsCount.incr(object.getType(), -1);
            if (typedObjects.get(object.getType()).size == 0)
                typeObjectsCount--;
        }else{
            throw new UnsupportedOperationException("Type object: " + com.fogok.dataobjects.GameObjectsType.values()[object.getType()].name() + " has not be removed");
        }
    }

    public Array<GameObject> getAllObjectsFromType(com.fogok.dataobjects.GameObjectsType type){
        return typedObjects.get(type.ordinal());
    }

    public Array<Array<GameObject>> getAllObjects() {
        return typedObjects;
    }

//    public int getClientServerObjectsCount(com.fogok.dataobjects.GameObjectsType type, boolean isServer){
//        return clientServerObjectsCount.get(type.ordinal()).get(isServer ? 0 : 1);
//    }

    public Array<GameObject> getFreeEveryBodies(){
        return freeObjects;
    }

    public int getTypeObjectsCount() {
        return typeObjectsCount;
    }

    @Override
    public void clear() {
        super.clear();
        for (int i = 0; i < typedObjects.size; i++) {
            typedObjects.get(i).clear();
            typedObjects.set(i, null);
        }
//        for (int i = 0; i < clientServerObjectsCount.size; i++) {
//            clientServerObjectsCount.get(i).clear();
//            clientServerObjectsCount.set(i, null);
//        }
        typedObjects.clear();

//        clientServerObjectsCount.clear();
//        clientServerObjectsCount = null;
        //TODO: GC optimize
    }

    private StringBuilder stringBuilder = new StringBuilder(1000);

    @Override
    public String toString() {
        return toString(false);
    }

    public String toString(boolean modern) {

        stringBuilder.setLength(0);
        stringBuilder.append(JSONElements[0]);
        int typesIters = 0, objectsIters = 0;
        for (int k = 0; k < typedObjects.size; k++) {   //проходимся по всем типам объектов
            Array<GameObject> typedGameObjects = typedObjects.get(k);
            if (typedGameObjects.size != 0){    //если в массиве типов есть >=1 элемента, то проходимся по им всем
                addEndJSONString(false, typesIters++ == 0);    //если не первый объект, ставим впереди запятую
                addStartJSONString(k, true);
                stringBuilder.append(JSONElements[6]);
                objectsIters = 0;
                for (int q = 0; q < typedGameObjects.size; q++) {   //проходимся по всем объектам, которые касаются определённого типа
                    GameObject gameObject = typedGameObjects.get(q);

                        addEndJSONString(false, objectsIters++ == 0);
                        stringBuilder.append(JSONElements[0]);
                        long gameObjectLongFlags = gameObject.getLongFlags();
                        for (int i = 0; i < JSONStrings.length; i++) {          //проходимся по параметрам объекта
                            if (!(i == GameObject.ADIITPRMS && gameObject.getAdditParams().length == 0) && !(i == GameObject.BOOLEANS && gameObjectLongFlags == 0)){     //не добавляем лишнего, если данных нет
                                addEndJSONString(false, i == 0);
                                addStartJSONString(i, false);
                                switch (i) {
                                    case GameObject.X:
                                        stringBuilder.append(gameObject.getX());
                                        break;
                                    case GameObject.Y:
                                        stringBuilder.append(gameObject.getY());
                                        break;
                                    case GameObject.ADIITPRMS:
                                        stringBuilder.append(JSONElements[6]);
                                        float[] addPrms = gameObject.getAdditParams();
                                        for (int j = 0; j < addPrms.length; j++) {
                                            stringBuilder.append(addPrms[j]);
                                            addEndJSONString(false, j == addPrms.length - 1);
                                        }
                                        stringBuilder.append(JSONElements[7]);
                                        break;
                                    case GameObject.BOOLEANS:
                                        stringBuilder.append(gameObjectLongFlags);
                                        break;
                                }
                            }
                        }
                        stringBuilder.append(JSONElements[1]);

                }
                stringBuilder.append(JSONElements[7]);
            }
        }
        stringBuilder.append(JSONElements[1]);
        return modern ? format(stringBuilder.toString()) : stringBuilder.toString();
    }

    private void addStartJSONString(int i, boolean isNumberIsNameParam){
        stringBuilder.append(JSONElements[3]);
        stringBuilder.append(isNumberIsNameParam ? i : JSONStrings[i]);
        stringBuilder.append(JSONElements[3]);
        stringBuilder.append(JSONElements[2]);
    }

    private void addEndJSONString(boolean enableScobe, boolean last){
        if (enableScobe)
            stringBuilder.append(JSONElements[3]);
        if (!last)
            stringBuilder.append(JSONElements[4]);
    }

    private static final char[] JSONElements = new char[]{
            //  0    1    2    3    4    5    6     7
            '{', '}', ':', '"', ',', 'N', '[', ']'
    };

    public static final String[] JSONStrings = new String[]{
            //0    1    2    3
            "x", "y", "a", "b"
            //type; x;  y; additPrms; booleans
    };

    public static String format(String json){
        try {
            int empty=0;
            char[]chs=json.toCharArray();
            StringBuilder stringBuilder=new StringBuilder();
            for (int i = 0; i < chs.length;) {
                if (chs[i]=='\"') {

                    stringBuilder.append(chs[i]);
                    i++;
                    for ( ; i < chs.length;) {
                        if ( chs[i]=='\"'&&isDoubleSerialBackslash(chs,i-1)) {
                            stringBuilder.append(chs[i]);
                            i++;
                            break;
                        } else{
                            stringBuilder.append(chs[i]);
                            i++;
                        }

                    }
                }else if (chs[i]==',') {
                    stringBuilder.append(',').append('\n').append(getEmpty(empty));

                    i++;
                }else if (chs[i]=='{'||chs[i]=='[') {
                    empty++;
                    stringBuilder.append(chs[i]).append('\n').append(getEmpty(empty));

                    i++;
                }else if (chs[i]=='}'||chs[i]==']') {
                    empty--;
                    stringBuilder.append('\n').append(getEmpty(empty)).append(chs[i]);

                    i++;
                }else {
                    stringBuilder.append(chs[i]);
                    i++;
                }


            }



            return stringBuilder.toString();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return json;
        }

    }
    private static boolean isDoubleSerialBackslash(char[] chs, int i) {
        int count=0;
        for (int j = i; j >-1; j--) {
            if (chs[j]=='\\') {
                count++;
            }else{
                return count%2==0;
            }
        }

        return count%2==0;
    }
    /**
     * 缩进
     * @param count
     * @return
     */
    private static final String empty="    ";

    private static String getEmpty(int count){
        StringBuilder stringBuilder=new StringBuilder();
        for (int i = 0; i < count; i++) {
            stringBuilder.append(empty) ;
        }

        return stringBuilder.toString();
    }

}
