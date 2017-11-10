package com.fogok.spaceships.model.game.weapons.bullets;

import com.fogok.dataobjects.GameObjectsType;
import com.fogok.dataobjects.gameobjects.weapons.SimpleBlusterObject;
import com.fogok.spaceships.control.ControllerManager;
import com.fogok.spaceships.model.game.ViewModelMemberBase;

public class SimpleBluster extends ViewModelMemberBase<SimpleBlusterObject> {

    public SimpleBluster(ControllerManager controllerManager) {
        super(controllerManager, GameObjectsType.SimpleBluster, controllerManager.getEveryBodyViews().getView(GameObjectsType.SimpleBluster));
    }

}
