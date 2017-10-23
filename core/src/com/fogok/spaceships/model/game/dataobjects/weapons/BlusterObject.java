package com.fogok.spaceships.model.game.dataobjects.weapons;

import com.fogok.spaceships.model.game.dataobjects.GameObjectsType;

public class BlusterObject extends BulletObjectBase {

    public BlusterObject() {
        initAdditParams(AdditParams.values().length);
        setType(GameObjectsType.SimpleBluster);
    }

}
