package com.fogok.spaceships.model.game.weapons.bullets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fogok.spaceships.control.ControllerManager;
import com.fogok.spaceships.control.game.SpaceShipController;
import com.fogok.spaceships.control.game.weapons.bullets.simplebluster.BlusterController;
import com.fogok.spaceships.model.ViewModelObject;
import com.fogok.spaceships.view.game.SpaceShipView;
import com.fogok.spaceships.view.game.weapons.bullets.simplebluster.SimpleBlusterView;

public class Bluster implements ViewModelObject{

    private BlusterController blusterController;
    private SimpleBlusterView blusterView;

    private SpaceShipController spaceShipController;

    public Bluster(ControllerManager controllerManager) {
        blusterController = controllerManager.getEverybodyObjectsController().getDemolishingObjectsController().getBlusterBulletController();
        spaceShipController = controllerManager.getSpaceShipController();

        blusterView = new SimpleBlusterView();
    }

    @Override
    public void draw(SpriteBatch batch) {
        blusterController.handle(false);
        blusterView.draw(batch, blusterController);
    }

    public void fire(SpaceShipView spaceShipView) {
            blusterController.fire(spaceShipController.getPosition().x + (spaceShipView.getSprite().getWidth() - blusterView.getSprite().getWidth()) / 2f, spaceShipController.getPosition().y + (spaceShipView.getSprite().getHeight() - blusterView.getSprite().getHeight()) / 2f, 0.2f + spaceShipController.getCurrentSpeed(), (int) spaceShipController.getCurrentDirection() + 90);
    }
}
