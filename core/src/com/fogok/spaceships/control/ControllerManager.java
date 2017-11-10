package com.fogok.spaceships.control;

import com.fogok.dataobjects.utils.EveryBodyPool;
import com.fogok.spaceships.control.other.BackgroundController;
import com.fogok.spaceships.control.ui.CameraController;
import com.fogok.spaceships.control.ui.JoyStickController;
import com.fogok.spaceships.model.NetworkData;
import com.fogok.spaceships.view.game.EveryBodyViews;
import com.fogok.spaceships.view.utils.NativeGdxHelper;

public class ControllerManager implements Controller {

    private NativeGdxHelper nativeGdxHelper;
    private EveryBodyViews everyBodyViews;

    //region Pool system
    public static final int bufferSize = 100;

    private final EveryBodyPool everyBodyObjectsPool;
    //endregion

//    private EverybodyObjectsController everybodyObjectsController;

    private JoyStickController joyStickController;
    private BackgroundController backgroundController;
    private CameraController cameraController;

    public ControllerManager(EveryBodyViews everyBodyViews, NativeGdxHelper nativeGdxHelper, NetworkData networkData){
        this.nativeGdxHelper = nativeGdxHelper;
        this.everyBodyViews = everyBodyViews;
        everyBodyObjectsPool = new EveryBodyPool(bufferSize);

        joyStickController = new JoyStickController(networkData);
//        everybodyObjectsController = new EverybodyObjectsController(this, networkData);   //на этом этапе тут объектов нет
        backgroundController = new BackgroundController();
    }


    @Override
    public void handle(boolean pause) {
//        everybodyObjectsController.handle(false);
    }

//    public void postInitialization() {    //здесь иниициализируем все, где нам нужжны уже созданные объекты модели
//        cameraController = new CameraController(nativeGdxHelper, (SimpleShipObject) everyBodyObjectsPool.getAllObjectsFromType(GameObjectsType.SimpleShip).get(0));
//    }

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
