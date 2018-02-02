package com.fogok.spaceships.view.screens.game_session;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.fogok.dataobjects.PlayerData;
import com.fogok.dataobjects.gameobjects.ConsoleState;
import com.fogok.dataobjects.utils.Serialization;
import com.fogok.spaceships.net.NetRootController;
import com.fogok.spaceships.view.utils.DebugGUI;
import com.fogok.spaceships.view.utils.NativeGdxHelper;

import java.net.SocketException;
import java.net.UnknownHostException;

public class GameProcess implements Screen {

    private NativeGdxHelper nativeGdxHelper;
    private GUI gui;
    private GameSession gameSession;
    private PlayerData playerData;

    public GameProcess(NativeGdxHelper nativeGdxHelper, NetRootController netRootController) {
        netRootController.setToken("qweqweqwdfqwfjq");

        this.nativeGdxHelper = nativeGdxHelper;
        playerData = new PlayerData(new ConsoleState());
        Serialization.instance.setPlayerData(playerData);

        gameSession = new GameSession(nativeGdxHelper, playerData);
        Serialization.instance.setEveryBodyPoolToSync(gameSession.getControllerManager().getEveryBodyObjectsPool());

        gui = new GUI(nativeGdxHelper, gameSession.getControllerManager());

        try {
            netRootController.getNetPvpController().connectToPvp("qweqwdgqfqwf");
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.02f, 0.02f, 0.02f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        info("TEST");
        gameSession.draw(nativeGdxHelper);
        if (DebugGUI.DEBUG) {
            DebugGUI.DEBUG_TEXT.setLength(0);
            DebugGUI.DEBUG_TEXT.append(gameSession.getControllerManager().getEveryBodyObjectsPool().toString(true));
        }
        gui.draw(nativeGdxHelper);
    }

    @Override
    public void resize(int width, int height) {

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
