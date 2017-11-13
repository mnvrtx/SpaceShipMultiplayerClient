package com.fogok.dataobjects;

public class PlayerData {

    private int AUID;
    private ConsoleState consoleState;

    public PlayerData() {
        this.consoleState = new ConsoleState();
    }

    public ConsoleState getConsoleState() {
        return consoleState;
    }

    public void setAUID(int AUID) {
        this.AUID = AUID;
    }

    public int getAUID() {
        return AUID;
    }
}
