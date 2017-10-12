package com.fogok.spaceships.view.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fogok.spaceships.Main;

public class DebugGUI {

    public static String DEBUG_TEXT;
    private BitmapFont debugFont;

    public DebugGUI() {
        debugFont = new BitmapFont();
        debugFont.setColor(Color.WHITE);
        debugFont.getData().setScale(0.03f, 0.03f);
        debugFont.setUseIntegerPositions(false);
        debugFont.getData().markupEnabled = true;
        debugFont.getRegion(0).getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        DEBUG_TEXT = "";
    }

    public void draw(SpriteBatch batch){
        debugFont.draw(batch, DEBUG_TEXT, 0.3f, Main.HEIGHT - 0.3f);
    }

    public void dispose(){
        debugFont.dispose();
    }
}
