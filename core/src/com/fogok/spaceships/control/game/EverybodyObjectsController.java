package com.fogok.spaceships.control.game;

import com.fogok.spaceships.control.Controller;
import com.fogok.spaceships.control.game.weapons.DemolishingObjectsController;

public class EverybodyObjectsController implements Controller{

    private DemolishingObjectsController demolishingObjectsController;

    public EverybodyObjectsController() {
        demolishingObjectsController = new DemolishingObjectsController();
    }

    @Override
    public void handle(boolean pause) {
        demolishingObjectsController.handle(pause);
    }

    public DemolishingObjectsController getDemolishingObjectsController() {
        return demolishingObjectsController;
    }
}
