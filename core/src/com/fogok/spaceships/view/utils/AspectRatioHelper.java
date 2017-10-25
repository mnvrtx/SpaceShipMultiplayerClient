package com.fogok.spaceships.view.utils;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class AspectRatioHelper {

    public static void setSpriteSize(Sprite sprite, float targetValue, boolean widthHeight) {
        float width, height;
        if (widthHeight) {
            width = targetValue;
            height = sprite.getHeight() / sprite.getWidth() * width;
        } else {
            height = targetValue;
            width = sprite.getWidth() / sprite.getHeight() * height;
        }
        sprite.setSize(width, height);
    }
}
