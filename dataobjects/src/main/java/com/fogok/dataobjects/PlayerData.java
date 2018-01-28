package com.fogok.dataobjects;

import com.fogok.dataobjects.gameobjects.ConsoleState;

public class PlayerData {

    private ConsoleState consoleState;

    public PlayerData(ConsoleState consoleState) {
        setConsoleState(consoleState);
    }

    public void setConsoleState(ConsoleState consoleState) {
        this.consoleState = consoleState;
    }

    public ConsoleState getConsoleState() {
        return consoleState;
    }

    @Override
    public String toString() {
        return consoleState.toString();
    }
}
