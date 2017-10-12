package com.fogok.spaceships.control.game.weapons.bullets.simplebluster;

import com.fogok.spaceships.control.game.weapons.bullets.BulletControllerBase;

public class BlusterController extends BulletControllerBase<BlusterBulletController>{

    /*
     * Контроллер бластера
     */

    public BlusterController() {
        super(BlusterBulletController.class);
    }


    @Override
    public void addBulletPostAction(BlusterBulletController bullet) {

    }

    @Override
    public void handleOneBullet(BlusterBulletController bullet) {

    }
}
