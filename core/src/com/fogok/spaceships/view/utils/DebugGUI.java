package com.fogok.spaceships.view.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.JsonReader;
import com.fogok.spaceships.Main;

public class DebugGUI {

    public static boolean DEBUG;
    public static StringBuilder DEBUG_TEXT;
    public static StringBuilder EVERYBODYPOOLVISUAL;
    public static JsonReader jsonReader = new JsonReader();
    private BitmapFont debugFont;

    private int screen = 1;

    public DebugGUI(BitmapFont bitmapFont) {
        DEBUG = true;
        debugFont = bitmapFont;
        DEBUG_TEXT = new StringBuilder("EMPTY_LOG");
        EVERYBODYPOOLVISUAL = new StringBuilder("EMPTY_LOG");
    }

    public void draw(SpriteBatch batch){

        if (Gdx.input.isKeyJustPressed(Input.Keys.P))
            if (++screen > 2)
                screen = 0;

        switch (screen) {
            case 1:
                debugFont.draw(batch, DEBUG_TEXT, 0.3f, Main.HEIGHT - 0.3f);
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
