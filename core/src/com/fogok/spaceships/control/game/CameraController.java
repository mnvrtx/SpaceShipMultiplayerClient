package com.fogok.spaceships.control.game;

import com.fogok.spaceships.control.Controller;
import com.fogok.spaceships.control.game.gameobjects.SpaceShipServerController;
import com.fogok.spaceships.view.utils.NativeGdxHelper;

public class CameraController implements Controller{

    private NativeGdxHelper _nativeGdxHelper;
    private SpaceShipServerController spaceShipServerController;

    public CameraController(NativeGdxHelper nativeGdxHelper, SpaceShipServerController spaceShipServerController) {
        _nativeGdxHelper = nativeGdxHelper;
        this.spaceShipServerController = spaceShipServerController;
    }

    @Override
    public void handle(boolean pause) {
        _nativeGdxHelper.getGameSessionCamera().position.set(spaceShipServerController.getX(), spaceShipServerController.getY(), 0);
        _nativeGdxHelper.getGameSessionCamera().update();
    }
}
