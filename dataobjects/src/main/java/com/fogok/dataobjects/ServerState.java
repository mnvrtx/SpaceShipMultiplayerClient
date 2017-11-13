package com.fogok.dataobjects;

public class ServerState {

    private PlayerGlobalData playerGlobalData;
    private int playersOnline;

    public void setPlayersOnline(int playersOnline) {
        this.playersOnline = playersOnline;
    }

    public void setPlayerGlobalData(PlayerGlobalData playerGlobalData) {
        this.playerGlobalData = playerGlobalData;
    }

    public int getPlayersOnline() {
        return playersOnline;
    }

    public PlayerGlobalData getPlayerGlobalData() {
        return playerGlobalData;
    }
}
