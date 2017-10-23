package com.fogok.spaceships.model.game.dataobjects.gameobjects.ships;

import com.fogok.spaceships.model.game.dataobjects.GameObject;

public abstract class ShipObjectBase extends GameObject {

    public enum AdditParams{
        DIRECTION, SPEED, SIZE
    }

    public ShipObjectBase(){

    }

    @Override
    public void reset() {

    }
}
