package com.fogok.dataobjects;

import com.fogok.dataobjects.utils.GMUtils;
import com.fogok.dataobjects.utils.Pool;
import com.fogok.dataobjects.utils.Serialization;

import java.util.BitSet;

public abstract class GameObject implements Pool.Poolable {

    public static final int X = 0, Y = 1, ADIITPRMS = 2, BOOLEANS = 3;

    private BitSet flags = new BitSet(10);  //10 params available

    private long playerId;
    private float widthDivHeight;
    private int type;
    private float x;
    private float y;
    private float[] additParams = new float[0];
    private char[] stringInformation = new char[0];


    private boolean isInsideField;  //означает, находится ли объект в пуле или же он сейчас непосредственно находится на карте, true - значит, что он внутри пула

    //additParams - здесь параметры, которые не передаются на сервер, и расчитываются прямо в игре


    //region Setters


    public void setStringInformation(char[] stringInformation) {
        this.stringInformation = stringInformation;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public void setType(GameObjectsType gameObjectsType) {
        setType(gameObjectsType.ordinal());
    }

    public void setType(int i) {
        this.type = i;
    }

    public void setPosition(float x, float y) {
        setX(x);
        setY(y);
    }

    public void setWidthDivHeight(float widthDivHeight) {
        this.widthDivHeight = widthDivHeight;
    }

    public void initAdditParams(int size) {
        additParams = new float[size];
    }

    public <E extends Enum<E>> void setAdditParam(float param, E enumObject) {
        setAdditParam(param, enumObject.ordinal());
    }

    public void setAdditParam(float param, int i){
        additParams[i] = GMUtils.getRoundedVal(param);
    }

    public void setAdditParams(float[] additParams) {
        this.additParams = additParams;
    }

    public void setInsideField(boolean insideField) {
        isInsideField = insideField;
    }

    public void setX(float x) {
        this.x = GMUtils.getRoundedVal(x);
    }

    public void setY(float y) {
        this.y = GMUtils.getRoundedVal(y);
    }

    public void setLongFlags(long l){
        flags.clear();
        Serialization.convert(flags, l);
    }

    public void setFlags(int flagNumber, boolean flag) {
        flags.set(flagNumber, flag);
    }
    //endregion

    //region Getters


    public long getPlayerId() {
        return playerId;
    }

    public long getLongFlags(){
        return Serialization.convert(flags);
    }

    public float getWidthDivHeight() {
        return widthDivHeight;
    }

    public char[] getStringInformation() {
        return stringInformation;
    }

    public boolean getFlag(int i) {
        return flags.get(i);
    }

    public <E extends Enum<E>> float getAdditParam(E enumObject) {
        return additParams[enumObject.ordinal()];
    }

    public float[] getAdditParams() {
        return additParams;
    }

    public int getType() {
        return type;
    }

    public boolean isInsideField() {
        return isInsideField;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
    //endregion


    //test

    //    public static void main(String[] args) {
//        BitSet flags = new BitSet(10);  //10 params avialable
//
//        flags.set(new Random().nextInt(10), true);
//        flags.set(new Random().nextInt(10), true);
//        flags.set(new Random().nextInt(10), true);
//        flags.set(new Random().nextInt(10), true);
//        flags.set(new Random().nextInt(10), true);
//        System.out.println(convert(flags, convert(flags)));
//        flags.clear();
//
//        flags.set(new Random().nextInt(10), true);
//        flags.set(new Random().nextInt(10), true);
//        flags.set(new Random().nextInt(10), true);
//        flags.set(new Random().nextInt(10), true);
//        flags.set(new Random().nextInt(10), true);
//        System.out.println(convert(flags, convert(flags)));
//        flags.clear();
//
//        flags.set(new Random().nextInt(10), true);
//        flags.set(new Random().nextInt(10), true);
//        flags.set(new Random().nextInt(10), true);
//        flags.set(new Random().nextInt(10), true);
//        flags.set(new Random().nextInt(10), true);
//        System.out.println(convert(flags, convert(flags)));
//        flags.clear();
//
//        flags.set(new Random().nextInt(10), true);
//        flags.set(new Random().nextInt(10), true);
//        flags.set(new Random().nextInt(10), true);
//        flags.set(new Random().nextInt(10), true);
//        flags.set(new Random().nextInt(10), true);
//        System.out.println(convert(flags, convert(flags)));
//        flags.clear();
//
//        flags.set(new Random().nextInt(10), true);
//        flags.set(new Random().nextInt(10), true);
//        flags.set(new Random().nextInt(10), true);
//        flags.set(new Random().nextInt(10), true);
//        flags.set(new Random().nextInt(10), true);
//        System.out.println(convert(flags, convert(flags)));
//        flags.clear();
//
//        flags.set(new Random().nextInt(10), true);
//        flags.set(new Random().nextInt(10), true);
//        flags.set(new Random().nextInt(10), true);
//        flags.set(new Random().nextInt(10), true);
//        flags.set(new Random().nextInt(10), true);
//        System.out.println(convert(flags, convert(flags)));
//        flags.clear();
//
//        flags.set(new Random().nextInt(10), true);
//        flags.set(new Random().nextInt(10), true);
//        flags.set(new Random().nextInt(10), true);
//        flags.set(new Random().nextInt(10), true);
//        flags.set(new Random().nextInt(10), true);
//        System.out.println(convert(flags, convert(flags)));
//        flags.clear();
//
//    }

}
