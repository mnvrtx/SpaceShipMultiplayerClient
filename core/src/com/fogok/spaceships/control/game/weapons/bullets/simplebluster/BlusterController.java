package com.fogok.spaceships.control.game.weapons.bullets.simplebluster;

import com.fogok.spaceships.model.game.dataobjects.BlusterObject;
import com.fogok.spaceships.model.game.dataobjects.GameObjectsType;
import com.fogok.spaceships.control.game.weapons.bullets.BulletControllerBase;
import com.fogok.spaceships.model.NetworkData;
import com.fogok.spaceships.utils.gamedepended.EveryBodyPool;

public class BlusterController extends BulletControllerBase<BlusterObject, BlusterBulletControllerController>{

    /*
     * Контроллер бластера
     */

    public BlusterController(EveryBodyPool everyBodyPool, NetworkData networkData) {
        super(GameObjectsType.Bluster, everyBodyPool, networkData);
    }

    @Override
    public void addBulletPostAction(BlusterObject bullet) {

    }

    @Override
    public void handleOneBullet(BlusterObject bullet) {

    }
}
