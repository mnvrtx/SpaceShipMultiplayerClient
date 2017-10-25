package com.fogok.spaceships.utils.gamedepended;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.fogok.spaceships.model.NetworkData;
import com.fogok.spaceships.model.game.dataobjects.GameObject;
import com.fogok.spaceships.model.game.dataobjects.GameObjectsType;
import com.fogok.spaceships.model.game.dataobjects.gameobjects.ships.SimpleShipObject;
import com.fogok.spaceships.model.game.dataobjects.weapons.SimpleBlusterObject;
import com.fogok.spaceships.utils.Pool;

public class EveryBodyPool extends Pool<GameObject> {

    private NetworkData networkData;
    private Array<Array<GameObject>> typedObjects;
    //двумерный массив, как работает рассказываю на примере:
    /*

    typedObjects.get(type.ordinal()).add(responseGameObject);
    где Первый get выдаёт нам ArrayList с объектами, которые относятся
    только к определённому типу

    а дальше мы можем с ними уже работать
    * */

    private Array<IntArray> clientServerObjectsCount;
    //двумерный массив, как работает рассказываю на примере:
    /*

    clientServerObjectCount.get(type.ordinal()).set($0/1$, count);
    где Первый get выдаёт нам IntArray с объектами, которые относятся
    только к определённому типу

    $0/1$ = тут мы можем указать количество серверных/клиентских объектов
    0 - количество серверных объектов
    1 - количество клиентских объектов

    * */

    //TODO: УБРАТЬ ЛИШНИЕ ТУДУ ОЧЕНЬ ВАЖНО!!!!!

    public EveryBodyPool(NetworkData networkData, int initialCapacity) {
        super(initialCapacity);
        typedObjects = new Array<Array<GameObject>>(false, initialCapacity);
        for (int i = 0; i < initialCapacity; i++) {
            //TODO: increment new arrays
            typedObjects.add(new Array<GameObject>(false, initialCapacity));
        }

        clientServerObjectsCount = new Array<IntArray>(false, initialCapacity);
        for (int i = 0; i < initialCapacity; i++) {
            clientServerObjectsCount.add(new IntArray(false, 2));
            clientServerObjectsCount.peek().add(0);
            clientServerObjectsCount.peek().add(0);
        }


        this.networkData = networkData; //TODO: бряку сюад + сделать красивое отображение всех наших объектов для наглядности, что и как происходит
    }

    @Override
    protected GameObject newObject() {
        return null;
    }

    protected GameObject newObject(GameObjectsType type){
        switch (type) {
            case SimpleBluster:
                return new SimpleBlusterObject();
            case SimpleShip:
                return new SimpleShipObject();
        }
        return null;
    }

    public GameObject obtain(GameObjectsType type, boolean isServer){

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
        responseGameObject.setServer(isServer);


        clientServerObjectsCount.get(type.ordinal()).incr(isServer ? 0 : 1, 1);
        typedObjects.get(type.ordinal()).add(responseGameObject);

        return responseGameObject;

    }

    @Override
    public void free(GameObject object) {
        super.free(object);
        object.setInsideField(true);
        if (typedObjects.get(object.getType()).removeValue(object, false)){
            clientServerObjectsCount.get(object.getType()).incr(object.isServer() ? 0 : 1, -1);
        }else{
            throw new UnsupportedOperationException("Type object: " + object.getType() + " has not be removed");
        }
    }

    public Array<GameObject> getAllObjectsFromType(GameObjectsType type){
        return typedObjects.get(type.ordinal());
    }

    public int getClientServerObjectsCount(GameObjectsType type, boolean isServer){
        return clientServerObjectsCount.get(type.ordinal()).get(isServer ? 0 : 1);
    }

    public Array<GameObject> getFreeEveryBodies(){
        return freeObjects;
    }

    @Override
    public void clear() {
        super.clear();
        for (int i = 0; i < typedObjects.size; i++) {
            typedObjects.get(i).clear();
            typedObjects.set(i, null);
        }
        for (int i = 0; i < clientServerObjectsCount.size; i++) {
            clientServerObjectsCount.get(i).clear();
            clientServerObjectsCount.set(i, null);
        }
        typedObjects.clear();
        typedObjects = null;

        clientServerObjectsCount.clear();
        clientServerObjectsCount = null;
        //TODO: GC optimize
    }
}
