package com.fogok.spaceships.view;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fogok.spaceships.model.game.dataobjects.GameObject;

public interface View {
    void draw(SpriteBatch batch, GameObject gameObject);
    Sprite getSprite();
}
