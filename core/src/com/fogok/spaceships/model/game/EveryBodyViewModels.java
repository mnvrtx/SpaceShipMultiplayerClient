package com.fogok.spaceships.model.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fogok.spaceships.control.ControllerManager;
import com.fogok.spaceships.model.SimpleViewModelObject;
import com.fogok.dataobjects.GameObjectsType;
import com.fogok.spaceships.model.game.gameobjects.ships.SimpleShip;
import com.fogok.spaceships.model.game.weapons.bullets.SimpleBluster;

public class EveryBodyViewModels {

    private SimpleViewModelObject[] simpleViewModelObjects;
    private ViewModelMemberBase[] viewModelMemberBase;

    public EveryBodyViewModels(ControllerManager controllerManager) {

        simpleViewModelObjects = new SimpleViewModelObject[1];
        simpleViewModelObjects[0] = new Background(controllerManager);

        viewModelMemberBase = new ViewModelMemberBase[GameObjectsType.values().length];
        for (GameObjectsType gameObjectsType : GameObjectsType.values()){
            int ord = gameObjectsType.ordinal();
            switch (gameObjectsType) {
                case SimpleBluster:
                    viewModelMemberBase[ord] = new SimpleBluster(controllerManager);
                    break;
                case SimpleShip:
                    viewModelMemberBase[ord] = new SimpleShip(controllerManager);
                    break;
            }
        }

    }

    public void draw(SpriteBatch batch){
        for (SimpleViewModelObject simpleViewModelObject : simpleViewModelObjects)
            simpleViewModelObject.draw(batch);
        for (ViewModelMemberBase memberBase : viewModelMemberBase)
            if (memberBase != null)
                memberBase.draw(batch);
    }
}
