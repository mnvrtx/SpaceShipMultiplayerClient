package com.fogok.dataobjects;

import com.fogok.dataobjects.utils.GMUtils;
import com.fogok.dataobjects.utils.Serialization;

import java.util.BitSet;

public class ConsoleState {

    public enum ConsoleFlagState{
        SIMPLE_FIRE
    }

    private BitSet flags = new BitSet(10);  //10 params available
    private float[] additParams = new float[0];
    private float x;
    private float y;

    public ConsoleState() {

    }

    public void initAdditParams(int size) {
        additParams = new float[size];
    }

    public void setAdditParam(float param, int i){
        additParams[i] = GMUtils.getRoundedVal(param);
    }

    public void setAdditParams(float[] additParams) {
        this.additParams = additParams;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
            this.y = y;
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

    public <E extends Enum<E>> float getAdditParam(E enumObject) {
        return additParams[enumObject.ordinal()];
    }

    public float[] getAdditParams() {
        return additParams;
    }
}
