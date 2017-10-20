package com.fogok.spaceships.control.game.weapons;

import com.fogok.spaceships.control.Controller;
import com.fogok.spaceships.control.game.weapons.bullets.simplebluster.BlusterController;
import com.fogok.spaceships.model.NetworkData;
import com.fogok.spaceships.utils.gamedepended.EveryBodyPool;

public class DemolishingObjectsController implements Controller {

    /*
     * Класс, который отвечает за все контроллеры, которые что-то разрушают
     */

    private BlusterController blusterBulletController;

    public DemolishingObjectsController(EveryBodyPool everyBodyPool, NetworkData networkData) {
        blusterBulletController = new BlusterController(everyBodyPool, networkData);
    }

    @Override
    public void handle(boolean pause) {

    }

    public BlusterController getBlusterBulletController() {
        return blusterBulletController;
    }
}
