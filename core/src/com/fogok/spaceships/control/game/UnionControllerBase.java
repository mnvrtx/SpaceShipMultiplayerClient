package com.fogok.spaceships.control.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.fogok.dataobjects.GameObject;
import com.fogok.dataobjects.GameObjectsType;
import com.fogok.dataobjects.utils.EveryBodyPool;
import com.fogok.dataobjects.utils.libgdxexternals.Array;
import com.fogok.spaceships.control.ControllerManager;
import com.fogok.spaceships.model.NetworkData;
import com.fogok.spaceships.view.game.EveryBodyViews;
import com.fogok.spaceships.view.utils.DebugGUI;

public abstract class UnionControllerBase {

    /*
     * Основа для любого union контроллера. Здесь мы инициализируем базовые переменные +
     * основа для обработки каждого объекта, серверные и клиентские варианты. Важный момент: логики
     * добавления тут вообще нет, т.к. она слишком отличается для каждой конкретной реализации,
     * но вот то, что нам нужно будет работать с каждым объектом это точно на 99.9 (в крайнем
     * случае всегда можно переопределить любой метод)
     *
     * Единственное, есть balanceServerResponseObjects, который добавляет/удаляет в пуле нужные серверные
     * объекты
     */

    protected NetworkData networkData;
    protected EveryBodyPool everyBodyPool;
    protected EveryBodyViews everyBodyViews;
    protected GameObjectsType objectType;

    public UnionControllerBase(GameObjectsType objectType, ControllerManager controllerManager, NetworkData networkData) {
        this.networkData = networkData;
        this.everyBodyPool = controllerManager.getEveryBodyObjectsPool();
        this.everyBodyViews = controllerManager.getEveryBodyViews();
        this.objectType = objectType;
    }

    public void handleComplex(boolean pause){
        handleClient(pause);
//        handleAllServerObjects(pause);
    }

    private void handleClient(boolean pause) {
        Array<GameObject> activeObjects = everyBodyPool.getAllObjectsFromType(objectType);
        int len = activeObjects.size;
        for (int i = len; --i >= 0;)
            if (!activeObjects.get(i).isServer())
                handleClientNetworkLogic(activeObjects.get(i), preLogicHandeObjectAndHandleObject(activeObjects.get(i)), i);

        if (DebugGUI.DEBUG)
            handleDebug(everyBodyPool);
    }

    //region Debug
    private void handleDebug(EveryBodyPool everyBodyPool){  ///вызывается трильиарды раз, но мне похер)))0)
        DebugGUI.EVERYBODYPOOLVISUAL.setLength(0);
        for (int i = 0; i < everyBodyPool.getAllObjects().size; i++) {
            Array<GameObject> array = everyBodyPool.getAllObjects().get(i);
            if (array.size != 0) {
                for (int j = 0; j < array.size; j++) {
                    GameObject gameObject = array.get(j);
                    DebugGUI.EVERYBODYPOOLVISUAL.append("[");
                    DebugGUI.EVERYBODYPOOLVISUAL.append(GameObjectsType.values()[gameObject.getType()].name() + (gameObject.isServer() ? "Server" : "Client"));
                    DebugGUI.EVERYBODYPOOLVISUAL.append("]");
                }
                DebugGUI.EVERYBODYPOOLVISUAL.append("\n");
            }
        }
    }

    //endregion

    private boolean preLogicHandeObjectAndHandleObject(GameObject gameObject) {
        final Sprite targetSprite = everyBodyViews.getView(objectType).getSprite();
        gameObject.setWidthDivHeight(targetSprite.getWidth() / targetSprite.getHeight());   //preLogic - тут я тупо даю объекту текущее соотношение сторон
        return handleClientOneObject(gameObject);
    }



    protected void handleClientNetworkLogic(GameObject handledObject, boolean isInsideField, int i){    //TODO: REFACTOR THIS
        Array<GameObject> activeObjects = everyBodyPool.getAllObjectsFromType(objectType);
        if (isInsideField)
            everyBodyPool.free(handledObject);
//        else
//            //if object is not isInsideField, then add itself to networkdata
//            networkData.addObject(handledObject);

    }

    /**
     * Значит этот метод вызывается в цикле (тобишь тупо проходим по всем объектам нужным контроллером.
     * @param handledClientObject объект, по которому прошли
     * @return находится ли объект в игре, или же внутри пула (нужно вернуть true, чтобы добавить объект в пул снова)
     */
    protected abstract boolean handleClientOneObject(GameObject handledClientObject);
}
