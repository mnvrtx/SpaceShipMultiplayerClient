package com.fogok.spaceships.utils.gamedepended;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {

    private AssetManager manager;
    private static TextureAtlas textureAtlas;
    private static Texture background;

    public Assets() {
        manager = new AssetManager();
        manager.load("atlas1.atlas", TextureAtlas.class);
        manager.finishLoading();
        textureAtlas = manager.get("atlas1.atlas", TextureAtlas.class);
        background = new Texture(Gdx.files.internal("testBack.jpg"));
    }

    public static Texture getBackground() {
        return background;
    }

    public static Sprite getNewSprite(int number){
        return textureAtlas.createSprite(number + "");
    }

    public static TextureRegion getRegion(int number){
        return textureAtlas.findRegion(number + "");
    }

    public void dispose() {
        textureAtlas = null;
        if (manager != null) {
            manager.dispose();
            manager = null;
        }
        background.dispose();
        background = null;
    }

}
