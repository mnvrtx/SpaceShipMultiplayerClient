package com.fogok.spaceships.control.game.gameobjects;

import com.fogok.spaceships.Main;
import com.fogok.spaceships.control.game.GameObjectsTypes;
import com.fogok.spaceships.control.ui.JoyStickController;
import com.fogok.spaceships.model.NetworkData;
import com.fogok.spaceships.view.utils.CORDCONV;
import com.fogok.spaceships.view.utils.GMUtils;

import static com.fogok.spaceships.control.game.gameobjects.SpaceShipController.AdditParams.*;

public class SpaceShipClientController extends SpaceShipController {

    private NetworkData networkData;

    private float maxSpeed = 0.14f;
    private float speedVelocityPercent = 0.03f;
    JoyStickController joyStickController;

    public SpaceShipClientController(NetworkData networkData, JoyStickController joyStickController) {
        this.networkData = networkData;
        this.joyStickController = joyStickController;
        setType(GameObjectsTypes.SpaceShip);
        setPosition(Main.WIDTH / 2f, Main.HEIGHT / 2f);
        initAdditParams(3);
        setAdditParam(1.4f, SIZE);
    }

    @Override
    public void handle(boolean pause) {
        float x = CORDCONV.gCamX((int) joyStickController.joyStickOutputX);
        float y = CORDCONV.gCamY((int) joyStickController.joyStickOutputY);

        boolean isMoving = x != 0 || y != 0;

        setAdditParam(getAdditParam(SPEED) + (isMoving ? maxSpeed * speedVelocityPercent : maxSpeed * -speedVelocityPercent), SPEED);

        if (getAdditParam(SPEED) > maxSpeed)
            setAdditParam(maxSpeed, SPEED);
        if (getAdditParam(SPEED) < 0f)
            setAdditParam(0f, SPEED);

        float targetDir = 0f;
        if (isMoving) {
            targetDir = GMUtils.getDeg(getX() + x, getY() + y, getX(), getY()) + 90;
            targetDir += targetDir > 360 ? -360 : 0;
            setAdditParam(GMUtils.lerpDirection(getAdditParam(DIRECTION),
                    targetDir, 6 * Main.mdT * (getAdditParam(SPEED) / maxSpeed)), DIRECTION);
        }

//        DebugGUI.DEBUG_TEXT = "{" + currentDirection + "} " + "{" + targetDir + "} ";
        setPosition(getX() + GMUtils.getNextX(getAdditParam(SPEED), getAdditParam(DIRECTION) + 90) * Main.mdT, getY() + GMUtils.getNextY(getAdditParam(SPEED), getAdditParam(DIRECTION) + 90) * Main.mdT);

        networkData.addObject(this);
    }
}
