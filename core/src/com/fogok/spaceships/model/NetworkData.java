package com.fogok.spaceships.model;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.fogok.spaceships.model.game.dataobjects.GameObject;

public class NetworkData {

    private StringBuilder stringBuilder = new StringBuilder(100);
    private Array<GameObject> gameObjects = new Array<GameObject>(false, 100);
    private NetworkDataResponse networkDataResponse = new NetworkDataResponse();

    private static final char[] JSONElements = new char[]{
     //  0    1    2    3    4    5    6     7
        '{', '}', ':', '"', ',', 'N', '[', ']'
    };

    private static final String[] JSONStrings = new String[]{
            //0   1    2    3    4
            "t", "x", "y", "a", "b"
            //type; x;  y; additPrms; booleans
    };


    public void addObject(GameObject gameObject){
        gameObjects.add(gameObject);
    }

    public void resetSize(){
        gameObjects.setSize(0);
    }

    public String getJSON(){
        stringBuilder.setLength(0);
        stringBuilder.append(JSONElements[6]);
        for (int q = 0; q < gameObjects.size; q++) {
            GameObject gameObject = gameObjects.get(q);
            if (q != 0)
                stringBuilder.append(JSONElements[4]);
            stringBuilder.append(JSONElements[0]);
            long gameObjectLongFlags = gameObject.getLongFlags();
            for (int i = 0; i < JSONStrings.length; i++) {
                if (!(i == 3 && gameObject.getAdditParams().length == 0) && !(i == 4 && gameObjectLongFlags == 0)){     //не добавляем лишнего, если данных нет
                    addEndJSONString(false, i == JSONStrings.length - 1 || i == 0);
                    addStartJSONString(i, false);
                    switch (i) {
                        case 0:
                            stringBuilder.append(gameObject.getType());
                            break;
                        case 1:
                            stringBuilder.append(gameObject.getX());
                            break;
                        case 2:
                            stringBuilder.append(gameObject.getY());
                            break;
                        case 3:
                            stringBuilder.append(JSONElements[6]);
                            float[] addPrms = gameObject.getAdditParams();
                            for (int j = 0; j < addPrms.length; j++) {
                                stringBuilder.append(addPrms[j]);
                                addEndJSONString(false, j == addPrms.length - 1);
                            }
                            stringBuilder.append(JSONElements[7]);
                            break;
                        case 4:
                            stringBuilder.append(gameObjectLongFlags);
                            break;
                    }
                }
            }
            stringBuilder.append(JSONElements[1]);
        }
        stringBuilder.append(JSONElements[7]);
        return stringBuilder.toString();
    }

    private void addStartJSONString(int i, boolean enableScobe){
        stringBuilder.append(JSONElements[3]);
        stringBuilder.append(JSONStrings[i]);
        stringBuilder.append(JSONElements[3]);
        stringBuilder.append(JSONElements[2]);
        if (enableScobe)
            stringBuilder.append(JSONElements[3]);
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

    public JsonValue getResponseJson(){
        return networkDataResponse.getJsonResponse();
    }

    public JsonValue getOldJsonResponse(){
        return networkDataResponse.getOldJsonResponse();
    }




}
