package com.fogok.dataobjects.utils;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fogok.dataobjects.GameObject;
import com.fogok.dataobjects.utils.libgdxexternals.Array;

import java.util.BitSet;

public class Serialization {

    private static Serialization instance;

    public static Serialization getInstance() {
        return instance == null ? instance = new Serialization() : instance;
    }

    private Kryo kryo;
    private final EveryBodyPool everyBodyPool;

    public Serialization(EveryBodyPool everyBodyPool) {
        this.everyBodyPool = everyBodyPool;
        kryo.register(EveryBodyPool.class, new Serializer<EveryBodyPool>(){

            @Override
            public void write(Kryo kryo, Output output, EveryBodyPool everyBodyPool) {

            }

            @Override
            public EveryBodyPool read(Kryo kryo, Input input, Class<EveryBodyPool> aClass) {
                return null;
            }
        });

        kryo.register(GameObject.class, new Serializer<GameObject>(){

            @Override
            public void write(Kryo kryo, Output output, GameObject gameObject) {

                if (gameObject == null || gameObject.getAdditParams().length == 0)  return;

                output.writeLong(gameObject.getPlayerId(), true);
                output.writeInt(gameObject.getType(), true);
                output.writeFloat(gameObject.getX(), 0.02f, true);
                output.writeFloat(gameObject.getY(), 0.02f, true);
                output.writeInt(gameObject.getAdditParams().length, true);
                output.writeFloats(gameObject.getAdditParams());
                output.writeLong(gameObject.getLongFlags());
            }

            @Override
            public GameObject read(Kryo kryo, Input input, Class<GameObject> aClass) {
                GameObject gameObject = everyBodyPool.obtain();
                gameObject.setPlayerId(input.readLong(true));
                gameObject.setType(input.readInt(true));
                gameObject.setX(input.readFloat(0.02f, true));
                gameObject.setY(input.readFloat(0.02f, true));
                int lenghtAdditParams = input.readInt(true);
                gameObject.setAdditParams(input.readFloats(lenghtAdditParams));
                gameObject.setLongFlags(input.readLong());
                return gameObject;
            }

        });

    }

    public Kryo getKryo() {
        return kryo;
    }


    public static void convert(BitSet bitSet, long value) {
        bitSet.clear();
        int index = 0;
        while (value != 0L) {
            if (value % 2L != 0) {
                bitSet.set(index);
            }
            ++index;
            value = value >>> 1;
        }
    }

    public static long convert(BitSet bits) {
        long value = 0L;
        for (int i = 0; i < bits.length(); ++i) {
            value += bits.get(i) ? (1L << i) : 0L;
        }
        return value;
    }

}
