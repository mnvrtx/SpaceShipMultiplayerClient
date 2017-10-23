package com.fogok.spaceships.view.game;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fogok.spaceships.control.Controller;
import com.fogok.spaceships.utils.gamedepended.Assets;
import com.fogok.spaceships.view.View;

public class SpaceShipView implements View {

    //TODO: рефактор всего этого пакета, в соотвествии с контроллерами (Всего пакета view.game)

    private Sprite ship;

    public SpaceShipView() {
        ship = Assets.getNewSprite(0);
    }

    @Override
    public void draw(SpriteBatch batch, Controller controller) {
//        SpaceShipServerController ssController = (SpaceShipServerController)controller;
//
//        AspectRatioHelper.setSpriteSize(ship, ssController.getAdditParam(SIZE), true);
//        ship.setOriginCenter();
//        ship.setPosition(ssController.getX(), ssController.getY());
//        ship.setRotation(ssController.getAdditParam(DIRECTION));
//        ship.draw(batch);
    }

    @Override
    public Sprite getSprite() {
        return ship;
    }
}
