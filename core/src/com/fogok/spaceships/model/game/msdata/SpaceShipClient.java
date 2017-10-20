package com.fogok.spaceships.model.game.msdata;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fogok.spaceships.control.ControllerManager;
import com.fogok.spaceships.control.game.gameobjects.SpaceShipClientController;
import com.fogok.spaceships.model.ViewModelObject;
import com.fogok.spaceships.model.game.weapons.bullets.Bluster;
import com.fogok.spaceships.view.game.SpaceShipView;


public class SpaceShipClient implements ViewModelObject{

    private SpaceShipView spaceShipView;
    private SpaceShipClientController spaceShipClientController;

    private float test;

    private Bluster bluster;

    public SpaceShipClient(ControllerManager controllerManager) {
        spaceShipView = new SpaceShipView();
        spaceShipClientController = controllerManager.getEverybodyObjectsController().getMsDataController().getSpaceShipClientController();

        bluster = new Bluster(controllerManager);
    }

    @Override
    public void draw(SpriteBatch batch) {
        bluster.draw(batch);    //уничтожить этот метод, все объекты будут отрисовываться в EveryBodyObjectsController

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            test += 0.2f;
            if (test > 1.5f) {
                bluster.fire(spaceShipView);
                test = 0f;
            }
        }

        spaceShipClientController.handle(false);
        spaceShipView.draw(batch, spaceShipClientController);
    }
}
