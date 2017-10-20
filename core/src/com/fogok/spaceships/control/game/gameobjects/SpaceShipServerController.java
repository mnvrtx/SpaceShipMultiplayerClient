package com.fogok.spaceships.control.game.gameobjects;

import com.badlogic.gdx.utils.JsonValue;
import com.fogok.spaceships.control.Controller;
import com.fogok.spaceships.model.NetworkData;
import com.fogok.spaceships.model.game.dataobjects.GameObject;
import com.fogok.spaceships.model.game.dataobjects.SpaceShipObject;

import static com.fogok.spaceships.control.game.gameobjects.SpaceShipServerController.AdditParams.DIRECTION;
import static com.fogok.spaceships.control.game.gameobjects.SpaceShipServerController.AdditParams.SIZE;
import static com.fogok.spaceships.control.game.gameobjects.SpaceShipServerController.AdditParams.SPEED;

public class SpaceShipServerController implements Controller {

    private NetworkData networkData;
    private SpaceShipObject spaceShipObject;

    public SpaceShipServerController(GameObject spaceShipObject, NetworkData networkData){
        this.spaceShipObject = (SpaceShipObject) spaceShipObject;
        this.networkData = networkData;
    }

    @Override
    public void handle(boolean pause) {     //serverLogic
//        JsonValue respJ = networkData.getResponseJson();
        JsonValue oldRespJ = networkData.getOldJsonResponse();

        oldRespJ = oldRespJ.get(0);

        spaceShipObject.setX(oldRespJ.getFloat(1));
        spaceShipObject.setY(oldRespJ.getFloat(2));
        spaceShipObject.setAdditParam(oldRespJ.get(3).getFloat(DIRECTION.ordinal()), DIRECTION);
        spaceShipObject.setAdditParam(oldRespJ.get(3).getFloat(SPEED.ordinal()), SPEED);
        spaceShipObject.setAdditParam(oldRespJ.get(3).getFloat(SIZE.ordinal()), SIZE);

//        setX(GMUtils.lerpValue(oldRespJ.getFloat(1), respJ.getFloat(1), getX()));
//        setY(GMUtils.lerpValue(oldRespJ.getFloat(2), respJ.getFloat(2), getX()));
//        setAdditParam(GMUtils.lerpValue(oldRespJ.get(3).getFloat(DIRECTION.ordinal()), respJ.get(3).getFloat(DIRECTION.ordinal()), getAdditParam(DIRECTION)), DIRECTION);
//        setAdditParam(GMUtils.lerpValue(oldRespJ.get(3).getFloat(SPEED.ordinal()), respJ.get(3).getFloat(SPEED.ordinal()), getAdditParam(SPEED)), SPEED);
//        setAdditParam(GMUtils.lerpValue(oldRespJ.get(3).getFloat(SIZE.ordinal()), respJ.get(3).getFloat(SIZE.ordinal()), getAdditParam(SIZE)), SIZE);

    }

}
