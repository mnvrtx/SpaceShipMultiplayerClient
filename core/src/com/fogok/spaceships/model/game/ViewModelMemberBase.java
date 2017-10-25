package com.fogok.spaceships.model.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.fogok.spaceships.control.game.EverybodyObjectsController;
import com.fogok.spaceships.control.game.UnionControllerBase;
import com.fogok.spaceships.model.game.dataobjects.GameObject;
import com.fogok.spaceships.model.game.dataobjects.GameObjectsType;
import com.fogok.spaceships.view.View;

public abstract class ViewModelMemberBase<T extends GameObject, E extends UnionControllerBase> {

    protected Array<T> gameObjects;
    protected View view;

    @SuppressWarnings("unchecked")
    public ViewModelMemberBase(EverybodyObjectsController everybodyObjectsController, GameObjectsType gameObjectsType, View view) {
        gameObjects = (Array<T>) everybodyObjectsController.getEveryBodyObjectsPool().getAllObjectsFromType(gameObjectsType);
        this.view = view;
    }

    public void draw(SpriteBatch batch) {
        for (GameObject gameObject : gameObjects)
            if (!gameObject.isInsideField())
                view.draw(batch, gameObject);
    }
}
