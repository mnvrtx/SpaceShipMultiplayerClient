package com.fogok.spaceships.view.game;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fogok.spaceships.control.Controller;
import com.fogok.spaceships.control.game.gameobjects.SpaceShipController;
import com.fogok.spaceships.utils.Assets;
import com.fogok.spaceships.view.View;
import com.fogok.spaceships.view.utils.AspectRatioHelper;

import static com.fogok.spaceships.control.game.gameobjects.SpaceShipController.AdditParams.*;

public class SpaceShipView implements View {

    private Sprite ship;

    public SpaceShipView() {
        ship = Assets.getNewSprite(0);
    }

    @Override
    public void draw(SpriteBatch batch, Controller controller) {
        SpaceShipController ssController = (SpaceShipController)controller;

        AspectRatioHelper.setSpriteSize(ship, ssController.getAdditParam(SIZE), true);
        ship.setOriginCenter();
        ship.setPosition(ssController.getX(), ssController.getY());
        ship.setRotation(ssController.getAdditParam(DIRECTION));
        ship.draw(batch);
    }

    @Override
    public Sprite getSprite() {
        return ship;
    }
}
