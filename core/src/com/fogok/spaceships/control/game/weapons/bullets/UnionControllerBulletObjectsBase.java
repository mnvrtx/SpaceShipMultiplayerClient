package com.fogok.spaceships.control.game.weapons.bullets;

import com.fogok.spaceships.control.game.UnionControllerBase;
import com.fogok.spaceships.model.NetworkData;
import com.fogok.spaceships.model.game.dataobjects.GameObject;
import com.fogok.spaceships.model.game.dataobjects.GameObjectsType;
import com.fogok.spaceships.model.game.dataobjects.weapons.BulletObjectBase;
import com.fogok.spaceships.utils.gamedepended.EveryBodyPool;

public abstract class UnionControllerBulletObjectsBase<T extends BulletObjectBase, E extends BulletObjectControllerBase> extends UnionControllerBase {

    /*
     * Основа для контроллера любой коллекции пулек
     */

    private E bulletObjectController;

    public UnionControllerBulletObjectsBase(GameObjectsType objectType, EveryBodyPool everyBodyPool, E bulletObjectController, NetworkData networkData) {
        super(objectType, everyBodyPool, networkData);
        this.bulletObjectController = bulletObjectController;
    }

    public void fire(float x, float y, float speed, int direction){
        @SuppressWarnings("unchecked")
        T item = (T) everyBodyPool.obtain(objectType, false);
        bulletObjectController.setHandledObject(item);
        bulletObjectController.fire(x, y, speed, direction);
        addBulletPostAction(item);
    }

    @Override
    protected boolean handleClientOneObject(GameObject handledClientObject) {
        @SuppressWarnings("unchecked")
        T bullet = (T) handledClientObject;   //приводим к нужному нам типу
        bulletObjectController.setHandledObject(bullet);
        bulletObjectController.handleClient(false);     //устанавливаем нужный объект нам и делаем с ним то, чё нам нужно
        handleOneBullet(bullet);    //делаем чёт с ним дополнительно
        return !bulletObjectController.isAlive();  //возвращаем, на в игре наш объект или в пуле
    }



    //        DebugGUI.DEBUG_TEXT = "Bullets Status:" +
//                "\n    [#FF5500] Bullets in arena: " + everyBodyPool.getAllObjectsFromType(GameObjectsType.SimpleBluster).size + "[]" +
//                "\n    [#FF5500] Peak objects in pool: " + everyBodyPool.peak + "[]";


    public abstract void addBulletPostAction(T bullet);

    public abstract void handleOneBullet(T bullet);     //ВНИМАНИЕ: не рефрешить тут буллет, он уже это делает сам в своём handle


}