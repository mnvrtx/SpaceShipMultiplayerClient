package com.fogok.spaceships.control.game.weapons.bullets;

import com.badlogic.gdx.utils.Array;
import com.fogok.spaceships.control.game.weapons.Weapon;
import com.fogok.spaceships.view.utils.DebugGUI;
import com.fogok.spaceships.view.utils.Pool;

public abstract class BulletControllerBase<T extends BulletItselfBase> implements Weapon {

    /*
     * Основа для котроллера любой пульки
     */

    private static final int bufferSize = 30;

    private Class<T> clazz;     //ретурнить генерик не можем, приходтся городить басурманские костыли

    // тут активные пульки
    private final Array<T> activeBullets;

    // тут либгдхсовский пул, вроде норм по оптимизиации говорят ребята, будем юзать
    private final Pool<T> bulletPool = new Pool<T>(bufferSize) {
        @Override
        protected T newObject(){
            try {
                System.out.println("new instance! " + (bulletPool.peak + 1) );
                return clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }
    };

    public BulletControllerBase(Class<T> clazz) {
        this(clazz, bufferSize);
    }

    public BulletControllerBase(Class<T> clazz, int sizeBuffer) {
        this.clazz = clazz;
        activeBullets = new Array<T>(false, sizeBuffer);
    }

    @Override
    public void fire(float x, float y, float speed, int direction){
        T item = bulletPool.obtain();
        item.start(x, y, speed, direction);
        activeBullets.add(item);
        addBulletPostAction(item);

    }

    public abstract void addBulletPostAction(T bullet);

    @Override
    public void handle(boolean pause){
        T item;
        int len = activeBullets.size;
        for (int i = len; --i >= 0;) {
            item = activeBullets.get(i);
            item.process();
            handleOneBullet(item);
            if (!item.isAlive) {
                activeBullets.removeIndex(i);
                bulletPool.free(item);
            }
        }

        DebugGUI.DEBUG_TEXT = "Bullets Status:\n    [#FF5500] Bullets in arena: " + activeBullets.size + "[]";
    }

    public Array<T> getActiveBullets() {
        return activeBullets;
    }

    public abstract void handleOneBullet(T bullet);     //ВНИМАНИЕ: не рефрешить тут буллет, он уже это делает сам в своём handle

}