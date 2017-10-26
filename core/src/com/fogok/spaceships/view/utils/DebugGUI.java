package com.fogok.spaceships.view.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.fogok.spaceships.Main;

public class DebugGUI {

    public static boolean DEBUG;
    public static StringBuilder DEBUG_TEXT;
    public static StringBuilder EVERYBODYPOOLVISUAL;
    private int screen;
    private BitmapFont debugFont;
    private Stage stage;

    public DebugGUI() {
        DEBUG = true;
        debugFont = new BitmapFont();
        debugFont.setColor(Color.WHITE);
        debugFont.getData().setScale(0.03f, 0.03f);
        debugFont.setUseIntegerPositions(false);
        debugFont.getData().markupEnabled = true;
        debugFont.getRegion(0).getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        DEBUG_TEXT = new StringBuilder("EMPTY_LOG");
        EVERYBODYPOOLVISUAL = new StringBuilder("EMPTY_LOG");
    }

    public void draw(SpriteBatch batch){

        if (Gdx.input.isKeyJustPressed(Input.Keys.P))
            if (++screen > 2)
                screen = 0;

        switch (screen) {
            case 1:
                debugFont.draw(batch, DEBUG_TEXT, 0.3f, Main.HEIGHT - 0.3f, Main.WIDTH, Align.left, true);
                break;
            case 2:
                debugFont.draw(batch, EVERYBODYPOOLVISUAL.toString(), 0.3f, Main.HEIGHT - 0.3f);
                break;
        }
    }

    public void drawScene(){

    }

    public void dispose(){
        debugFont.dispose();
    }
}
