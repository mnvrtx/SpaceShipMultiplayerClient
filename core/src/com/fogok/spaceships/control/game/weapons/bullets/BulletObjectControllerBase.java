package com.fogok.spaceships.control.game.weapons.bullets;

import com.badlogic.gdx.Gdx;
import com.fogok.spaceships.control.game.ObjectController;
import com.fogok.spaceships.control.game.weapons.Weapon;
import com.fogok.spaceships.model.game.dataobjects.weapons.BulletObjectBase;
import com.fogok.spaceships.model.game.dataobjects.GameObject;
import com.fogok.spaceships.utils.GMUtils;

import static com.fogok.spaceships.model.game.dataobjects.weapons.BulletObjectBase.AdditParams.*;

public abstract class BulletObjectControllerBase implements ObjectController, Weapon{

    /*
     * Основа для любой пульки
     */

    private BulletObjectBase bulletObjectBase;

    @Override
    public void setHandledObject(GameObject handledObject) {
        this.bulletObjectBase = (BulletObjectBase) handledObject;
    }


    @Override
    public void fire(float x, float y, float speed, int direction) {
        bulletObjectBase.setPosition(x, y);
        bulletObjectBase.setAdditParam(speed, SPEED);
        bulletObjectBase.setAdditParam(direction, DIRECTION);
        bulletObjectBase.setAdditParam(0f, TIMEALIVE);
        preClientAction(bulletObjectBase);
    }

    @Override
    public void handleClient(boolean pause) {
        if (isAlive()) {
            processClientAction(bulletObjectBase);
            float plusX = GMUtils.getNextX(bulletObjectBase.getAdditParam(SPEED), bulletObjectBase.getAdditParam(DIRECTION)), plusY = GMUtils.getNextY(bulletObjectBase.getAdditParam(SPEED), bulletObjectBase.getAdditParam(DIRECTION));
            bulletObjectBase.setPosition(bulletObjectBase.getX() + plusX, bulletObjectBase.getY() + plusY);
            bulletObjectBase.setAdditParam(bulletObjectBase.getAdditParam(TIMEALIVE) - Gdx.graphics.getDeltaTime(), TIMEALIVE);
            if (getCondtitionToDead(bulletObjectBase))
                postClientAction(bulletObjectBase);
        }
    }

    public abstract void preClientAction(BulletObjectBase bulletObjectBase);

    public abstract void processClientAction(BulletObjectBase bulletObjectBase);

    public abstract void postClientAction(BulletObjectBase bulletObjectBase);



    public abstract boolean getCondtitionToDead(BulletObjectBase bulletObjectBase);

    @Override
    public boolean isAlive(){
        return !getCondtitionToDead(bulletObjectBase);
    }

}
