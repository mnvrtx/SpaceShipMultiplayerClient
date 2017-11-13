package com.fogok.dataobjects.gameobjects.ships;

import com.fogok.dataobjects.ConsoleState;
import com.fogok.dataobjects.GameObject;

public abstract class ShipObjectBase extends GameObject {

    public enum AdditParams{
        DIRECTION, SPEED, SIZE
    }

    private ConsoleState consoleState;   //у любого корабля есть консоль управления

    public void setConsoleState(ConsoleState consoleState) {
        this.consoleState = consoleState;
    }

    public ConsoleState getConsoleState() {
        return consoleState;
    }

    public <E extends ConsoleState.ConsoleFlagState> boolean getConsoleFlag(E flagState){
        return consoleState.getFlag(flagState);
    }

}
