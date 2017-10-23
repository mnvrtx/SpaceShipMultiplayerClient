package com.fogok.spaceships.control.game.gameobjects.ships;

import com.fogok.spaceships.Main;
import com.fogok.spaceships.control.game.ObjectController;
import com.fogok.spaceships.control.ui.JoyStickController;
import com.fogok.spaceships.model.game.dataobjects.GameObject;
import com.fogok.spaceships.model.game.dataobjects.gameobjects.ships.ShipObjectBase;
import com.fogok.spaceships.utils.GMUtils;
import com.fogok.spaceships.view.utils.CORDCONV;

import static com.fogok.spaceships.model.game.dataobjects.gameobjects.ships.ShipObjectBase.AdditParams.*;

public abstract class ShipObjectControllerBase implements ObjectController {

    /*
     * Основа для любого космического корабля
     */

    private ShipObjectBase shipObjectBase;
    private JoyStickController joyStickController;

    public ShipObjectControllerBase(JoyStickController joyStickController) {
        this.joyStickController = joyStickController;
    }

    @Override
    public void setHandledObject(GameObject handledObject) {
        shipObjectBase = (ShipObjectBase) handledObject;
    }

    public void add(){
        shipObjectBase.setPosition(Main.WIDTH / 2f, Main.HEIGHT / 2f);
        shipObjectBase.setAdditParam(1.4f, SIZE);
    }

    @Override
    public void handleClient(boolean pause) {
        float x = CORDCONV.gCamX((int) joyStickController.joyStickOutputX);
        float y = CORDCONV.gCamY((int) joyStickController.joyStickOutputY);

        boolean isMoving = x != 0 || y != 0;

        float maxSpeed = 0.14f;
        float speedVelocityPercent = 0.03f;
        shipObjectBase.setAdditParam(shipObjectBase.getAdditParam(SPEED) + (isMoving ? maxSpeed * speedVelocityPercent : maxSpeed * -speedVelocityPercent), SPEED);

        if (shipObjectBase.getAdditParam(SPEED) > maxSpeed)
            shipObjectBase.setAdditParam(maxSpeed, SPEED);
        if (shipObjectBase.getAdditParam(SPEED) < 0f)
            shipObjectBase.setAdditParam(0f, SPEED);

        float targetDir = 0f;
        if (isMoving) {
            targetDir = GMUtils.getDeg(shipObjectBase.getX() + x, shipObjectBase.getY() + y, shipObjectBase.getX(), shipObjectBase.getY()) + 90;
            targetDir += targetDir > 360 ? -360 : 0;
            shipObjectBase.setAdditParam(GMUtils.lerpDirection(shipObjectBase.getAdditParam(DIRECTION),
                    targetDir, 6 * Main.mdT * (shipObjectBase.getAdditParam(SPEED) / maxSpeed)), DIRECTION);
        }

//        DebugGUI.DEBUG_TEXT = "{" + currentDirection + "} " + "{" + targetDir + "} ";
        shipObjectBase.setPosition(shipObjectBase.getX() + GMUtils.getNextX(shipObjectBase.getAdditParam(SPEED), shipObjectBase.getAdditParam(DIRECTION) + 90) * Main.mdT, shipObjectBase.getY() + GMUtils.getNextY(shipObjectBase.getAdditParam(SPEED), shipObjectBase.getAdditParam(DIRECTION) + 90) * Main.mdT);
    }

    @Override
    public boolean isAlive() {
        return true;
    }
}
