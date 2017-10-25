package com.fogok.spaceships.control.game.gameobjects;

import com.fogok.spaceships.control.Controller;
import com.fogok.spaceships.control.ControllerManager;
import com.fogok.spaceships.control.game.gameobjects.ships.simpleship.UnionControllerSimpleShipObjs;
import com.fogok.spaceships.control.game.weapons.DemolishingObjectsController;
import com.fogok.spaceships.model.NetworkData;

public class PlayerObjectsController implements Controller{

    /*
     * Класс, который отвечает за все контроллеры, которые связаны непосредственно с игроком
     */

    private UnionControllerSimpleShipObjs unionControllerSimpleShipObjs;

    public PlayerObjectsController(DemolishingObjectsController demolishingObjectsController, ControllerManager controllerManager, NetworkData networkData) {
        unionControllerSimpleShipObjs = new UnionControllerSimpleShipObjs(controllerManager, networkData);
    }

    @Override
    public void handle(boolean pause) {
        unionControllerSimpleShipObjs.handleClient(pause);
    }

    public UnionControllerSimpleShipObjs getUnionControllerSimpleShipObjs() {
        return unionControllerSimpleShipObjs;
    }
}
