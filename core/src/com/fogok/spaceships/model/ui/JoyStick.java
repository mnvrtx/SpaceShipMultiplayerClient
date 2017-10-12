package com.fogok.spaceships.model.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fogok.spaceships.control.ControllerManager;
import com.fogok.spaceships.control.ui.JoyStickController;
import com.fogok.spaceships.model.ViewModelObject;
import com.fogok.spaceships.view.ui.JoyStickView;

public class JoyStick implements ViewModelObject {

    private JoyStickController joyStickController;
    private JoyStickView joyStickView;

    public JoyStick(ControllerManager controllerManager) {
        joyStickController = controllerManager.getJoyStickController();
        joyStickView = new JoyStickView();
    }

    @Override
    public void draw(SpriteBatch batch) {
        joyStickController.handle(false);
        joyStickView.draw(batch, joyStickController);
    }
}
