package com.fogok.spaceships.model.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.fogok.spaceships.control.ControllerManager;
import com.fogok.spaceships.model.game.dataobjects.GameObject;
import com.fogok.spaceships.model.game.dataobjects.GameObjectsType;
import com.fogok.spaceships.view.View;

public abstract class ViewModelMemberBase<T extends GameObject> {

    /**
     * Здесь происходит только тупая отрисовка всех GameObject`ов
     */

    protected Array<T> gameObjects;
    protected View view;

    @SuppressWarnings("unchecked")
    public ViewModelMemberBase(ControllerManager controllerManager, GameObjectsType gameObjectsType, View view) {
        gameObjects = (Array<T>) controllerManager.getEveryBodyObjectsPool().getAllObjectsFromType(gameObjectsType);
        this.view = view;
    }

    public void draw(SpriteBatch batch) {
        for (GameObject gameObject : gameObjects)
            if (!gameObject.isInsideField())
                view.draw(batch, gameObject);
    }
}
