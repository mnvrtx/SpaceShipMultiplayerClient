package com.fogok.dataobjects.weapons;

import com.fogok.dataobjects.GameObjectsType;

public class SimpleBlusterObject extends BulletObjectBase {

    public SimpleBlusterObject() {
        initAdditParams(AdditParams.values().length);
        setType(GameObjectsType.SimpleBluster);
    }

    @Override
    public void reset() {

    }
}
