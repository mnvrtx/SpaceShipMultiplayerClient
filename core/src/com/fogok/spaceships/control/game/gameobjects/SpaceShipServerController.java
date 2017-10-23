package com.fogok.spaceships.control.game.gameobjects;

import com.badlogic.gdx.utils.JsonValue;
import com.fogok.spaceships.control.Controller;
import com.fogok.spaceships.model.NetworkData;
import com.fogok.spaceships.model.game.dataobjects.GameObject;
import com.fogok.spaceships.model.game.dataobjects.gameobjects.ships.ShipObjectBase;

import static com.fogok.spaceships.model.game.dataobjects.gameobjects.ships.ShipObjectBase.AdditParams.*;


public class SpaceShipServerController implements Controller {

    private NetworkData networkData;
    private ShipObjectBase shipObjectBase;

    public SpaceShipServerController(GameObject spaceShipObject, NetworkData networkData){
        this.shipObjectBase = (ShipObjectBase) spaceShipObject;
        this.networkData = networkData;
    }

    @Override
    public void handle(boolean pause) {     //serverLogic
//        JsonValue respJ = networkData.getResponseJson();
        JsonValue oldRespJ = networkData.getOldJsonResponse();

        oldRespJ = oldRespJ.get(0);

        shipObjectBase.setX(oldRespJ.getFloat(1));
        shipObjectBase.setY(oldRespJ.getFloat(2));
        shipObjectBase.setAdditParam(oldRespJ.get(3).getFloat(DIRECTION.ordinal()), DIRECTION);
        shipObjectBase.setAdditParam(oldRespJ.get(3).getFloat(SPEED.ordinal()), SPEED);
        shipObjectBase.setAdditParam(oldRespJ.get(3).getFloat(SIZE.ordinal()), SIZE);

//        setX(GMUtils.lerpValue(oldRespJ.getFloat(1), respJ.getFloat(1), getX()));
//        setY(GMUtils.lerpValue(oldRespJ.getFloat(2), respJ.getFloat(2), getX()));
//        setAdditParam(GMUtils.lerpValue(oldRespJ.get(3).getFloat(DIRECTION.ordinal()), respJ.get(3).getFloat(DIRECTION.ordinal()), getAdditParam(DIRECTION)), DIRECTION);
//        setAdditParam(GMUtils.lerpValue(oldRespJ.get(3).getFloat(SPEED.ordinal()), respJ.get(3).getFloat(SPEED.ordinal()), getAdditParam(SPEED)), SPEED);
//        setAdditParam(GMUtils.lerpValue(oldRespJ.get(3).getFloat(SIZE.ordinal()), respJ.get(3).getFloat(SIZE.ordinal()), getAdditParam(SIZE)), SIZE);

    }

}
