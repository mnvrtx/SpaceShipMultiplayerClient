package com.fogok.dataobjects;

import com.fogok.dataobjects.utils.EveryBodyPool;
import com.fogok.dataobjects.utils.GMUtils;
import com.fogok.dataobjects.utils.Pool;
import com.fogok.dataobjects.utils.Serialization;

import java.util.BitSet;

public abstract class GameObject implements Pool.Poolable {

    private BitSet flags = new BitSet(10);  //10 bool params available

    private long playerId;
    private int type;
    private float x;
    private float y;
    private float[] additParams = new float[0];
    private char[] stringInformation = new char[0];

    //local
    private float widthDivHeight;

    //server
    private boolean isInsideField;


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

    public void setFlag(int flagNumber, boolean flag) {
        flags.set(flagNumber, flag);
    }

    public <E extends Enum<E>> void setFlag(E flagEnum, boolean flag) {
        flags.set(flagEnum.ordinal(), flag);
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

    //region Json serialization (it is necessary to view all fields)
    public static final int X = 0, Y = 1, ADIITPRMS = 2, BOOLEANS = 3;

    private static StringBuilder stringBuilder = new StringBuilder();

    @Override
    public String toString() {
        return toString(false);
    }

    public String toString(boolean modern) {
        stringBuilder.setLength(0);
        stringBuilder.append(EveryBodyPool.JSONElements[0]);
        long gameObjectLongFlags = getLongFlags();
        for (int i = 0; i < EveryBodyPool.JSONStrings.length; i++) {          //проходимся по параметрам объекта
            if (!(i == GameObject.ADIITPRMS && getAdditParams().length == 0) && !(i == GameObject.BOOLEANS && gameObjectLongFlags == 0)){     //не добавляем лишнего, если данных нет
                addEndJSONString(i == 0);
                addStartJSONString(i);
                switch (i) {
                    case GameObject.X:
                        stringBuilder.append(getX());
                        break;
                    case GameObject.Y:
                        stringBuilder.append(getY());
                        break;
                    case GameObject.ADIITPRMS:
                        stringBuilder.append(EveryBodyPool.JSONElements[6]);
                        float[] addPrms = getAdditParams();
                        for (int j = 0; j < addPrms.length; j++) {
                            stringBuilder.append(addPrms[j]);
                            addEndJSONString(j == addPrms.length - 1);
                        }
                        stringBuilder.append(EveryBodyPool.JSONElements[7]);
                        break;
                    case GameObject.BOOLEANS:
                        stringBuilder.append(gameObjectLongFlags);
                        break;
                }
            }
        }
        stringBuilder.append(EveryBodyPool.JSONElements[1]);
        return modern ? EveryBodyPool.format(stringBuilder.toString()) : stringBuilder.toString();
    }

    private void addStartJSONString(int i){
        stringBuilder.append(EveryBodyPool.JSONElements[3]);
        stringBuilder.append(EveryBodyPool.JSONStrings[i]);
        stringBuilder.append(EveryBodyPool.JSONElements[3]);
        stringBuilder.append(EveryBodyPool.JSONElements[2]);
    }

    private void addEndJSONString(boolean last){
        if (!last)
            stringBuilder.append(EveryBodyPool.JSONElements[4]);
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
