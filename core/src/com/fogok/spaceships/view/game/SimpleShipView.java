package com.fogok.spaceships.view.game;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fogok.spaceships.model.game.dataobjects.GameObject;
import com.fogok.spaceships.utils.gamedepended.Assets;
import com.fogok.spaceships.view.View;
import com.fogok.spaceships.view.utils.AspectRatioHelper;

import static com.fogok.spaceships.model.game.dataobjects.gameobjects.ships.ShipObjectBase.AdditParams.*;

public class SimpleShipView implements View {

    //TODO: рефактор всего этого пакета, в соотвествии с контроллерами (Всего пакета view.game)

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
