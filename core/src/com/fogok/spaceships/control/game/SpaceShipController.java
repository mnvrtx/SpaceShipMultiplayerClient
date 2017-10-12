package com.fogok.spaceships.control.game;

import com.badlogic.gdx.math.Vector2;
import com.fogok.spaceships.Main;
import com.fogok.spaceships.control.Controller;
import com.fogok.spaceships.control.ui.JoyStickController;
import com.fogok.spaceships.view.utils.CORDCONV;
import com.fogok.spaceships.view.utils.GMUtils;

public class SpaceShipController implements Controller {

    private Vector2 position;
    private float currentDirection;
    private float currentSpeed;

    private float size = 1.4f;

    private float maxSpeed = 0.14f;
    private float speedVelocityPercent = 0.03f;
    JoyStickController _joyStickController;

    public SpaceShipController(JoyStickController joyStickController) {
        position = new Vector2(Main.WIDTH / 2f, Main.HEIGHT / 2f);
        _joyStickController = joyStickController;
    }

    @Override
    public void handle(boolean pause) {
        float x = CORDCONV.gCamX((int) _joyStickController.joyStickOutputX);
        float y = CORDCONV.gCamY((int) _joyStickController.joyStickOutputY);

        boolean isMoving = x != 0 || y != 0;

        currentSpeed += isMoving ? maxSpeed * speedVelocityPercent : maxSpeed * -speedVelocityPercent;

        if (currentSpeed > maxSpeed)
            currentSpeed = maxSpeed;
        if (currentSpeed < 0f)
            currentSpeed = 0f;

        float targetDir = 0f;
        if (isMoving) {
            targetDir = GMUtils.getDeg(position.x + x, position.y + y, position.x, position.y) + 90;
            targetDir += targetDir > 360 ? -360 : 0;
            currentDirection = GMUtils.lerpDirection(currentDirection,
                    targetDir, 6 * Main.mdT * (currentSpeed / maxSpeed));
        }

//        DebugGUI.DEBUG_TEXT = "{" + currentDirection + "} " + "{" + targetDir + "} ";
        position.add(GMUtils.getNextX(currentSpeed, currentDirection + 90) * Main.mdT, GMUtils.getNextY(currentSpeed, currentDirection + 90) * Main.mdT);
    }

    public float getCurrentDirection() {
        return currentDirection;
    }

    public float getCurrentSpeed() {
        return currentSpeed;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getSize() {
        return size;
    }
}
