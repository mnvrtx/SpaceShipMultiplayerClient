package com.fogok.spaceships.model.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fogok.spaceships.control.ControllerManager;
import com.fogok.spaceships.model.SimpleViewModelObject;
import com.fogok.spaceships.model.game.gameobjects.ships.SimpleShip;

public class EveryBodyViewModels {

    private SimpleViewModelObject[] simpleViewModelObjects;
    private ViewModelMemberBase[] viewModelMemberBase;

    public EveryBodyViewModels(ControllerManager controllerManager) {

        simpleViewModelObjects = new SimpleViewModelObject[1];
        simpleViewModelObjects[0] = new Background(controllerManager);

        viewModelMemberBase = new ViewModelMemberBase[1];
        viewModelMemberBase[0] = new SimpleShip(controllerManager.getEverybodyObjectsController());


        //TODO: сделать тут как в классе EveryBodyViews
    }

    public void draw(SpriteBatch batch){
        for (SimpleViewModelObject simpleViewModelObject : simpleViewModelObjects)
            simpleViewModelObject.draw(batch);
        for (ViewModelMemberBase memberBase : viewModelMemberBase)
            memberBase.draw(batch);
    }
}
