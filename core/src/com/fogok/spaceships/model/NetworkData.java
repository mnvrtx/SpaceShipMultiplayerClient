package com.fogok.spaceships.model;

import com.google.gson.annotations.SerializedName;

public class NetworkData {


    @SerializedName("jx")
    private float joystickX;

    @SerializedName("jy")
    private float joystickY;

    public void setJoystickX(float joystickX) {
        this.joystickX = ((int) (joystickX * 10f)) / 10f;
    }

    public void setJoystickY(float joystickY) {
        this.joystickY = ((int) (joystickY * 10f)) / 10f;
    }


}
