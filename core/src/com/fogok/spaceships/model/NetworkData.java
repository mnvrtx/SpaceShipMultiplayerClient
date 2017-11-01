package com.fogok.spaceships.model;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.fogok.spaceships.model.game.dataobjects.GameObject;

public class NetworkData {

    private StringBuilder stringBuilder = new StringBuilder(100);
//    private Array<GameObject> gameObjects = new Array<GameObject>(false, 100);

    private Array<Array<GameObject>> typedObjects;


    private NetworkDataResponse networkDataResponse = new NetworkDataResponse();

    private static final char[] JSONElements = new char[]{
     //  0    1    2    3    4    5    6     7
        '{', '}', ':', '"', ',', 'N', '[', ']'
    };

    public static final String[] JSONStrings = new String[]{
            //0    1    2    3
            "x", "y", "a", "b"
            //type; x;  y; additPrms; booleans
    };

    public void setTypedObjets(Array<Array<GameObject>> typedObjects) {
        this.typedObjects = typedObjects;

    }

    /**
     * Структура JSON
     * [
     * {
     *
     *      "1":[
     *              {"x":10.0,"y":5.62,"a":[0.0,0.0,1.4]}
     *      ]
     *
     * },
     * {
     *
     *      "1":[
     *              {"x":10.0,"y":5.62,"a":[0.0,0.0,1.4]}
     *      ]
     *
     * }
     * ]
     *
     *
     *
     * @return
     */
    public String getJSON(){
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
                    if (!gameObject.isServer()) {     //если объект не серверный, а непосредственно наш
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
                }
                stringBuilder.append(JSONElements[7]);
            }
        }
        stringBuilder.append(JSONElements[1]);
        return stringBuilder.toString();
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

    public void refreshOtherDatas(String json) {
        networkDataResponse.handle(json);
    }

    public boolean isResponseNormal(){
        return networkDataResponse.isResponseNormal();
    }

    public JsonValue getResponseJson(){
        return networkDataResponse.getJsonResponse();
    }

    public JsonValue getOldJsonResponse(){
        return networkDataResponse.getOldJsonResponse();
    }


//    public static void main(String[] args) {
//        NetworkData networkData = new NetworkData();
//        GameObject gameObject = new GameObject() {
//            @Override
//            public void reset() {
//
//            }
//        };
//        GameObject gameObject2 = new GameObject() {
//            @Override
//            public void reset() {
//
//            }
//        };
//
//        networkData.typedObjects = new Array<Array<GameObject>>(false, 100);
//        for (int i = 0; i < 100; i++) {
//            networkData.typedObjects.add(new Array<GameObject>(false, 100));
//        }
//
//        gameObject.setType(GameObjectsType.SimpleBluster);
//        gameObject.setX(0.3f);
//        gameObject.setY(0.256f);
//        gameObject.setFlags(0, false);
//        gameObject.setFlags(1, true);
//        gameObject.setFlags(2, true);
//        gameObject.setFlags(3, false);
//
//        networkData.typedObjects.get(GameObjectsType.SimpleBluster.ordinal()).add(gameObject);
//        networkData.typedObjects.get(GameObjectsType.SimpleBluster.ordinal()).add(gameObject);
//        networkData.typedObjects.get(GameObjectsType.SimpleBluster.ordinal()).add(gameObject);
//
//        gameObject2.setType(GameObjectsType.SimpleShip);
//        gameObject2.setPosition(5.6f, 0.12f);
//        gameObject2.initAdditParams(3);
//        gameObject2.setAdditParam(46f, ShipObjectBase.AdditParams.DIRECTION);
//        gameObject2.setAdditParam(1.4f, ShipObjectBase.AdditParams.SIZE);
//        gameObject2.setAdditParam(0.3f, ShipObjectBase.AdditParams.SIZE);
//        gameObject2.setFlags(0, true);
//        gameObject2.setFlags(1, false);
//        gameObject2.setFlags(2, true);
//        gameObject2.setFlags(3, true);
//
//        networkData.typedObjects.get(GameObjectsType.SimpleShip.ordinal()).add(gameObject2);
//        networkData.typedObjects.get(GameObjectsType.SimpleShip.ordinal()).add(gameObject2);
//        networkData.typedObjects.get(GameObjectsType.SimpleShip.ordinal()).add(gameObject2);
//        networkData.typedObjects.get(GameObjectsType.SimpleShip.ordinal()).add(gameObject2);
//        networkData.typedObjects.get(GameObjectsType.SimpleShip.ordinal()).add(gameObject2);
//        networkData.typedObjects.get(GameObjectsType.SimpleShip.ordinal()).add(gameObject2);
//
//
//        System.out.println(networkData.getJSON());
//        System.out.println(DebugGUI.jsonReader.parse(networkData.getJSON()).prettyPrint(JsonWriter.OutputType.json, 2));
//    }

}
