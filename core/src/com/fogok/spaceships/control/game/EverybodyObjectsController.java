package com.fogok.spaceships.control.game;

import com.fogok.spaceships.control.Controller;
import com.fogok.spaceships.control.ControllerManager;
import com.fogok.spaceships.control.game.gameobjects.PlayerObjectsController;
import com.fogok.spaceships.control.game.weapons.DemolishingObjectsController;
import com.fogok.spaceships.model.NetworkData;

public class EverybodyObjectsController implements Controller {

    private DemolishingObjectsController demolishingObjectsController;
    private PlayerObjectsController playerObjectsController;

    public EverybodyObjectsController(ControllerManager controllerManager, NetworkData networkData) {
        demolishingObjectsController = new DemolishingObjectsController(controllerManager, networkData);
        playerObjectsController = new PlayerObjectsController(demolishingObjectsController, controllerManager, networkData);
    }

    @Override
    public void handle(boolean pause) {
        demolishingObjectsController.handle(pause);
        playerObjectsController.handle(pause);
    }
}
