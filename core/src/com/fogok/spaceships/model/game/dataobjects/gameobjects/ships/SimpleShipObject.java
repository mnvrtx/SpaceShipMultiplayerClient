package com.fogok.spaceships.model.game.dataobjects.gameobjects.ships;

import com.fogok.spaceships.model.game.dataobjects.GameObjectsType;

public class SimpleShipObject extends ShipObjectBase {

    public SimpleShipObject() {
        initAdditParams(AdditParams.values().length);
        setType(GameObjectsType.SimpleShip);
    }

}
