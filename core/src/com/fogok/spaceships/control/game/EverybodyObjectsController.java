package com.fogok.spaceships.control.game;

import com.fogok.spaceships.control.Controller;
import com.fogok.spaceships.control.game.gameobjects.SpaceShipClientController;
import com.fogok.spaceships.control.game.gameobjects.SpaceShipController;
import com.fogok.spaceships.control.game.weapons.DemolishingObjectsController;
import com.fogok.spaceships.control.ui.JoyStickController;
import com.fogok.spaceships.model.NetworkData;

public class EverybodyObjectsController implements Controller{

    private NetworkData networkData;

    private DemolishingObjectsController demolishingObjectsController;
    private com.fogok.spaceships.control.game.gameobjects.SpaceShipController spaceShipController;

    public EverybodyObjectsController(NetworkData networkData, JoyStickController joyStickController) {
        this.networkData = networkData;
        demolishingObjectsController = new DemolishingObjectsController();
        spaceShipController = new SpaceShipClientController(networkData, joyStickController);
    }

    @Override
    public void handle(boolean pause) {
        demolishingObjectsController.handle(pause);
    }

    public DemolishingObjectsController getDemolishingObjectsController() {
        return demolishingObjectsController;
    }

    public SpaceShipController getSpaceShipController() {
        return spaceShipController;
    }
}
