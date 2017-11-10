package com.fogok.spaceships.view.game.weapons.bullets;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fogok.dataobjects.GameObject;
import com.fogok.dataobjects.utils.GMUtils;
import com.fogok.spaceships.utils.gamedepended.Assets;
import com.fogok.spaceships.view.View;
import com.fogok.spaceships.view.utils.AspectRatioHelper;

import static com.fogok.dataobjects.gameobjects.weapons.BulletObjectBase.AdditParams.*;


public class SimpleBlusterView implements View {

    private Sprite bluster;
    private final float startFading = 0.15f;

    public SimpleBlusterView() {
        bluster = Assets.getNewSprite(0);
        AspectRatioHelper.setSpriteSize(bluster, 0.5f, true);
        bluster.setOriginCenter();
    }

    @Override
    public void draw(SpriteBatch batch, GameObject gameObject) {
        bluster.setCenter(gameObject.getX(), gameObject.getY());
        bluster.setRotation(gameObject.getAdditParam(DIRECTION) - 90);
        bluster.setAlpha(gameObject.getAdditParam(TIMEALIVE) < startFading ? GMUtils.normalizeOneZero(gameObject.getAdditParam(TIMEALIVE) / startFading) : 1f);
        bluster.draw(batch);
    }

    @Override
    public Sprite getSprite() {
        return bluster;
    }
}
