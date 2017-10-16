package com.fogok.spaceships.control.game.weapons.bullets;


import com.badlogic.gdx.Gdx;
import com.fogok.spaceships.control.game.GameObject;
import com.fogok.spaceships.view.utils.GMUtils;
import com.fogok.spaceships.view.utils.Pool;
import com.fogok.spaceships.view.utils.PooledObject;

public abstract class BulletItselfBase extends GameObject implements Pool.Poolable, PooledObject {

    /*
     * Основа для любой пульки
     */

    float speed;
    float timeAlive;
    int direction;
    boolean isAlive;

    @Override
    public void start(float x, float y, float speed, int direction){
        setPosition(x, y);
        this.speed = speed;
        this.direction = direction;
        timeAlive = 0f;
        isAlive = true;
        preAction();
    }


    public abstract void preAction();

    @Override
    public void process(){
        if (isAlive) {
            processAction();
            float plusX = GMUtils.getNextX(speed, direction), plusY = GMUtils.getNextY(speed, direction);
            setPosition(getX() + plusX, getY() + plusY);
            timeAlive -= Gdx.graphics.getDeltaTime();
            if (getCondtitionToDead()) {
                isAlive = false;
                postAction();
            }
        }
    }


    public abstract void processAction();

    public abstract void postAction();

    @Override
    public abstract boolean getCondtitionToDead();

    @Override
    public void reset() {
        isAlive = false;
    }

    //region Setters

    public void setTimeAlive(float timeAlive) {
        this.timeAlive = timeAlive;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    //endregion

    //region Getters
    public int getDirection() {
        return direction;
    }

    public int distanceTraveledgetDirection() {
        return direction;
    }

    public float getTimeAlive() {
        return timeAlive;
    }

    public float getSpeed() {
        return speed;
    }

    public boolean isAlive() {
        return isAlive;
    }
    //endregion
}
