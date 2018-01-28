package com.fogok.spaceships.view.screens.game_session;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fogok.spaceships.control.ControllerManager;
import com.fogok.spaceships.model.ui.JoyStick;
import com.fogok.spaceships.view.utils.DebugGUI;
import com.fogok.spaceships.view.utils.NativeGdxHelper;

public class GUI {

    private JoyStick joyStick;
    private DebugGUI debugGUI;
    private SpriteBatch uiSpriteBatch;

    public GUI(NativeGdxHelper nativeGdxHelper, ControllerManager controllerManager) {
        uiSpriteBatch = nativeGdxHelper.getUiSpriteBatch();
        joyStick = new JoyStick(controllerManager);
        debugGUI = new DebugGUI(nativeGdxHelper.getUiBitmapFont());
    }

    public void draw(NativeGdxHelper nativeGdxHelper){
        uiSpriteBatch.setProjectionMatrix(nativeGdxHelper.getUiCamera().combined);
        uiSpriteBatch.begin();
        joyStick.draw(uiSpriteBatch);
        debugGUI.draw(uiSpriteBatch);
        uiSpriteBatch.end();
        debugGUI.drawScene();
    }

    public SpriteBatch getUiSpriteBatch() {
        return uiSpriteBatch;
    }

    public void dispose(){
        debugGUI.dispose();
    }
}
