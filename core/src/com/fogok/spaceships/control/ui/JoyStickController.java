package com.fogok.spaceships.control.ui;

import com.badlogic.gdx.Gdx;
import com.fogok.spaceships.Main;
import com.fogok.spaceships.control.Controller;
import com.fogok.spaceships.model.NetworkData;

public class JoyStickController implements Controller {

    public float touchedXJoystick, touchedYJoystick;
    public int sizeBackJoystick, posXJoystick, posYJoystick;  /// BackJoyStick
    public int sizeJoystick, centerJoystickX, centerJoystickY;   /// joyStick    /// 2 последние переменные - это центр джойстика

    public float joyStickOutputX, joyStickOutputY;

    private NetworkData _networkData;

    public JoyStickController(NetworkData networkData){
        _networkData = networkData;
        sizeBackJoystick = (int) (Main.DGNL / 7f);
        sizeJoystick = sizeBackJoystick / 2;
        setPos(Gdx.graphics.getWidth() - 30 - sizeBackJoystick, 30);
    }

    public void handle(boolean pause){
        int it1 = 0;
        for (int i = 0; i < 5; i++) {
            if (Gdx.input.isTouched(i) && isInJB(Gdx.input.getX(i), Gdx.graphics.getHeight() - Gdx.input.getY(i))){
                setJoyStick(i, false);
                it1++;
            }else if (it1 == 0){
                setJoyStick(i, true);
            }
        }
    }

    private void setJoyStick(int i, boolean center){   //setJoyStick
        if (!center){
            float xT = (float) Gdx.input.getX(i);
            float yT = (float) (Gdx.graphics.getHeight() - Gdx.input.getY(i));

            float angle = (float) (Math.atan2(yT - centerJoystickY, xT - centerJoystickX) / Math.PI * 180) + 90f;
            float distance = (float) Math.sqrt(Math.pow((xT - centerJoystickX), 2) + Math.pow((yT - centerJoystickY), 2));
            float max_over_joystick_distance =  (float) sizeBackJoystick / 2f;
            if (distance > max_over_joystick_distance) distance = max_over_joystick_distance;

//            NAPR_J = (int) (angle - 180f);
//            TYAGA = distance / max_over_joystick_distance;

            joyStickOutputX = (float) Math.sin(Math.toRadians(180f - angle)) * distance;
            joyStickOutputY = (float) Math.cos(Math.toRadians(180f - angle)) * distance;

            touchedXJoystick = (float) (centerJoystickX - sizeJoystick / 2) + joyStickOutputX;
            touchedYJoystick = (float) (centerJoystickY - sizeJoystick / 2) + joyStickOutputY;

//            _networkData.setJoystickX(joyStickOutputX);
//            _networkData.setJoystickY(joyStickOutputY);
        }else{
            touchedXJoystick = (float) centerJoystickX - (float) sizeJoystick / 2f;
            touchedYJoystick = (float) centerJoystickY - (float) sizeJoystick / 2f;
            joyStickOutputX = 0;
            joyStickOutputY = 0;
//            _networkData.setJoystickX(0f);
//            _networkData.setJoystickY(0f);
        }



    }

    private boolean isInJB(int x, int y){    //// true, если касание внутри джойстика, false - если снаружи джойстика
        int distance = (int) Math.sqrt(Math.pow((x - centerJoystickX), 2) + Math.pow((y - centerJoystickY), 2));
        if (distance < sizeBackJoystick * 2)
            return true;
        else
            return false;
    }


    private void setPos(int x, int y){
        posXJoystick = x;
        posYJoystick = y;
        centerJoystickX = posXJoystick + sizeBackJoystick / 2;
        centerJoystickY = posYJoystick + sizeBackJoystick / 2;
        setJoyStick(0, true);
    }

}
