package com.fogok.spaceships.utils.gamedepended;

import com.badlogic.gdx.utils.Array;
import com.fogok.spaceships.model.NetworkData;
import com.fogok.spaceships.model.game.dataobjects.BulletObject;
import com.fogok.spaceships.model.game.dataobjects.GameObject;
import com.fogok.spaceships.model.game.dataobjects.GameObjectsType;
import com.fogok.spaceships.model.game.dataobjects.SpaceShipObject;
import com.fogok.spaceships.utils.Pool;

/**
 * Created by FOGOK on 10/18/2017 8:55 PM.
 */

public class EveryBodyPool extends Pool<GameObject> {

    private NetworkData networkData;

    public EveryBodyPool(NetworkData networkData, int initialCapacity) {
        super(initialCapacity);
        this.networkData = networkData;
    }

    @Override
    protected GameObject newObject() {
        return null;
    }

    protected GameObject newObject(GameObjectsType type){
        switch (type) {
            case Bluster:
                return new BulletObject();
            case SpaceShip:
                return new SpaceShipObject();
        }
        return null;
    }

    public GameObject obtain(GameObjectsType type){
        for (int i = 0; i < freeObjects.size; i++) {
            GameObject gameObject = freeObjects.get(i);
            freeObjects.removeIndex(i);
            if (type.ordinal() == gameObject.getType()) {
                return gameObject;
            }
        }
        return newObject(type);
    }

    public Array<GameObject> getEveryBodies(){
        return freeObjects;
    }
}
