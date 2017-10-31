package com.fogok.spaceships.control.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.fogok.spaceships.control.ControllerManager;
import com.fogok.spaceships.model.NetworkData;
import com.fogok.spaceships.model.game.dataobjects.GameObject;
import com.fogok.spaceships.model.game.dataobjects.GameObjectsType;
import com.fogok.spaceships.utils.gamedepended.EveryBodyPool;
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

    public void handleComplex(NetworkData networkData, boolean pause){
        handleClient(pause);
        JsonValue jsonValue = networkData.getResponseJson(objectType);
        if (jsonValue != null)
            handleServer(jsonValue, pause);
    }

    public void handleClient(boolean pause) {
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

    /**
     * Обработка серверных данных
     * @param jsonValue в этом массиве массив ровно тех объектов, которые относятся к objectType
     */
    public void handleServer(JsonValue jsonValue, boolean pause) {
        int len = jsonValue.size;
        Array<GameObject> activeObjects = everyBodyPool.getAllObjectsFromType(objectType);
        balanceServerResponseObjects(len, activeObjects);

        int jsonIters = 0;
        int aoLen = activeObjects.size;

        for (int i = aoLen; --i >= 0;)
            if (activeObjects.get(i).isServer()){
                handleServerOneObject(jsonValue.get(jsonIters), activeObjects.get(i));
                jsonIters++;
            }

    }

    protected void balanceServerResponseObjects(int targetLenght, Array<GameObject> activeObjects){  //TODO: test this method
        int currLenght = everyBodyPool.getClientServerObjectsCount(objectType, true);
        if (targetLenght > currLenght)  // если нужное число больше текущего - надо добавить объектов
            for (int i = 0; i < targetLenght - currLenght; i++)
                everyBodyPool.obtain(objectType, true);
        else if (targetLenght < currLenght){    //если нужное число меньше текущего - надо делитнуть лишнее
            int currentDeletedObjects = 0, targetDeletedObjects = currLenght - targetLenght;
            int len = activeObjects.size;
            for (int i = len; --i >= 0;){    //проходимся по всем абсолютно объектам
                GameObject gameObject = activeObjects.get(i);
                if (gameObject.isServer()) {    //если объект серверный делитим егo
                    everyBodyPool.free(gameObject);
                    currentDeletedObjects++;    //инкрементим число делитнутых объектов
                    if (currentDeletedObjects == targetDeletedObjects)  //если делитнули нужное кол-во
                        break;                                          //выходим нах!
                }
            }
        }
    }

    protected void handleClientNetworkLogic(GameObject handledObject, boolean isInsideField, int i){
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

    /**
     * Значит этот метод вызывается в цикле (тобишь тупо проходим по всем объектам)
     * @param referenceObject объект, который пришёл с сервера, передаём его, чтобы с ним работать
     * @param handledServerObject непосредственно тот объект, с котороым работаем. Внутри метода
     *                            надо будет в него пихать нужные данные
     */
    protected void handleServerOneObject(JsonValue referenceObject, GameObject handledServerObject){
        //TODO: здесь логика перевода любого объекта в GameObject
        if (referenceObject.has(NetworkData.JSONStrings[GameObject.BOOLEANS]))
            handledServerObject.setLongFlags(referenceObject.getLong(GameObject.BOOLEANS));
        if (referenceObject.has(NetworkData.JSONStrings[GameObject.ADIITPRMS])) {
            JsonValue jsonValue = referenceObject.get(GameObject.ADIITPRMS);
            for (int i = 0; i < jsonValue.size; i++)
                handledServerObject.setAdditParam(referenceObject.get(GameObject.ADIITPRMS).getFloat(i), i);
        }

        handledServerObject.setPosition(referenceObject.getFloat(GameObject.X), referenceObject.getFloat(GameObject.Y));
        handledServerObject.setType(objectType);
    }
}
