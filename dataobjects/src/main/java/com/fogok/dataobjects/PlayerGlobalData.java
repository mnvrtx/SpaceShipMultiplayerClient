package com.fogok.dataobjects;

public class PlayerGlobalData extends GameObject{

    //this is to SOCSERVER, don`t use in game session

    public PlayerGlobalData(){
        initAdditParams(PlayerGlobalDataFloats.values().length);
        setType(GameObjectsType.PlayerGlobalData);
    }

    public enum PlayerGlobalDataFlags{

    }

    public enum PlayerGlobalDataFloats{
        WIN_LOSE_PERCENT, PLAYERS_ONLINE
    }

    @Override
    public void reset() {

    }
}
