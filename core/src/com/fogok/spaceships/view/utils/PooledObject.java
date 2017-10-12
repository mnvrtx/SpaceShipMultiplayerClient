package com.fogok.spaceships.view.utils;

public interface PooledObject {
    void start(float x, float y, float speed, int direction);
    void process();
    boolean getCondtitionToDead();
}
