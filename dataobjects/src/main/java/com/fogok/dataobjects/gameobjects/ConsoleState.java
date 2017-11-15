package com.fogok.dataobjects.gameobjects;

import com.fogok.dataobjects.GameObject;
import com.fogok.dataobjects.GameObjectsType;

public class ConsoleState extends GameObject{

    public enum AdditParams{

    }

    public enum AdditBooleanParams{
        IS_FIRE
    }

    public ConsoleState(){
        initAdditParams(AdditParams.values().length);
        setType(GameObjectsType.ConsoleState);
    }

    @Override
    public void reset() {

    }
}
