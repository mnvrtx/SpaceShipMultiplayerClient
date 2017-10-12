package com.fogok.spaceships.model.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fogok.spaceships.control.ControllerManager;
import com.fogok.spaceships.control.game.BackgroundController;
import com.fogok.spaceships.model.ViewModelObject;
import com.fogok.spaceships.view.game.BackgroundView;

public class Background implements ViewModelObject {

    private BackgroundView backgroundView;
    private BackgroundController backgroundController;

    public Background(ControllerManager controllerManager) {
        backgroundView = new BackgroundView();
        backgroundController = controllerManager.getBackgroundController();
    }

    @Override
    public void draw(SpriteBatch batch) {
        backgroundController.handle(false);
        backgroundView.draw(batch, backgroundController);
    }
}
