package com.fogok.spaceships.control.game;

import com.fogok.spaceships.control.Controller;
import com.fogok.spaceships.view.utils.NativeGdxHelper;

public class CameraController implements Controller{

    private NativeGdxHelper _nativeGdxHelper;
    private com.fogok.spaceships.control.game.gameobjects.SpaceShipController spaceShipController;

    public CameraController(NativeGdxHelper nativeGdxHelper, com.fogok.spaceships.control.game.gameobjects.SpaceShipController spaceShipController) {
        _nativeGdxHelper = nativeGdxHelper;
        this.spaceShipController = spaceShipController;
    }

    @Override
    public void handle(boolean pause) {
        _nativeGdxHelper.getGameSessionCamera().position.set(spaceShipController.getX(), spaceShipController.getY(), 0);
        _nativeGdxHelper.getGameSessionCamera().update();
    }
}
