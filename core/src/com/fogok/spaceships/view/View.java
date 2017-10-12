package com.fogok.spaceships.view;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fogok.spaceships.control.Controller;

public interface View {
    void draw(SpriteBatch batch, Controller controller);
    Sprite getSprite();
}
