package com.fogok.spaceships.view.utils;

import com.badlogic.gdx.Gdx;
import com.fogok.spaceships.Main;

public class CORDCONV {

    public static float gCamX(int pixelX){
        return (Main.WIDTH / Gdx.graphics.getWidth()) * pixelX;
    }

    public static float gCamY(int pixelY) {
        return Main.HEIGHT / Gdx.graphics.getHeight() * pixelY;
    }

}
