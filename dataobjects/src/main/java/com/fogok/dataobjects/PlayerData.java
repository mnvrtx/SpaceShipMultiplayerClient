package com.fogok.dataobjects;

public class PlayerData {

    private int AUID;
    private ConsoleState consoleState;
    private boolean hasServeredPlayerData;

    public PlayerData() {

    }

    public void setConsoleState(ConsoleState consoleState) {
        this.consoleState = consoleState;
        hasServeredPlayerData = true;
    }

    public boolean isHasServeredPlayerData() {
        return hasServeredPlayerData;
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
