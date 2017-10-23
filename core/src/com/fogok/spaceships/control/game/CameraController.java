package com.fogok.spaceships.control.game;

import com.fogok.spaceships.control.Controller;
import com.fogok.spaceships.model.game.dataobjects.gameobjects.ships.ShipObjectBase;
import com.fogok.spaceships.view.utils.NativeGdxHelper;

public class CameraController implements Controller{

    private NativeGdxHelper _nativeGdxHelper;
    private ShipObjectBase shipObjectBase;

    public CameraController(NativeGdxHelper nativeGdxHelper, ShipObjectBase shipObjectBase) {
        _nativeGdxHelper = nativeGdxHelper;
        this.shipObjectBase = shipObjectBase;
    }

    @Override
    public void handle(boolean pause) {
        _nativeGdxHelper.getGameSessionCamera().position.set(shipObjectBase.getX(), shipObjectBase.getY(), 0);
        _nativeGdxHelper.getGameSessionCamera().update();
    }
}
