package com.fogok.dataobjects.gameobjects.ships;

import com.fogok.dataobjects.ConsoleState;
import com.fogok.dataobjects.GameObject;

public abstract class ShipObjectBase extends GameObject {

    private final ConsoleState consoleState = new ConsoleState();   //у любого корабля есть консоль управления

    public enum AdditParams{
        DIRECTION, SPEED, SIZE
    }

    public ConsoleState getConsoleState() {
        return consoleState;
    }

    public <E extends ConsoleState.ConsoleFlagState> boolean getConsoleFlag(E flagState){
        return consoleState.getFlag(flagState);
    }
}
