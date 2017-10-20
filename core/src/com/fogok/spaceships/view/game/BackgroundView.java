package com.fogok.spaceships.view.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fogok.spaceships.control.Controller;
import com.fogok.spaceships.control.game.BackgroundController;
import com.fogok.spaceships.utils.gamedepended.Assets;
import com.fogok.spaceships.view.View;
import com.fogok.spaceships.view.utils.AspectRatioHelper;

public class BackgroundView implements View{

    private Sprite background;

    public BackgroundView() {
        Assets.getBackground().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        background = new Sprite(Assets.getBackground());
        AspectRatioHelper.setSpriteSize(background, 40, false);
    }

    @Override
    public void draw(SpriteBatch batch, Controller controller) {
        BackgroundController backgroundController = (BackgroundController) controller;
        background.draw(batch);
    }

    @Override
    public Sprite getSprite() {
        return background;
    }
}
