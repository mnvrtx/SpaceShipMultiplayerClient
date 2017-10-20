package com.fogok.spaceships;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.fogok.spaceships.utils.gamedepended.Assets;
import com.fogok.spaceships.view.screens.GameScreen;

public class Main extends Game {

    private Assets assets;
    private Screen gameScreen;

    public static float WIDTH, HEIGHT, DGNL;
    public static float mdT;    //Gdx.graphics.getDeltaTime in percents

    @Override
    public void create() {
        assets = new Assets();
        gameScreen = new GameScreen();
        setScreen(gameScreen);
    }

    @Override
    public void render() {
        super.render();
        mdT = Math.min(Gdx.graphics.getDeltaTime() / 0.016f, 1.5f);
    }

    @Override
    public void dispose() {
        super.dispose();
        gameScreen.dispose();
        assets.dispose();
    }
}
