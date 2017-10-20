package com.fogok.spaceships.control.game.gameobjects;

import com.fogok.spaceships.Main;
import com.fogok.spaceships.control.Controller;
import com.fogok.spaceships.control.ui.JoyStickController;
import com.fogok.spaceships.model.NetworkData;
import com.fogok.spaceships.model.game.dataobjects.GameObject;
import com.fogok.spaceships.model.game.dataobjects.SpaceShipObject;
import com.fogok.spaceships.utils.GMUtils;
import com.fogok.spaceships.view.utils.CORDCONV;

import static com.fogok.spaceships.model.game.dataobjects.SpaceShipObject.AdditParams.DIRECTION;
import static com.fogok.spaceships.model.game.dataobjects.SpaceShipObject.AdditParams.SIZE;
import static com.fogok.spaceships.model.game.dataobjects.SpaceShipObject.AdditParams.SPEED;


public class SpaceShipClientController implements Controller{

    private NetworkData networkData;
    private SpaceShipObject spaceShipObject;

    private JoyStickController joyStickController;

    public SpaceShipClientController(GameObject spaceShipObject, NetworkData networkData, JoyStickController joyStickController) {
        this.spaceShipObject = (SpaceShipObject) spaceShipObject;
        this.networkData = networkData;
        this.joyStickController = joyStickController;
        this.spaceShipObject.setPosition(Main.WIDTH / 2f, Main.HEIGHT / 2f);
        this.spaceShipObject.setAdditParam(1.4f, SIZE);
    }

    @Override
    public void handle(boolean pause) {
        float x = CORDCONV.gCamX((int) joyStickController.joyStickOutputX);
        float y = CORDCONV.gCamY((int) joyStickController.joyStickOutputY);

        boolean isMoving = x != 0 || y != 0;

        float maxSpeed = 0.14f;
        float speedVelocityPercent = 0.03f;
        spaceShipObject.setAdditParam(spaceShipObject.getAdditParam(SPEED) + (isMoving ? maxSpeed * speedVelocityPercent : maxSpeed * -speedVelocityPercent), SPEED);

        if (spaceShipObject.getAdditParam(SPEED) > maxSpeed)
            spaceShipObject.setAdditParam(maxSpeed, SPEED);
        if (spaceShipObject.getAdditParam(SPEED) < 0f)
            spaceShipObject.setAdditParam(0f, SPEED);

        float targetDir = 0f;
        if (isMoving) {
            targetDir = GMUtils.getDeg(spaceShipObject.getX() + x, spaceShipObject.getY() + y, spaceShipObject.getX(), spaceShipObject.getY()) + 90;
            targetDir += targetDir > 360 ? -360 : 0;
            spaceShipObject.setAdditParam(GMUtils.lerpDirection(spaceShipObject.getAdditParam(DIRECTION),
                    targetDir, 6 * Main.mdT * (spaceShipObject.getAdditParam(SPEED) / maxSpeed)), DIRECTION);
        }

//        DebugGUI.DEBUG_TEXT = "{" + currentDirection + "} " + "{" + targetDir + "} ";
        spaceShipObject.setPosition(spaceShipObject.getX() + GMUtils.getNextX(spaceShipObject.getAdditParam(SPEED), spaceShipObject.getAdditParam(DIRECTION) + 90) * Main.mdT, spaceShipObject.getY() + GMUtils.getNextY(spaceShipObject.getAdditParam(SPEED), spaceShipObject.getAdditParam(DIRECTION) + 90) * Main.mdT);

        networkData.addObject(spaceShipObject);
    }
}
