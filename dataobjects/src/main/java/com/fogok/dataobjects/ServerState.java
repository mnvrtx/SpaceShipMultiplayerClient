package com.fogok.dataobjects;

public class ServerState {

    private PlayerGlobalData playerGlobalData = new PlayerGlobalData();
    private int playersOnline;

    public void setPlayersOnline(int playersOnline) {
        this.playersOnline = playersOnline;
    }

    public int getPlayersOnline() {
        return playersOnline;
    }

    public PlayerGlobalData getPlayerGlobalData() {
        return playerGlobalData;
    }
}
