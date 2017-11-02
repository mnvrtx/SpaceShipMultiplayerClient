package com.fogok.spaceships.model.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fogok.dataobjects.GameObject;
import com.fogok.dataobjects.GameObjectsType;
import com.fogok.dataobjects.utils.libgdxexternals.Array;
import com.fogok.spaceships.control.ControllerManager;
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
