package com.fogok.spaceships.control.game.weapons.bullets.simplebluster;

import com.fogok.spaceships.control.game.weapons.bullets.BulletItselfBaseController;
import com.fogok.spaceships.model.game.dataobjects.BulletObject;

import static com.fogok.spaceships.model.game.dataobjects.BulletObject.AdditParams.*;

public class BlusterBulletControllerController extends BulletItselfBaseController {

    /*
     * Контроллер пульки бластера
     */

    public BlusterBulletControllerController(){

    }

    @Override
    public void preAction(BulletObject bulletObject) {
        bulletObject.setAdditParam(0.6f, TIMEALIVE);
    }

    @Override
    public void processAction(BulletObject bulletObject) {

    }

    @Override
    public void postAction(BulletObject bulletObject) {

    }

    @Override
    public boolean getCondtitionToDead(BulletObject bulletObject) {
        return bulletObject.getAdditParam(TIMEALIVE) < 0f;
    }


//    @Override
//    public void preAction() {
//        setTimeAlive(0.6f);
//    }
//
//    @Override
//    public void processAction() {
//
//    }
//
//    @Override
//    public void postAction() {
//
//    }
//
//    @Override
//    public boolean getCondtitionToDead() {
//        return getTimeAlive() < 0f;
//    }
//
//    public float getAlpha(float startFading){
//        return getTimeAlive() < startFading ? GMUtils.normalizeOneZero(getTimeAlive() / startFading) : 1f;
//    }

}
