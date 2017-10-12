package com.fogok.spaceships.control.game.weapons.bullets.simplebluster;

import com.fogok.spaceships.control.game.weapons.bullets.BulletItselfBase;
import com.fogok.spaceships.view.utils.GMUtils;

public class BlusterBulletController extends BulletItselfBase{

    /*
     * Контроллер пульки бластера
     */



    @Override
    public void preAction() {
        setTimeAlive(0.6f);
    }

    @Override
    public void processAction() {

    }

    @Override
    public void postAction() {

    }

    @Override
    public boolean getCondtitionToDead() {
        return getTimeAlive() < 0f;
    }

    public float getAlpha(float startFading){
        return getTimeAlive() < startFading ? GMUtils.normalizeOneZero(getTimeAlive() / startFading) : 1f;
    }

}
