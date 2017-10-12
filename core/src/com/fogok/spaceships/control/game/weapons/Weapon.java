package com.fogok.spaceships.control.game.weapons;

import com.fogok.spaceships.control.Controller;

public interface Weapon extends Controller{
    void fire(float x, float y, float speed, int direction);
}
