package com.fogok.spaceships.control.game.weapons.bullets;

import com.badlogic.gdx.utils.Array;
import com.fogok.spaceships.model.game.dataobjects.BulletObject;
import com.fogok.spaceships.model.game.dataobjects.GameObjectsType;
import com.fogok.spaceships.control.game.weapons.Weapon;
import com.fogok.spaceships.model.NetworkData;
import com.fogok.spaceships.utils.gamedepended.EveryBodyPool;
import com.fogok.spaceships.view.utils.DebugGUI;

public abstract class BulletControllerBase<T extends BulletObject, E extends BulletItselfBaseController> implements Weapon {

    /*
     * Основа для котроллера любой пульки
     */

    private static final int bufferSize = 30;

    // тут активные пульки
    private final Array<T> activeBullets;

    private NetworkData networkData;

    private E bulletItselfBaseController;

    // тут либгдхсовский пул
    private final EveryBodyPool everyBodyPool;

    public BulletControllerBase(GameObjectsType gameObjectsType, EveryBodyPool everyBodyPool, NetworkData networkData) {
        this(gameObjectsType, everyBodyPool, networkData, bufferSize);
    }

    public BulletControllerBase(/*TODO: DELETE THIS PRM*/GameObjectsType gameObjectsType, EveryBodyPool everyBodyPool, NetworkData networkData, int sizeBuffer) {
        this.networkData = networkData;
        this.everyBodyPool = everyBodyPool;
        activeBullets = new Array<T>(false, sizeBuffer);

    }

    @Override
    public void fire(float x, float y, float speed, int direction){
        @SuppressWarnings("unchecked")
        T item = (T) everyBodyPool.obtain(GameObjectsType.Bluster);
        bulletItselfBaseController.setBulletObject(item);
        bulletItselfBaseController.start(x, y, speed, direction);
        activeBullets.add(item);
        addBulletPostAction(item);
    }

    public abstract void addBulletPostAction(T bullet);

    @Override
    public void handle(boolean pause){
        T bullet;
        int len = activeBullets.size;
        for (int i = len; --i >= 0;) {
            bullet = activeBullets.get(i);
            bulletItselfBaseController.setBulletObject(bullet);
            bulletItselfBaseController.handle(pause);
            handleOneBullet(bullet);
            if (!bulletItselfBaseController.isAlive()) {
                activeBullets.removeIndex(i);
                everyBodyPool.free(bullet);
            }else{
                //if bullet is alive, then add itself to networkdata
                networkData.addObject(bullet);
            }
        }

        DebugGUI.DEBUG_TEXT = "Bullets Status:" +
                "\n    [#FF5500] Bullets in arena: " + activeBullets.size + "[]" +
                "\n    [#FF5500] Peak objects in pool: " + everyBodyPool.peak + "[]";
    }

    public Array<T> getActiveBullets() {
        return activeBullets;
    }

    public abstract void handleOneBullet(T bullet);     //ВНИМАНИЕ: не рефрешить тут буллет, он уже это делает сам в своём handle

}