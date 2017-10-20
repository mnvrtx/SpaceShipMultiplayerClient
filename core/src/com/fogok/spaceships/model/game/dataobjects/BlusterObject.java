package com.fogok.spaceships.model.game.dataobjects;

public class BlusterObject extends BulletObject{

    public BlusterObject() {
        initAdditParams(AdditParams.values().length);
        setType(GameObjectsType.Bluster);
    }

}
