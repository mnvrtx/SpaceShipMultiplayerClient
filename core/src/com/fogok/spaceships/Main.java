package com.fogok.spaceships;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.fogok.spaceships.net.NetRootController;
import com.fogok.spaceships.utils.gamedepended.Assets;
import com.fogok.spaceships.view.screens.ScreenSwitcher;

public class Main extends Game {

    private NetRootController netRootController;
    private static ScreenSwitcher screenSwitcher;
    private Assets assets;

    public static float WIDTH, HEIGHT, DGNL;
    public static float mdT;    //Gdx.graphics.getDeltaTime in percents

    @Override
    public void create() {
        assets = new Assets();
        netRootController = new NetRootController();
        screenSwitcher = new ScreenSwitcher(netRootController) {
            @Override
            public void flush(Screen screen) {
                Main.this.setScreen(screen);
            }
        };
        screenSwitcher.setCurrentScreen(ScreenSwitcher.Screens.GAMESESSION);
    }

    @Override
    public void render() {
        super.render();
        mdT = Math.min(Gdx.graphics.getDeltaTime() / 0.016f, 1.5f);
    }

    @Override
    public void resize(int width, int height) {
        screenSwitcher.getNativeGdxHelper().resize(width, height);
    }

    public static ScreenSwitcher getScreenSwitcher() {
        return screenSwitcher;
    }

    @Override
    public void dispose() {
        super.dispose();
        if (screenSwitcher != null) {
            screenSwitcher.disposeAll();
            screenSwitcher = null;
        }
        assets.dispose();
    }
}
