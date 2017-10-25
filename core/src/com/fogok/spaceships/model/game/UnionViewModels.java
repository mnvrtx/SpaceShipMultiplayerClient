package com.fogok.spaceships.model.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fogok.spaceships.control.ControllerManager;
import com.fogok.spaceships.model.NetworkData;
import com.fogok.spaceships.model.ViewModelObject;
import com.fogok.spaceships.model.game.gameobjects.ships.SimpleShip;

public class UnionViewModels {

    private ViewModelObject[] viewModelObjects;
    private ViewModelMemberBase[] viewModelMemberBase;

    public UnionViewModels(ControllerManager controllerManager, NetworkData networkData) {

        viewModelObjects = new ViewModelObject[1];
        viewModelObjects[0] = new Background(controllerManager);

        viewModelMemberBase = new ViewModelMemberBase[1];
        viewModelMemberBase[0] = new SimpleShip(controllerManager.getEverybodyObjectsController());

    }

    public void draw(SpriteBatch batch){
        for (ViewModelObject viewModelObject : viewModelObjects)
            viewModelObject.draw(batch);
        for (ViewModelMemberBase memberBase : viewModelMemberBase)
            memberBase.draw(batch);
    }
}
