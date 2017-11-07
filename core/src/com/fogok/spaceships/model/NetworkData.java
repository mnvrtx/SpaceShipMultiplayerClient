package com.fogok.spaceships.model;


import com.fogok.dataobjects.GameObject;
import com.fogok.dataobjects.utils.FastJsonWriter;
import com.fogok.dataobjects.utils.libgdxexternals.Array;
import com.fogok.dataobjects.utils.libgdxexternals.JsonValue;

public class NetworkData {


//    private Array<GameObject> gameObjects = new Array<GameObject>(false, 100);

    private FastJsonWriter fastJsonWriter;

    private NetworkDataResponse networkDataResponse = new NetworkDataResponse();

    public void setTypedObjets(Array<Array<GameObject>> typedObjects) {
        fastJsonWriter = new FastJsonWriter();
        fastJsonWriter.setTypedObjects(typedObjects);
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

    public String getJSON() {
        return fastJsonWriter.getJSON();
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
