package com.fogok.spaceships.control.game.weapons.bullets;

import com.badlogic.gdx.utils.JsonValue;
import com.fogok.spaceships.control.Controller;
import com.fogok.spaceships.model.NetworkData;

public abstract class BulletItselfServerBase implements Controller {

    private NetworkData networkData;

    public BulletItselfServerBase(NetworkData networkData) {
        this.networkData = networkData;
    }

    @Override
    public void handle(boolean pause) {
        JsonValue jsonValue = networkData.getResponseJson();
    }
}
