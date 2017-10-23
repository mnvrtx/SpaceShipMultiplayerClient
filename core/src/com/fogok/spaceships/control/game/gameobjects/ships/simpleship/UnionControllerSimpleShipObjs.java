package com.fogok.spaceships.control.game.gameobjects.ships.simpleship;

import com.fogok.spaceships.control.game.gameobjects.ships.UnionControllerShipObjsBase;
import com.fogok.spaceships.model.NetworkData;
import com.fogok.spaceships.model.game.dataobjects.GameObjectsType;
import com.fogok.spaceships.model.game.dataobjects.gameobjects.ships.SimpleShip;
import com.fogok.spaceships.utils.gamedepended.EveryBodyPool;

public class UnionControllerSimpleShipObjs extends UnionControllerShipObjsBase<SimpleShip, SimpleShipObjectController> {


    public UnionControllerSimpleShipObjs(GameObjectsType objectType, EveryBodyPool everyBodyPool, SimpleShipObjectController shipObjectController, NetworkData networkData) {
        super(objectType, everyBodyPool, shipObjectController, networkData);
    }

}
