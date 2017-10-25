package com.fogok.spaceships.view.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fogok.spaceships.control.Controller;
import com.fogok.spaceships.control.ui.JoyStickController;
import com.fogok.spaceships.utils.gamedepended.Assets;
import com.fogok.spaceships.view.utils.CORDCONV;

public class JoyStickView{

    private Sprite coverJoyStick, joyStick;

    public JoyStickView() {
        coverJoyStick = Assets.getNewSprite(1);
        joyStick = Assets.getNewSprite(2);
    }


    public void draw(SpriteBatch batch, Controller controller) {
        JoyStickController jController = (JoyStickController) controller;
        coverJoyStick.setBounds(CORDCONV.gCamX(jController.posXJoystick), CORDCONV.gCamY(jController.posYJoystick), CORDCONV.gCamX(jController.sizeBackJoystick), CORDCONV.gCamX(jController.sizeBackJoystick));
        coverJoyStick.draw(batch);
        joyStick.setBounds(CORDCONV.gCamX((int) jController.touchedXJoystick), CORDCONV.gCamY((int) jController.touchedYJoystick), CORDCONV.gCamX(jController.sizeJoystick), CORDCONV.gCamX(jController.sizeJoystick));
        joyStick.draw(batch);
    }

    public Sprite getSprite() {
        return coverJoyStick;
    }
}
