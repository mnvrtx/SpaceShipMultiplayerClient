package com.fogok.spaceships.model.game.weapons.bullets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fogok.spaceships.control.ControllerManager;
import com.fogok.spaceships.control.game.weapons.bullets.simplebluster.UnionControllerBlusterObjects;
import com.fogok.spaceships.model.ViewModelObject;
import com.fogok.spaceships.view.game.ShipView;
import com.fogok.spaceships.view.game.weapons.bullets.simplebluster.SimpleBlusterView;

public class Bluster implements ViewModelObject{

    private UnionControllerBlusterObjects blusterController;
    private SimpleBlusterView blusterView;

//    private SpaceShipClientController spaceShipClientController;

    public Bluster(ControllerManager controllerManager) {
        blusterController = controllerManager.getEverybodyObjectsController().getDemolishingObjectsController().getBlusterBulletController();
//        spaceShipClientController = controllerManager.getEverybodyObjectsController().getMsDataController().getSpaceShipClientController();

        blusterView = new SimpleBlusterView();
    }

    @Override
    public void draw(SpriteBatch batch) {
        blusterController.handleClient(false);
//        blusterView.draw(batch, );
    }

    public void fire(ShipView shipView) {
//            blusterController.fire(spaceShipClientController.getX() + (spaceShipView.getSprite().getWidth() - blusterView.getSprite().getWidth()) / 2f, spaceShipClientController.getY() + (spaceShipView.getSprite().getHeight() - blusterView.getSprite().getHeight()) / 2f, 0.2f + spaceShipClientController.getAdditParam(SPEED), (int) spaceShipClientController.getAdditParam(DIRECTION) + 90);

        //TODO: COMPLETE THIS
    }
}
