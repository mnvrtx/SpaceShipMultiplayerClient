package com.fogok.spaceships.control.game.weapons.bullets.simplebluster;

import com.fogok.spaceships.control.game.weapons.bullets.BulletObjectControllerBase;
import com.fogok.spaceships.model.game.dataobjects.weapons.BulletObjectBase;

import static com.fogok.spaceships.model.game.dataobjects.weapons.BulletObjectBase.AdditParams.*;

public class BlusterObjectController extends BulletObjectControllerBase {

    /*
     * Контроллер пульки бластера
     */

    public BlusterObjectController(){

    }

    @Override
    public void preClientAction(BulletObjectBase bulletObjectBase) {
        bulletObjectBase.setAdditParam(0.6f, TIMEALIVE);
    }

    @Override
    public void processClientAction(BulletObjectBase bulletObjectBase) {

    }

    @Override
    public void postClientAction(BulletObjectBase bulletObjectBase) {

    }

    @Override
    public boolean isDead(BulletObjectBase bulletObjectBase) {
        return bulletObjectBase.getAdditParam(TIMEALIVE) < 0f;
    }


//    @Override
//    public void preClientAction() {
//        setTimeAlive(0.6f);
//    }
//
//    @Override
//    public void processClientAction() {
//
//    }
//
//    @Override
//    public void postClientAction() {
//
//    }
//
//    @Override
//    public boolean isDead() {
//        return getTimeAlive() < 0f;
//    }
//
//    public float getAlpha(float startFading){
//        return getTimeAlive() < startFading ? GMUtils.normalizeOneZero(getTimeAlive() / startFading) : 1f;
//    }

}
