package com.fogok.spaceships.model.game.weapons.bullets;

import com.fogok.spaceships.control.ControllerManager;
import com.fogok.spaceships.model.game.ViewModelMemberBase;
import com.fogok.spaceships.model.game.dataobjects.GameObjectsType;
import com.fogok.spaceships.model.game.dataobjects.weapons.SimpleBlusterObject;

public class SimpleBluster extends ViewModelMemberBase<SimpleBlusterObject>{

    public SimpleBluster(ControllerManager controllerManager) {
        super(controllerManager, GameObjectsType.SimpleBluster, controllerManager.getEveryBodyViews().getView(GameObjectsType.SimpleBluster));
    }

}
