package com.fogok.dataobjects;

import com.fogok.dataobjects.utils.GMUtils;
import com.fogok.dataobjects.utils.Serialization;

import java.util.BitSet;

public class PlayerGlobalData {

    public enum PlayerGlobalDataFlags{

    }

    public enum PlayerGlobalDataFloats{
        WINLOSEPERCENT
    }

    private BitSet flags = new BitSet(10);  //10 params available
    private float[] dataFloats = new float[0];

    public PlayerGlobalData() {

    }

    public void initDataFloats(int size) {
        dataFloats = new float[size];
    }

    public <E extends PlayerGlobalDataFloats> void setDataFloat(float param, E playerGlobalDataEnum){
        dataFloats[playerGlobalDataEnum.ordinal()] = GMUtils.getRoundedVal(param);
    }

    public void setDataFloats(float[] dataFloats) {
        this.dataFloats = dataFloats;
    }

    public void setLongFlags(long l){
        flags.clear();
        Serialization.convert(flags, l);
    }

    public <E extends PlayerGlobalDataFlags> void setFlag(boolean flag, E enumObject) {
        flags.set(enumObject.ordinal(), flag);
        Serialization.convert(flags);
    }

    public <E extends PlayerGlobalDataFlags>boolean getFlag(E enumObject){
        return flags.get(enumObject.ordinal());
    }

    public BitSet getLongFlags(){
        return flags;
    }

    public <E extends PlayerGlobalDataFloats> float getDataFloat(E enumObject) {
        return dataFloats[enumObject.ordinal()];
    }

    public float[] getDataFloats() {
        return dataFloats;
    }

}
