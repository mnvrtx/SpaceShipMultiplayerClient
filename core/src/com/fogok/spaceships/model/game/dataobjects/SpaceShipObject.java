package com.fogok.spaceships.model.game.dataobjects;

public class SpaceShipObject extends GameObject  {

    public enum AdditParams{
        DIRECTION, SPEED, SIZE
    }

    public SpaceShipObject(){
        initAdditParams(3);
        setType(GameObjectsType.SpaceShip);
    }

    @Override
    public void reset() {

    }
}
