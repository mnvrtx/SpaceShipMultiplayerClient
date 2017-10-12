package com.fogok.spaceships.view.screens.screen_components;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fogok.spaceships.control.ControllerManager;
import com.fogok.spaceships.model.NetworkData;
import com.fogok.spaceships.model.ViewModelObject;
import com.fogok.spaceships.model.game.Background;
import com.fogok.spaceships.model.game.SpaceShip;
import com.fogok.spaceships.view.utils.NativeGdxHelper;

public class GameSession {

    private SpriteBatch gameSpriteBatch;
    private NetworkData _networkData;

    private ViewModelObject[] viewModelObjects;

    private ControllerManager _controllerManager;

    public GameSession(ControllerManager controllerManager, NetworkData networkData) {
        _networkData = networkData;
        _controllerManager = controllerManager;
        gameSpriteBatch = new SpriteBatch();

        viewModelObjects = new ViewModelObject[2];

        viewModelObjects[0] = new Background(controllerManager);
        viewModelObjects[1] = new SpaceShip(controllerManager);
    }

    public void draw(NativeGdxHelper nativeGdxHelper) {
        _controllerManager.getCameraController().handle(false);
        gameSpriteBatch.setProjectionMatrix(nativeGdxHelper.getGameSessionCamera().combined);
        gameSpriteBatch.begin();
        for (ViewModelObject viewModelObject : viewModelObjects)
            viewModelObject.draw(gameSpriteBatch);
        gameSpriteBatch.end();
    }

    public void dispose() {
        gameSpriteBatch.dispose();
    }
}
