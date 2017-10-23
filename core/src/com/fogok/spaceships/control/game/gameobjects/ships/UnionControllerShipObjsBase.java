package com.fogok.spaceships.control.game.gameobjects.ships;

import com.fogok.spaceships.control.game.UnionControllerBase;
import com.fogok.spaceships.model.NetworkData;
import com.fogok.spaceships.model.game.dataobjects.GameObject;
import com.fogok.spaceships.model.game.dataobjects.GameObjectsType;
import com.fogok.spaceships.model.game.dataobjects.gameobjects.ships.ShipObjectBase;
import com.fogok.spaceships.utils.gamedepended.EveryBodyPool;

public abstract class UnionControllerShipObjsBase<T extends ShipObjectBase, E extends ShipObjectControllerBase> extends UnionControllerBase{

    /*
     * Основа для контроллера любой коллекции космических кораблей
     */

    private E shipObjectController;

    public UnionControllerShipObjsBase(GameObjectsType objectType, EveryBodyPool everyBodyPool, E shipObjectController, NetworkData networkData) {
        super(objectType, everyBodyPool, networkData);
        this.shipObjectController = shipObjectController;

        //TODO: temp
        shipObjectController.setHandledObject(everyBodyPool.obtain(objectType, false));
        shipObjectController.add();
    }

    @Override
    protected boolean handleClientOneObject(GameObject handledClientObject) {
        @SuppressWarnings("unchecked")
        T bullet = (T) handledClientObject;   //приводим к нужному нам типу
        shipObjectController.setHandledObject(bullet);
        shipObjectController.handleClient(false);     //устанавливаем нужный объект нам и делаем с ним то, чё нам нужно
//        handleOneBullet(bullet);    //делаем чёт с ним дополнительно
        return !shipObjectController.isAlive();  //возвращаем, на в игре наш объект или в пуле
    }
}
