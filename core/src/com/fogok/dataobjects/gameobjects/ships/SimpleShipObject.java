package com.fogok.dataobjects.gameobjects.ships;

import com.fogok.dataobjects.GameObjectsType;

public class SimpleShipObject extends ShipObjectBase {

    public SimpleShipObject() {
        initAdditParams(AdditParams.values().length);
        setType(GameObjectsType.SimpleShip);
    }

    @Override
    public void reset() {

    }
}
