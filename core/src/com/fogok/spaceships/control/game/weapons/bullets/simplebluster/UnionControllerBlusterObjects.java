package com.fogok.spaceships.control.game.weapons.bullets.simplebluster;

import com.fogok.spaceships.control.ControllerManager;
import com.fogok.spaceships.control.game.weapons.bullets.UnionControllerBulletObjectsBase;
import com.fogok.spaceships.model.NetworkData;
import com.fogok.dataobjects.GameObjectsType;
import com.fogok.dataobjects.weapons.SimpleBlusterObject;

public class UnionControllerBlusterObjects extends UnionControllerBulletObjectsBase<SimpleBlusterObject, BlusterObjectController> {

    /*
     * Контроллер бластера
     */

    public UnionControllerBlusterObjects(ControllerManager controllerManager, NetworkData networkData) {
        super(GameObjectsType.SimpleBluster, controllerManager, new BlusterObjectController(), networkData);
    }

    @Override
    public void addBulletPostAction(SimpleBlusterObject bullet) {

    }

    @Override
    public void handleOneBullet(SimpleBlusterObject bullet) {

    }
}
