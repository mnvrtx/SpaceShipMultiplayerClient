package com.fogok.spaceships.control.game;

import com.fogok.spaceships.view.utils.GMUtils;

public abstract class GameObject {

    private int type;
    private float x;
    private float y;
    private float[] additParams;

    public <E extends Enum<E>> float getAdditParam(E enumObject) {
        return additParams[enumObject.ordinal()];
    }

    public float[] getAdditParams() {
        return additParams;
    }

    public int getType() {
        return type;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setType(GameObjectsTypes gameObjectsTypes) {
        this.type = gameObjectsTypes.ordinal();
    }

    public void setPosition(float x, float y) {
        setX(x);
        setY(y);
    }

    public void initAdditParams(int size) {
        additParams = new float[size];
    }

    public <E extends Enum<E>> void setAdditParam(float param, E enumObject) {
        additParams[enumObject.ordinal()] = GMUtils.getRoundedVal(param);
    }

    public void setX(float x) {
        this.x = GMUtils.getRoundedVal(x);
    }

    public void setY(float y) {
        this.y = GMUtils.getRoundedVal(y);
    }
}
