package com.fogok.spaceships.model.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.fogok.spaceships.control.ControllerManager;
import com.fogok.spaceships.control.game.SpaceShipController;
import com.fogok.spaceships.control.game.weapons.Weapon;
import com.fogok.spaceships.control.game.weapons.bullets.simplebluster.BlusterBulletController;
import com.fogok.spaceships.control.game.weapons.bullets.simplebluster.BlusterController;
import com.fogok.spaceships.model.ViewModelObject;
import com.fogok.spaceships.utils.Assets;
import com.fogok.spaceships.view.game.SpaceShipView;
import com.fogok.spaceships.view.utils.AspectRatioHelper;


public class SpaceShip implements ViewModelObject{

    private SpaceShipView spaceShipView;
    private SpaceShipController spaceShipController;

    private Weapon blusterController;


    public SpaceShip(ControllerManager controllerManager) {
        spaceShipView = new SpaceShipView();
        spaceShipController = controllerManager.getSpaceShipController();

        blusterController = controllerManager.getEverybodyObjectsController().getDemolishingObjectsController().getBlusterBulletController();
    }

    @Override
    public void draw(SpriteBatch batch) {
        blusterController.handle(false);

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            blusterController.fire(spaceShipController.getPosition().x + (spaceShipView.getSprite().getWidth() - test.getWidth()) / 2f, spaceShipController.getPosition().y + (spaceShipView.getSprite().getHeight() - test.getHeight()) / 2f, 0.2f + spaceShipController.getCurrentSpeed(), (int) spaceShipController.getCurrentDirection() + 90);




        spaceShipController.handle(false);
        spaceShipView.draw(batch, spaceShipController);

    }
}
