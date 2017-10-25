package com.fogok.spaceships.model.game.weapons;

import com.fogok.spaceships.control.game.EverybodyObjectsController;
import com.fogok.spaceships.model.game.ViewModelMemberBase;
import com.fogok.spaceships.model.game.dataobjects.GameObjectsType;
import com.fogok.spaceships.model.game.dataobjects.weapons.SimpleBlusterObject;

public class SimpleBluster extends ViewModelMemberBase<SimpleBlusterObject>{


    public SimpleBluster(EverybodyObjectsController everybodyObjectsController) {
        super(everybodyObjectsController, GameObjectsType.SimpleBluster, null);
    }


}
