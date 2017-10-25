package com.fogok.spaceships.control.game;

import com.fogok.spaceships.control.Controller;
import com.fogok.spaceships.control.ControllerManager;
import com.fogok.spaceships.control.game.weapons.DemolishingObjectsController;
import com.fogok.spaceships.control.ui.JoyStickController;
import com.fogok.spaceships.model.NetworkData;
import com.fogok.spaceships.utils.gamedepended.EveryBodyPool;

public class EverybodyObjectsController implements Controller{

    //region Pool system
    private static final int bufferSize = 100;

    private final EveryBodyPool everyBodyObjectsPool;
    //endregion

    private NetworkData networkData;

    private DemolishingObjectsController demolishingObjectsController;

    private OPDataController opDataController;

    private MSDataController msDataController;

    public EverybodyObjectsController(ControllerManager controllerManager, NetworkData networkData, JoyStickController joyStickController) {
        this.networkData = networkData;
        everyBodyObjectsPool = new EveryBodyPool(networkData, bufferSize);

        demolishingObjectsController = new DemolishingObjectsController(everyBodyObjectsPool, networkData);

        msDataController = new MSDataController(everyBodyObjectsPool, networkData, joyStickController);

        opDataController = new OPDataController(networkData);
    }

    @Override
    public void handle(boolean pause) {
        demolishingObjectsController.handle(pause);
        msDataController.handle(pause);
        opDataController.handle(pause);
    }

    public MSDataController getMsDataController() {
        return msDataController;
    }

    public DemolishingObjectsController getDemolishingObjectsController() {
        return demolishingObjectsController;
    }

    public EveryBodyPool getEveryBodyObjectsPool() {
        return everyBodyObjectsPool;
    }
}
