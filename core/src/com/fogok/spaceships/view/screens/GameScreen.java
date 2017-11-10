package com.fogok.spaceships.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.fogok.spaceships.model.NetworkData;
import com.fogok.spaceships.net.ServerLogicWrapper;
import com.fogok.spaceships.view.screens.screen_components.GUI;
import com.fogok.spaceships.view.screens.screen_components.GameSession;
import com.fogok.spaceships.view.utils.DebugGUI;
import com.fogok.spaceships.view.utils.NativeGdxHelper;

public class GameScreen implements Screen {

    private NetworkData networkData;
    private NativeGdxHelper nativeGdxHelper;
    private GUI gui;
    private GameSession gameSession;

    public GameScreen() {
        nativeGdxHelper = new NativeGdxHelper();
        networkData = new NetworkData();

        gameSession = new GameSession(nativeGdxHelper, networkData);
        gui = new GUI(gameSession.getControllerManager());

        networkData.setTypedObjets(gameSession.getControllerManager().getEveryBodyObjectsPool().getAllObjects());

        ServerLogicWrapper.openServerSocket(networkData);
    }

    @Override
    public void show() {


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.02f, 0.02f, 0.02f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameSession.draw(nativeGdxHelper);
        if (DebugGUI.DEBUG) {
            DebugGUI.DEBUG_TEXT.setLength(0);
//            DebugGUI.DEBUG_TEXT.append(DebugGUI.jsonReader.parse(networkData.getJSON()).prettyPrint(JsonWriter.OutputType.json, 2));
        }
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
