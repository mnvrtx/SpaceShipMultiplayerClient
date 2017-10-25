package com.fogok.spaceships.control.game.gameobjects.ships.simpleship;

import com.fogok.spaceships.control.ControllerManager;
import com.fogok.spaceships.control.game.gameobjects.ships.UnionControllerShipObjsBase;
import com.fogok.spaceships.model.NetworkData;
import com.fogok.spaceships.model.game.dataobjects.GameObjectsType;
import com.fogok.spaceships.model.game.dataobjects.gameobjects.ships.SimpleShipObject;

public class UnionControllerSimpleShipObjs extends UnionControllerShipObjsBase<SimpleShipObject, SimpleShipObjectController> {


    public UnionControllerSimpleShipObjs(ControllerManager controllerManager, NetworkData networkData) {
        super(GameObjectsType.SimpleShip, controllerManager.getEverybodyObjectsController().getEveryBodyObjectsPool(), new SimpleShipObjectController(controllerManager.getJoyStickController()), networkData);
    }

}
