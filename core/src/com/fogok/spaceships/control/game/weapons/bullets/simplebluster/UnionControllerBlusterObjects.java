package com.fogok.spaceships.control.game.weapons.bullets.simplebluster;

import com.fogok.spaceships.control.game.weapons.bullets.UnionControllerBulletObjectsBase;
import com.fogok.spaceships.model.NetworkData;
import com.fogok.spaceships.model.game.dataobjects.weapons.BlusterObject;
import com.fogok.spaceships.model.game.dataobjects.GameObjectsType;
import com.fogok.spaceships.utils.gamedepended.EveryBodyPool;

public class UnionControllerBlusterObjects extends UnionControllerBulletObjectsBase<BlusterObject, BlusterObjectController> {

    /*
     * Контроллер бластера
     */

    public UnionControllerBlusterObjects(EveryBodyPool everyBodyPool, NetworkData networkData) {
        super(GameObjectsType.SimpleBluster, everyBodyPool, new BlusterObjectController(), networkData);
    }

    @Override
    public void addBulletPostAction(BlusterObject bullet) {

    }

    @Override
    public void handleOneBullet(BlusterObject bullet) {

    }
}
