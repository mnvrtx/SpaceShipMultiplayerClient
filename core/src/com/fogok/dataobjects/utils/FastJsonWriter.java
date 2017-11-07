package com.fogok.dataobjects.utils;

import com.fogok.dataobjects.GameObject;
import com.fogok.dataobjects.utils.libgdxexternals.Array;

/**
 * Created by FOGOK on 11/2/2017 9:44 PM.
 */

public class FastJsonWriter {

    private Array<Array<GameObject>> typedObjects;

    private StringBuilder stringBuilder = new StringBuilder(3000);

    public void setTypedObjects(Array<Array<GameObject>> typedObjects) {
        this.typedObjects = typedObjects;
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

    /**
     * Структура JSON
     * [
     * { игрок (этот метод возвращает конкретно один такой вот элемент)
     *
     *      "1":[   //массив всех объектов этого типа (1 - значит ordinal enum типа в классе GameObjectsType
     *              {"x":10.0,"y":5.62,"a":[0.0,0.0,1.4]}
     *      ]
     *
     * },
     * { игрок
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
}
