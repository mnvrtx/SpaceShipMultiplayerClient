package com.fogok.spaceships.model.game.gameobjects.ships;

import com.fogok.spaceships.control.ControllerManager;
import com.fogok.spaceships.model.game.ViewModelMemberBase;
import com.fogok.spaceships.model.game.dataobjects.GameObjectsType;
import com.fogok.spaceships.model.game.dataobjects.gameobjects.ships.SimpleShipObject;

public class SimpleShip extends ViewModelMemberBase<SimpleShipObject> {

    public SimpleShip(ControllerManager controllerManager) {
        super(controllerManager, GameObjectsType.SimpleShip, controllerManager.getEveryBodyViews().getView(GameObjectsType.SimpleShip));
    }

}
