package com.fogok.spaceships.model.game.gameobjects.ships;

import com.fogok.spaceships.control.game.EverybodyObjectsController;
import com.fogok.spaceships.model.game.ViewModelMemberBase;
import com.fogok.spaceships.model.game.dataobjects.GameObjectsType;
import com.fogok.spaceships.model.game.dataobjects.gameobjects.ships.SimpleShipObject;
import com.fogok.spaceships.view.game.ShipView;

public class SimpleShip extends ViewModelMemberBase<SimpleShipObject> {

    public SimpleShip(EverybodyObjectsController everybodyObjectsController) {
        super(everybodyObjectsController, GameObjectsType.SimpleShip, new ShipView());
    }

}
