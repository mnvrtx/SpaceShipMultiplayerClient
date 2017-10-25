package com.fogok.spaceships.model.game.dataobjects;

import com.fogok.spaceships.utils.GMUtils;
import com.fogok.spaceships.utils.Pool;
import com.fogok.spaceships.utils.gamedepended.LocatorsAddon;

import java.util.BitSet;

public abstract class GameObject implements Pool.Poolable {

    public static int TYPE = 0, X = 1, Y = 2, ADIITPRMS = 3; //TODO: поставить эти параметры куда надо

    //TODO: рефактор всего этого пакета, в соотвествии с контроллерами (всего пакета MODEL!!!)
    private BitSet flags = new BitSet(10);  //10 params available
    private LocatorsAddon locatorsAddon = null;
    private int type;
    private float x;
    private float y;
    private float[] additParams = new float[0];
    private boolean isServer;   //означает, является ли объект внутри сервера

    private boolean isInsideField;  //означает, находится ли объект в пуле или же он сейчас непосредственно находится на карте, true - значит, что он внутри пула

    //additParams - здесь параметры, которые не передаются на сервер, и расчитываются прямо в игре


    //region Setters

    public void setType(GameObjectsType gameObjectsType) {
        this.type = gameObjectsType.ordinal();
    }

    public void setServer(boolean server) {
        isServer = server;
    }

    public void setPosition(float x, float y) {
        setX(x);
        setY(y);
    }

    public void initLocators(int locatorsCount){
        locatorsAddon = new LocatorsAddon(new float[locatorsCount * 2]);
    }

    public void setLocator(float x, float y, int i) {
        locatorsAddon.setLocator(x, i);
        locatorsAddon.setLocator(y, i + 1);
    }

    public float getLocator(int i) {     //TODO: сделать адекватное доставание
        return locatorsAddon.getLocators()[i];
    }

    public void initAdditParams(int size) {
        additParams = new float[size];
    }

    public <E extends Enum<E>> void setAdditParam(float param, E enumObject) {
        additParams[enumObject.ordinal()] = GMUtils.getRoundedVal(param);
    }

    public void setX(float x) {
        this.x = GMUtils.getRoundedVal(x);
    }

    public void setY(float y) {
        this.y = GMUtils.getRoundedVal(y);
    }

    public void setLongFlags(long l){
        flags.clear();
        convert(flags, l);
    }

    public void setFlags(int flagNumber, boolean flag) {
        flags.set(flagNumber, flag);
    }
    //endregion

    //region Getters
    public long getLongFlags(){
        return convert(flags);
    }

    public void setInsideField(boolean insideField) {
        isInsideField = insideField;
    }

    public boolean isServer() {
        return isServer;
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

    private static BitSet convert(BitSet bitSet, long value) {
        bitSet.clear();
        int index = 0;
        while (value != 0L) {
            if (value % 2L != 0) {
                bitSet.set(index);
            }
            ++index;
            value = value >>> 1;
        }
        return bitSet;
    }

    private static long convert(BitSet bits) {
        long value = 0L;
        for (int i = 0; i < bits.length(); ++i) {
            value += bits.get(i) ? (1L << i) : 0L;
        }
        return value;
    }

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
