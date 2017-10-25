package com.fogok.spaceships.control.game.weapons;

import com.fogok.spaceships.control.Controller;
import com.fogok.spaceships.control.ControllerManager;
import com.fogok.spaceships.control.game.weapons.bullets.simplebluster.UnionControllerBlusterObjects;
import com.fogok.spaceships.model.NetworkData;

public class DemolishingObjectsController implements Controller {

    /*
     * Класс, который отвечает за все контроллеры, которые что-то разрушают
     */

    private UnionControllerBlusterObjects blusterBulletController;

    public DemolishingObjectsController(ControllerManager controllerManager, NetworkData networkData) {
        blusterBulletController = new UnionControllerBlusterObjects(controllerManager, networkData);
    }

    @Override
    public void handle(boolean pause) {
        blusterBulletController.handleClient(pause);
    }

    public UnionControllerBlusterObjects getBlusterBulletController() {
        return blusterBulletController;
    }
}
