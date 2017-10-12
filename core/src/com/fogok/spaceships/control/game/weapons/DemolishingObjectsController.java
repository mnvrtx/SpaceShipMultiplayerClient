package com.fogok.spaceships.control.game.weapons;

import com.fogok.spaceships.control.Controller;
import com.fogok.spaceships.control.game.weapons.bullets.simplebluster.BlusterController;

public class DemolishingObjectsController implements Controller {

    /*
     * Класс, который отвечает за все контроллеры, которые что-то разрушают
     */

    private BlusterController blusterBulletController;

    public DemolishingObjectsController() {
        blusterBulletController = new BlusterController();
    }

    @Override
    public void handle(boolean pause) {

    }

    public BlusterController getBlusterBulletController() {
        return blusterBulletController;
    }
}
