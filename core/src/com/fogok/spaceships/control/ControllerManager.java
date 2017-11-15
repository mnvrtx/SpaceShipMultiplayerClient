package com.fogok.spaceships.control;

import com.fogok.dataobjects.gameobjects.ConsoleState;
import com.fogok.dataobjects.utils.EveryBodyPool;
import com.fogok.spaceships.control.other.BackgroundController;
import com.fogok.spaceships.control.ui.CameraController;
import com.fogok.spaceships.control.ui.JoyStickController;
import com.fogok.spaceships.view.game.EveryBodyViews;
import com.fogok.spaceships.view.utils.NativeGdxHelper;

public class ControllerManager implements Controller {

    private EveryBodyViews everyBodyViews;

    //region Pool system
    public static final int bufferSize = 100;

    private final EveryBodyPool everyBodyObjectsPool;
    //endregion

    private JoyStickController joyStickController;
    private BackgroundController backgroundController;
    private CameraController cameraController;

    public ControllerManager(EveryBodyViews everyBodyViews, NativeGdxHelper nativeGdxHelper, ConsoleState consoleState){
        this.everyBodyViews = everyBodyViews;

        everyBodyObjectsPool = new EveryBodyPool(bufferSize);

        joyStickController = new JoyStickController(consoleState);
        backgroundController = new BackgroundController();
        cameraController = new CameraController(nativeGdxHelper/*, (SimpleShipObject) everyBodyObjectsPool.getAllObjectsFromType(GameObjectsType.SimpleShip).get(0)*/);
    }


    @Override
    public void handle(boolean pause) {

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

    public EveryBodyViews getEveryBodyViews() {
        return everyBodyViews;
    }

    public EveryBodyPool getEveryBodyObjectsPool() {
        return everyBodyObjectsPool;
    }

//    public EverybodyObjectsController getEverybodyObjectsController() {
//        return everybodyObjectsController;
//    }
    //endregion

    public void dispose(){
        everyBodyObjectsPool.clear();
    }
}
