package com.fogok.spaceships.control.game.gameobjects;

import com.badlogic.gdx.utils.JsonValue;
import com.fogok.spaceships.control.Controller;
import com.fogok.spaceships.control.game.GameObject;
import com.fogok.spaceships.model.NetworkData;

import static com.fogok.spaceships.control.game.gameobjects.SpaceShipController.AdditParams.DIRECTION;
import static com.fogok.spaceships.control.game.gameobjects.SpaceShipController.AdditParams.SIZE;
import static com.fogok.spaceships.control.game.gameobjects.SpaceShipController.AdditParams.SPEED;

public class SpaceShipController extends GameObject implements Controller {

    public enum AdditParams{
        DIRECTION, SPEED, SIZE
    }

    private NetworkData networkData;

    public SpaceShipController(){

    }

    public SpaceShipController(NetworkData networkData) {
        this.networkData = networkData;
        initAdditParams(3);
    }

    @Override
    public void handle(boolean pause) {     //serverLogic
        JsonValue respJ = networkData.getResponseJson();
        JsonValue oldRespJ = networkData.getOldJsonResponse();

        setX(oldRespJ.getFloat(1));
        setY(oldRespJ.getFloat(2));
        setAdditParam(oldRespJ.get(3).getFloat(DIRECTION.ordinal()), DIRECTION);
        setAdditParam(oldRespJ.get(3).getFloat(SPEED.ordinal()), SPEED);
        setAdditParam(oldRespJ.get(3).getFloat(SIZE.ordinal()), SIZE);

//        setX(GMUtils.lerpValue(oldRespJ.getFloat(1), respJ.getFloat(1), getX()));
//        setY(GMUtils.lerpValue(oldRespJ.getFloat(2), respJ.getFloat(2), getX()));
//        setAdditParam(GMUtils.lerpValue(oldRespJ.get(3).getFloat(DIRECTION.ordinal()), respJ.get(3).getFloat(DIRECTION.ordinal()), getAdditParam(DIRECTION)), DIRECTION);
//        setAdditParam(GMUtils.lerpValue(oldRespJ.get(3).getFloat(SPEED.ordinal()), respJ.get(3).getFloat(SPEED.ordinal()), getAdditParam(SPEED)), SPEED);
//        setAdditParam(GMUtils.lerpValue(oldRespJ.get(3).getFloat(SIZE.ordinal()), respJ.get(3).getFloat(SIZE.ordinal()), getAdditParam(SIZE)), SIZE);

    }

}
