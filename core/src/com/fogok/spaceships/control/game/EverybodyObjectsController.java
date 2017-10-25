package com.fogok.spaceships.control.game;

import com.fogok.spaceships.control.Controller;
import com.fogok.spaceships.control.ControllerManager;
import com.fogok.spaceships.control.game.gameobjects.PlayerObjectsController;
import com.fogok.spaceships.control.game.weapons.DemolishingObjectsController;
import com.fogok.spaceships.model.NetworkData;
import com.fogok.spaceships.utils.gamedepended.EveryBodyPool;

public class EverybodyObjectsController implements Controller {

    //region Pool system
    private static final int bufferSize = 100;

    private final EveryBodyPool everyBodyObjectsPool;
    //endregion

    private NetworkData networkData;

    private DemolishingObjectsController demolishingObjectsController;
    private PlayerObjectsController playerObjectsController;

    public EverybodyObjectsController(ControllerManager controllerManager, NetworkData networkData) {
        this.networkData = networkData;
        everyBodyObjectsPool = new EveryBodyPool(controllerManager, networkData, bufferSize);

        demolishingObjectsController = new DemolishingObjectsController(controllerManager, networkData);
        playerObjectsController = new PlayerObjectsController(demolishingObjectsController, controllerManager, networkData);
    }

    @Override
    public void handle(boolean pause) {
        demolishingObjectsController.handle(pause);
        playerObjectsController.handle(pause);
    }

    public EveryBodyPool getEveryBodyObjectsPool() {
        return everyBodyObjectsPool;
    }
}
