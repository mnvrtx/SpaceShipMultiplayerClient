package com.fogok.dataobjects.gameobjects.ships;

import com.fogok.dataobjects.GameObject;
import com.fogok.dataobjects.gameobjects.ConsoleState;
import com.fogok.dataobjects.gameobjects.Weapon;

public abstract class ShipObjectBase extends GameObject {

    private ConsoleState consoleState;
    private Weapon weapon;

    public enum AdditParams{
        DIRECTION, SPEED, SIZE
    }

    public void setConsoleState(ConsoleState consoleState) {
        this.consoleState = consoleState;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public ConsoleState getConsoleState() {
        return consoleState;
    }

    public Weapon getWeapon() {
        return weapon;
    }
}
