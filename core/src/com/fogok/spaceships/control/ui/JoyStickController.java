package com.fogok.spaceships.control.ui;

import com.badlogic.gdx.Gdx;
import com.fogok.dataobjects.gameobjects.ConsoleState;
import com.fogok.spaceships.Main;
import com.fogok.spaceships.control.Controller;

public class JoyStickController implements Controller {

    /**
     * Выходные координаты для отрисовки dragButton
     */
    public float touchedXJoystick, touchedYJoystick;
    /**
     * Размер контрола joystick, а так же координаты центра
     */
    public int sizeJoystickDragButton, sizeJoystickCircle, posXJoystick, posYJoystick, centerXJoystick, centerYJoystick;
    private boolean isFireTouched;

    private ConsoleState consoleState;

    public JoyStickController(ConsoleState consoleState){
        this.consoleState = consoleState;
        sizeJoystickCircle = (int) (Main.DGNL / 7f);
        sizeJoystickDragButton = sizeJoystickCircle / 2;
        setControlPosition(30, 30);
    }

    /**
     * Обрабатываем все
     * @param pause
     */
    public void handle(boolean pause){
        if (!pause) {
            int it1 = 0;
            for (int i = 0; i < 5; i++) {
                if (Gdx.input.isTouched(i) && isContainsJoystick(Gdx.input.getX(i), Gdx.graphics.getHeight() - Gdx.input.getY(i))){
                    refreshJoystick(i, true);
                    it1++;
                }else if (it1 == 0){
                    refreshJoystick(i, false);
                }
            }
        }
    }

    /**
     * Refresh all joystick data
     * @param tIdx finger touch idx
     * @param isTouched finger is touch ?
     */
    private void refreshJoystick(int tIdx, boolean isTouched){
        float joyStickOutputX, joyStickOutputY;
        if (isTouched){
            float xT = (float) Gdx.input.getX(tIdx);
            float yT = (float) (Gdx.graphics.getHeight() - Gdx.input.getY(tIdx));

            float angle = (float) (Math.atan2(yT - centerYJoystick, xT - centerXJoystick) / Math.PI * 180) + 90f;
            float distance = (float) Math.sqrt(Math.pow((xT - centerXJoystick), 2) + Math.pow((yT - centerYJoystick), 2));
            float max_over_joystick_distance =  (float) sizeJoystickCircle / 2f;
            if (distance > max_over_joystick_distance) distance = max_over_joystick_distance;

            joyStickOutputX = (float) Math.sin(Math.toRadians(180f - angle)) * distance;
            joyStickOutputY = (float) Math.cos(Math.toRadians(180f - angle)) * distance;

            touchedXJoystick = centerXJoystick - sizeJoystickDragButton / 2f + joyStickOutputX;
            touchedYJoystick = centerYJoystick - sizeJoystickDragButton / 2f + joyStickOutputY;

        }else{
            touchedXJoystick = centerXJoystick - sizeJoystickDragButton / 2f;
            touchedYJoystick = centerYJoystick - sizeJoystickDragButton / 2f;

            joyStickOutputX = 0;
            joyStickOutputY = 0;
        }

        consoleState.setX(joyStickOutputX);
        consoleState.setY(joyStickOutputY);
        consoleState.setFlag(ConsoleState.AdditBooleanParams.IS_FIRE, isFireTouched);
    }

    /**
     * Возвращаем true, если координаты внутри джойстика, false - если снаружи джойстика
     */
    private boolean isContainsJoystick(int x, int y){
        int distance = (int) Math.sqrt(Math.pow((x - centerXJoystick), 2) + Math.pow((y - centerYJoystick), 2));
        if (distance < sizeJoystickCircle * 2)
            return true;
        else
            return false;
    }

    /**
     * Ставим сам контрол joystick куда нам надо
     */
    private void setControlPosition(int x, int y){
        posXJoystick = x;
        posYJoystick = y;
        centerXJoystick = posXJoystick + sizeJoystickCircle / 2;
        centerYJoystick = posYJoystick + sizeJoystickCircle / 2;
        refreshJoystick(0, false);
    }

}
