package com.fogok.spaceships.model.game.weapons.bullets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fogok.spaceships.control.ControllerManager;
import com.fogok.spaceships.control.game.gameobjects.SpaceShipClientController;
import com.fogok.spaceships.control.game.weapons.bullets.simplebluster.BlusterController;
import com.fogok.spaceships.model.ViewModelObject;
import com.fogok.spaceships.view.game.SpaceShipView;
import com.fogok.spaceships.view.game.weapons.bullets.simplebluster.SimpleBlusterView;

public class Bluster implements ViewModelObject{

    private BlusterController blusterController;
    private SimpleBlusterView blusterView;

    private SpaceShipClientController spaceShipClientController;

    public Bluster(ControllerManager controllerManager) {
        blusterController = controllerManager.getEverybodyObjectsController().getDemolishingObjectsController().getBlusterBulletController();
        spaceShipClientController = controllerManager.getEverybodyObjectsController().getMsDataController().getSpaceShipClientController();

        blusterView = new SimpleBlusterView();
    }

    @Override
    public void draw(SpriteBatch batch) {
        blusterController.handle(false);
        blusterView.draw(batch, blusterController);
    }

    public void fire(SpaceShipView spaceShipView) {
//            blusterController.fire(spaceShipClientController.getX() + (spaceShipView.getSprite().getWidth() - blusterView.getSprite().getWidth()) / 2f, spaceShipClientController.getY() + (spaceShipView.getSprite().getHeight() - blusterView.getSprite().getHeight()) / 2f, 0.2f + spaceShipClientController.getAdditParam(SPEED), (int) spaceShipClientController.getAdditParam(DIRECTION) + 90);

        //TODO: COMPLETE THIS
    }
}
