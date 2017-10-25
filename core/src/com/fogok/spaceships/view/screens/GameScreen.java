package com.fogok.spaceships.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.fogok.spaceships.model.NetworkData;
import com.fogok.spaceships.view.screens.screen_components.GUI;
import com.fogok.spaceships.view.screens.screen_components.GameSession;
import com.fogok.spaceships.view.utils.NativeGdxHelper;

public class GameScreen implements Screen {

    private NetworkData networkData;
    private NativeGdxHelper nativeGdxHelper;
    private GUI gui;
    private GameSession gameSession;

    @Override
    public void show() {

        nativeGdxHelper = new NativeGdxHelper();
        networkData = new NetworkData();

        gameSession = new GameSession(nativeGdxHelper, networkData);
        gui = new GUI(gameSession.getControllerManager());

//        ServerLogicWrapper.openServerSocket(networkData);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.02f, 0.02f, 0.02f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameSession.draw(nativeGdxHelper);
        gui.draw(nativeGdxHelper);
    }

    @Override
    public void resize(int width, int height) {
        nativeGdxHelper.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        gui.dispose();
        gameSession.dispose();
    }
}
