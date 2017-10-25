package com.fogok.spaceships.model.game.dataobjects.weapons;

import com.fogok.spaceships.model.game.dataobjects.GameObjectsType;

public class SimpleBlusterObject extends BulletObjectBase {

    public SimpleBlusterObject() {
        initAdditParams(AdditParams.values().length);
        setType(GameObjectsType.SimpleBluster);
    }

}
