package com.fogok.spaceships.model.game.dataobjects.gameobjects.ships;

import com.fogok.spaceships.model.game.dataobjects.GameObjectsType;

public class SimpleShip extends ShipObjectBase {

    public SimpleShip() {
        initAdditParams(AdditParams.values().length);
        setType(GameObjectsType.SimpleShip);
    }

}
