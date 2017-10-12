package com.fogok.spaceships.view.game.weapons.bullets.simplebluster;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.fogok.spaceships.control.Controller;
import com.fogok.spaceships.control.game.weapons.bullets.simplebluster.BlusterBulletController;
import com.fogok.spaceships.control.game.weapons.bullets.simplebluster.BlusterController;
import com.fogok.spaceships.utils.Assets;
import com.fogok.spaceships.view.View;
import com.fogok.spaceships.view.utils.AspectRatioHelper;

public class SimpleBlusterView implements View {

    private Sprite bluster;

    public SimpleBlusterView() {
        bluster = Assets.getNewSprite(3);
        AspectRatioHelper.setSpriteSize(bluster, 0.2f, true);
        bluster.setOriginCenter();
    }

    @Override
    public void draw(SpriteBatch batch, Controller controller) {

        Array<BlusterBulletController> array = ((BlusterController)controller).getActiveBullets();
        for (BlusterBulletController blusterBulletController : array) {
            bluster.setPosition(blusterBulletController.getX(), blusterBulletController.getY());
            bluster.setRotation(blusterBulletController.getDirection() - 90);
            bluster.setAlpha(blusterBulletController.getAlpha(0.15f));
            bluster.draw(batch);
        }
    }

    @Override
    public Sprite getSprite() {
        return null;
    }
}
