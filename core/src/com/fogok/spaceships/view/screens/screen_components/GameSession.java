package com.fogok.spaceships.view.screens.screen_components;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fogok.spaceships.control.ControllerManager;
import com.fogok.spaceships.model.NetworkData;
import com.fogok.spaceships.model.game.UnionViewModels;
import com.fogok.spaceships.view.utils.NativeGdxHelper;

public class GameSession {

    private SpriteBatch gameSpriteBatch;
    private NetworkData networkData;

    private UnionViewModels unionViewModels;

    private ControllerManager controllerManager;

    public GameSession(NativeGdxHelper nativeGdxHelper, NetworkData networkData) {
        this.networkData = networkData;
        gameSpriteBatch = new SpriteBatch();
        controllerManager = new ControllerManager(networkData, nativeGdxHelper);    //controller manager хранит в себе все контроллеры, в каждую модель передаём его
        unionViewModels = new UnionViewModels(controllerManager); //теперь в controllerManager есть необходимые начальные объекты
        controllerManager.postInitializate();
    }

    public void draw(NativeGdxHelper nativeGdxHelper) {
        networkData.resetSize();
        controllerManager.getCameraController().handle(false);
        gameSpriteBatch.setProjectionMatrix(nativeGdxHelper.getGameSessionCamera().combined);
        gameSpriteBatch.begin();

        unionViewModels.draw(gameSpriteBatch);

        gameSpriteBatch.end();
    }

    public ControllerManager getControllerManager() {
        return controllerManager;
    }

    public void dispose() {
        gameSpriteBatch.dispose();
    }
}
