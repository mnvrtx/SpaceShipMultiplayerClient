package com.fogok.dataobjects;

import com.fogok.dataobjects.utils.Serialization;

import java.util.BitSet;

public class ConsoleState {

    public enum ConsoleFlagState{
        SIMPLE_FIRE
    }

    private BitSet flags = new BitSet(10);  //10 params available
    private float x;
    private float y;

    public ConsoleState() {

    }

    public void setLongFlags(long l){
        flags.clear();
        Serialization.convert(flags, l);
    }

    public <E extends Enum<E>> void setFlag(boolean flag, E enumObject) {
        flags.set(enumObject.ordinal(), flag);
        Serialization.convert(flags);
    }


    public void setState(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public <E extends ConsoleState.ConsoleFlagState>boolean getFlag(E enumObject){
        return flags.get(enumObject.ordinal());
    }

    public BitSet getLongFlags(){
        return flags;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
