package com.fogok.spaceships.view.game.ships;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fogok.dataobjects.GameObject;
import com.fogok.spaceships.utils.gamedepended.Assets;
import com.fogok.spaceships.view.View;
import com.fogok.spaceships.view.utils.AspectRatioHelper;

import static com.fogok.dataobjects.gameobjects.ships.ShipObjectBase.AdditParams.*;

public class SimpleShipView implements View {

    private Sprite ship;

    public SimpleShipView() {
        ship = Assets.getNewSprite(0);
    }

    @Override
    public void draw(SpriteBatch batch, GameObject gameObject) {
        AspectRatioHelper.setSpriteSize(ship, gameObject.getAdditParam(SIZE), true);
        ship.setOriginCenter();
        ship.setPosition(gameObject.getX(), gameObject.getY());
        ship.setRotation(gameObject.getAdditParam(DIRECTION));
        ship.draw(batch);
    }

    @Override
    public Sprite getSprite() {
        return ship;
    }
}
