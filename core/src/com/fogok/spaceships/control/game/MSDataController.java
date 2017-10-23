package com.fogok.spaceships.control.game;

import com.fogok.spaceships.control.Controller;
import com.fogok.spaceships.control.game.gameobjects.SpaceShipClientController;
import com.fogok.spaceships.control.ui.JoyStickController;
import com.fogok.spaceships.model.NetworkData;
import com.fogok.spaceships.model.game.dataobjects.GameObjectsType;
import com.fogok.spaceships.utils.gamedepended.EveryBodyPool;

public class MSDataController implements Controller {

    private SpaceShipClientController spaceShipClientController;

    public MSDataController(EveryBodyPool everyBodyPool, NetworkData networkData, JoyStickController joyStickController) {
        spaceShipClientController = new SpaceShipClientController(everyBodyPool.obtain(GameObjectsType.SimpleShip, false), networkData, joyStickController);
    }

    @Override
    public void handle(boolean pause) {

    }

    public SpaceShipClientController getSpaceShipClientController() {
        return spaceShipClientController;
    }
}
