package com.fogok.spaceships.control.game;

import com.fogok.spaceships.control.Controller;
import com.fogok.spaceships.control.ControllerManager;
import com.fogok.spaceships.control.ui.JoyStickController;
import com.fogok.spaceships.model.NetworkData;
import com.fogok.spaceships.utils.gamedepended.EveryBodyPool;

public class MSDataController implements Controller {



    public MSDataController(ControllerManager controllerManager) {

    }

    @Override
    public void handle(boolean pause) {
        unionController.handleClient(false);
    }

}
