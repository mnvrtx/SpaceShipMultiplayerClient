package com.fogok.spaceships.control.game;

import com.badlogic.gdx.math.Vector2;
import com.fogok.spaceships.control.Controller;
import com.fogok.spaceships.view.utils.NativeGdxHelper;

public class CameraController implements Controller{

    private NativeGdxHelper _nativeGdxHelper;
    private SpaceShipController _spaceShipController;

    public CameraController(NativeGdxHelper nativeGdxHelper, SpaceShipController spaceShipController) {
        _nativeGdxHelper = nativeGdxHelper;
        _spaceShipController = spaceShipController;
    }

    @Override
    public void handle(boolean pause) {
        Vector2 positionSpaceShip = _spaceShipController.getPosition();
        _nativeGdxHelper.getGameSessionCamera().position.set(positionSpaceShip.x, positionSpaceShip.y, 0);
        _nativeGdxHelper.getGameSessionCamera().update();
    }
}
