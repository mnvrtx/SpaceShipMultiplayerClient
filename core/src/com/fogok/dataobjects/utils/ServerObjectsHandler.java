package com.fogok.dataobjects.utils;

import com.fogok.dataobjects.GameObject;
import com.fogok.dataobjects.GameObjectsType;
import com.fogok.dataobjects.utils.libgdxexternals.Array;
import com.fogok.dataobjects.utils.libgdxexternals.JsonValue;

public class ServerObjectsHandler {

    private EveryBodyPool everyBodyPool;
    private GameObjectsType objectType;

    public ServerObjectsHandler(EveryBodyPool everyBodyPool, GameObjectsType objectType) {
        this.everyBodyPool = everyBodyPool;
        this.objectType = objectType;
    }

    /**
     * Обрабатываем всех игроков, и добавляем все их объекты в everyBodyPool
     * @param somePlayersJ json, который имеет в себе несколько  MSD
     */
    private void handleAllServerObjects(JsonValue somePlayersJ){
        if (somePlayersJ != null) {
            balanceServerResponseObjects(calculateSizeAllObjectsTypeFromAllServerPlayers(somePlayersJ),
                    everyBodyPool.getAllObjectsFromType(objectType));
            handleServer(somePlayersJ);
        }
    }

    /**
     * Расчёт количества объектов определённного типа в
     * @param somePlayersJ json, который имеет в себе несколько  MSD
     * @return
     */
    private int calculateSizeAllObjectsTypeFromAllServerPlayers(JsonValue somePlayersJ){
        int resp = 0;
        for (int i = 0; i < somePlayersJ.size; i++) {
            JsonValue playerJ = somePlayersJ.get(i);
            JsonValue objTypeJ = playerJ.get(String.valueOf(objectType.ordinal()));
            if (objTypeJ != null)
                resp += objTypeJ.size;
        }

        return resp;
    }

    /**
     * Проходимся по всем объектам определённого типа внутри всех игроков и обрабатываем их
     * @param somePlayersJ json, который имеет в себе несколько  MSD
     */
    private void handleServer(JsonValue somePlayersJ) {
        Array<GameObject> activeObjects = everyBodyPool.getAllObjectsFromType(objectType);  //все объекты определённого типа
        int currentServerObjectIndex = 0;
        for (int i = 0; i < somePlayersJ.size; i++) {   //проходимся по всем игрокам
            JsonValue playerJ = somePlayersJ.get(i);
            JsonValue objTypeJ = playerJ.get(String.valueOf(objectType.ordinal()));     //берём объекты определённого типа
            if (objTypeJ != null) {     //если эти объекты вообще есть
                for (int j = 0; j < objTypeJ.size; j++) {       //обрабатываем каждый объект

                    for (int k = currentServerObjectIndex; k < activeObjects.size; k++)    //проходимся по всем объектам определённого типа внутри нашего пула, и
                        if (activeObjects.get(k).isServer()) {
                            currentServerObjectIndex = k;
                            break;
                        }

                    handleServerOneObject(objTypeJ.get(j), activeObjects.get(currentServerObjectIndex++));
                }
            }
        }
    }

    /**
     * Этот метод вызывается в цикле (тобишь тупо проходим по всем объектам)
     * @param referenceObject объект, который пришёл с сервера, передаём его, чтобы с ним работать
     * @param handledServerObject непосредственно тот объект, с котороым работаем. Внутри метода
     *                            надо будет в него пихать нужные данные
     */
    protected void handleServerOneObject(JsonValue referenceObject, GameObject handledServerObject){
        //TODO: здесь логика перевода любого объекта в GameObject
        if (referenceObject.has(FastJsonWriter.JSONStrings[GameObject.BOOLEANS]))
            handledServerObject.setLongFlags(referenceObject.getLong(GameObject.BOOLEANS));
        if (referenceObject.has(FastJsonWriter.JSONStrings[GameObject.ADIITPRMS])) {
            JsonValue jsonValue = referenceObject.get(GameObject.ADIITPRMS);
            for (int i = 0; i < jsonValue.size; i++)
                handledServerObject.setAdditParam(referenceObject.get(GameObject.ADIITPRMS).getFloat(i), i);
        }

        handledServerObject.setPosition(referenceObject.getFloat(GameObject.X), referenceObject.getFloat(GameObject.Y));
        handledServerObject.setType(objectType);
    }

    /**
     * Добавляем/удаляем объекты таким образом, чтобы их количество было равным
     * @param targetLenght - количество объектов
     * @param activeObjects - объекты определённого типа
     */
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
}

