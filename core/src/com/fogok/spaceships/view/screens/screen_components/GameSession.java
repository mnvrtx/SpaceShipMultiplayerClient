package com.fogok.spaceships.view.screens.screen_components;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fogok.spaceships.control.ControllerManager;
import com.fogok.spaceships.model.NetworkData;
import com.fogok.spaceships.model.ViewModelObject;
import com.fogok.spaceships.model.game.Background;
import com.fogok.spaceships.model.game.msdata.SpaceShipClient;
import com.fogok.spaceships.model.game.opdata.SpaceShipServer;
import com.fogok.spaceships.view.utils.NativeGdxHelper;

public class GameSession {

    private SpriteBatch gameSpriteBatch;
    private NetworkData networkData;

    private ViewModelObject[] viewModelObjects;

    private ControllerManager _controllerManager;

    public GameSession(ControllerManager controllerManager, NetworkData networkData) {
        this.networkData = networkData;
        _controllerManager = controllerManager;
        gameSpriteBatch = new SpriteBatch();

        viewModelObjects = new ViewModelObject[3];

        viewModelObjects[0] = new Background(controllerManager);
        viewModelObjects[1] = new SpaceShipClient(controllerManager);
        viewModelObjects[2] = new SpaceShipServer(networkData);
    }

    public void draw(NativeGdxHelper nativeGdxHelper) {
        networkData.resetSize();
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
