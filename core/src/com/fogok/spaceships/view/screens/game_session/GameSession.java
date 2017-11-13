package com.fogok.spaceships.view.screens.game_session;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fogok.dataobjects.PlayerData;
import com.fogok.spaceships.control.ControllerManager;
import com.fogok.spaceships.model.game.EveryBodyViewModels;
import com.fogok.spaceships.view.game.EveryBodyViews;
import com.fogok.spaceships.view.utils.NativeGdxHelper;

public class GameSession {

    private SpriteBatch gameSpriteBatch;

    private EveryBodyViews everyBodyViews;

    private EveryBodyViewModels everyBodyViewModels;

    private ControllerManager controllerManager;

    public GameSession(NativeGdxHelper nativeGdxHelper, PlayerData playerData) {
        gameSpriteBatch = new SpriteBatch();

        everyBodyViews = new EveryBodyViews();
        controllerManager = new ControllerManager(everyBodyViews, nativeGdxHelper, playerData.getConsoleState());    //контроллерманагер реализует контроль объектов, которые отображаются локально
        everyBodyViewModels = new EveryBodyViewModels(controllerManager);   //теперь в controllerManager есть необходимые начальные объекты

    }

    public void draw(NativeGdxHelper nativeGdxHelper) {
        controllerManager.getCameraController().handle(false);
        gameSpriteBatch.setProjectionMatrix(nativeGdxHelper.getGameSessionCamera().combined);
        gameSpriteBatch.begin();

        controllerManager.handle(false);
        everyBodyViewModels.draw(gameSpriteBatch);

        gameSpriteBatch.end();
    }

    public ControllerManager getControllerManager() {
        return controllerManager;
    }

    public void dispose() {
        gameSpriteBatch.dispose();
        controllerManager.dispose();
    }
}
