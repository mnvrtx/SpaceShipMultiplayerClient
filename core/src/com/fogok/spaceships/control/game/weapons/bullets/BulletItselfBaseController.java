package com.fogok.spaceships.control.game.weapons.bullets;


import com.badlogic.gdx.Gdx;
import com.fogok.spaceships.control.Controller;
import com.fogok.spaceships.model.game.dataobjects.BulletObject;
import com.fogok.spaceships.utils.GMUtils;

import static com.fogok.spaceships.model.game.dataobjects.BulletObject.AdditParams.*;

public abstract class BulletItselfBaseController implements Controller{

    /*
     * Основа для любой пульки
     */

    private BulletObject bulletObject;

    public void setBulletObject(BulletObject bulletObject) {
        this.bulletObject = bulletObject;
    }

    public void start(float x, float y, float speed, int direction){
        bulletObject.setPosition(x, y);
        bulletObject.setAdditParam(speed, SPEED);
        bulletObject.setAdditParam(direction, DIRECTION);
        bulletObject.setAdditParam(0f, TIMEALIVE);
        preAction(bulletObject);
    }

    public abstract void preAction(BulletObject bulletObject);

    @Override
    public void handle(boolean pause) {
        if (isAlive()) {
            processAction(bulletObject);
            float plusX = GMUtils.getNextX(bulletObject.getAdditParam(SPEED), bulletObject.getAdditParam(DIRECTION)), plusY = GMUtils.getNextY(bulletObject.getAdditParam(SPEED), bulletObject.getAdditParam(DIRECTION));
            bulletObject.setPosition(bulletObject.getX() + plusX, bulletObject.getY() + plusY);
            bulletObject.setAdditParam(bulletObject.getAdditParam(TIMEALIVE) - Gdx.graphics.getDeltaTime(), TIMEALIVE);
            if (getCondtitionToDead(bulletObject))
                postAction(bulletObject);
        }
    }


    public abstract void processAction(BulletObject bulletObject);

    public abstract void postAction(BulletObject bulletObject);

    public abstract boolean getCondtitionToDead(BulletObject bulletObject);

    public boolean isAlive(){
        return !getCondtitionToDead(bulletObject);
    }

//    //region Setters
//
//    public void setTimeAlive(float timeAlive) {
//        this.timeAlive = timeAlive;
//    }
//
//    public void setDirection(int direction) {
//        this.direction = direction;
//    }
//
//    //endregion
//
//    //region Getters
//    public int getDirection() {
//        return direction;
//    }
//
//    public int distanceTraveledgetDirection() {
//        return direction;
//    }
//
//    public float getTimeAlive() {
//        return timeAlive;
//    }
//
//    public float getSpeed() {
//        return speed;
//    }
//
//    public boolean isAlive() {
//        return isAlive;
//    }
//    //endregion
}
