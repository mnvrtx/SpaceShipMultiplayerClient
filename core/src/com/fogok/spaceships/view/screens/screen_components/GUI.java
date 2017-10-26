package com.fogok.spaceships.view.screens.screen_components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fogok.spaceships.control.ControllerManager;
import com.fogok.spaceships.model.ui.JoyStick;
import com.fogok.spaceships.view.utils.DebugGUI;
import com.fogok.spaceships.view.utils.NativeGdxHelper;

public class GUI {

    private JoyStick joyStick;
    private SpriteBatch uiSpriteBatch;
    private DebugGUI debugGUI;

    public GUI(ControllerManager controllerManager) {
        uiSpriteBatch = new SpriteBatch();
        joyStick = new JoyStick(controllerManager);
        debugGUI = new DebugGUI();
    }

    public void draw(NativeGdxHelper nativeGdxHelper){
        uiSpriteBatch.setProjectionMatrix(nativeGdxHelper.getUiCamera().combined);
        uiSpriteBatch.begin();
        joyStick.draw(uiSpriteBatch);
        debugGUI.draw(uiSpriteBatch);
        uiSpriteBatch.end();
        debugGUI.drawScene();
    }

    public void dispose(){
        uiSpriteBatch.dispose();
        debugGUI.dispose();
    }
}
