package com.fogok.spaceships.control;

import com.fogok.spaceships.control.game.BackgroundController;
import com.fogok.spaceships.control.game.CameraController;
import com.fogok.spaceships.control.game.EverybodyObjectsController;
import com.fogok.spaceships.control.ui.JoyStickController;
import com.fogok.spaceships.model.NetworkData;
import com.fogok.spaceships.view.utils.NativeGdxHelper;

public class ControllerManager {

    private EverybodyObjectsController everybodyObjectsController;
    private JoyStickController joyStickController;

    private BackgroundController backgroundController;
    private CameraController cameraController;

    public ControllerManager(NetworkData networkData, NativeGdxHelper nativeGdxHelper){
        joyStickController = new JoyStickController(networkData);
        everybodyObjectsController = new EverybodyObjectsController(networkData, joyStickController);
        cameraController = new CameraController(nativeGdxHelper, everybodyObjectsController.getSpaceShipController());
        backgroundController = new BackgroundController();
    }


    //region Getters
    public JoyStickController getJoyStickController() {
        return joyStickController;
    }

    public BackgroundController getBackgroundController() {
        return backgroundController;
    }

    public CameraController getCameraController() {
        return cameraController;
    }

    public EverybodyObjectsController getEverybodyObjectsController() {
        return everybodyObjectsController;
    }
    //endregion
}
