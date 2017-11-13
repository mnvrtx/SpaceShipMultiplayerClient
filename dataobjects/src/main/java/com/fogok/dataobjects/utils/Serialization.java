package com.fogok.dataobjects.utils;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fogok.dataobjects.GameObject;
import com.fogok.dataobjects.GameObjectsType;
import com.fogok.dataobjects.PlayerData;
import com.fogok.dataobjects.ServerState;
import com.fogok.dataobjects.utils.libgdxexternals.Array;

import java.util.BitSet;

public class Serialization {

    private static Serialization instance;

    public static Serialization getInstance() {
        return instance == null ? instance = new Serialization() : instance;
    }

    private Kryo kryo;
    private EveryBodyPool everyBodyPool;
    private PlayerData playerData;
    private ServerState serverState;

    public void setParams(EveryBodyPool everyBodyPool) {
        this.everyBodyPool = everyBodyPool;
    }

    public void setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
    }

    public void setServerState(ServerState serverState) {
        this.serverState = serverState;
    }

    public Serialization() {

        kryo = new Kryo();

        kryo.register(ServerState.class, new Serializer<ServerState>() {
            @Override
            public void write(Kryo kryo, Output output, ServerState serverState) {

                output.writeInt(serverState.getPlayersOnline(), true);
                output.writeInt(serverState.getPlayerGlobalData().getDataFloats().length, true);
                output.writeFloats(serverState.getPlayerGlobalData().getDataFloats());
                output.writeLong(convert(serverState.getPlayerGlobalData().getLongFlags()), true);

            }

            @Override
            public ServerState read(Kryo kryo, Input input, Class<ServerState> type) {

                serverState.setPlayersOnline(input.readInt(true));
                int lenghtDataFloats = input.readInt(true);
                serverState.getPlayerGlobalData().setDataFloats(input.readFloats(lenghtDataFloats));
                convert(serverState.getPlayerGlobalData().getLongFlags(), input.readLong(true));

                return null;
            }
        });

        kryo.register(PlayerData.class, new Serializer<PlayerData>(){

            @Override
            public void write(Kryo kryo, Output output, PlayerData playerData) {

                output.writeInt(playerData.getAUID(), true);
                output.writeBoolean(playerData.isHasServeredPlayerData());
                if (playerData.isHasServeredPlayerData()) {
                    output.writeFloat(playerData.getConsoleState().getX(), 0.02f, false);
                    output.writeFloat(playerData.getConsoleState().getY(), 0.02f, false);
                    output.writeInt(playerData.getConsoleState().getAdditParams().length, true);
                    output.writeFloats(playerData.getConsoleState().getAdditParams());
                    output.writeLong(convert(playerData.getConsoleState().getLongFlags()), true);
                }

            }

            @Override
            public PlayerData read(Kryo kryo, Input input, Class<PlayerData> aClass) {

                playerData.setAUID(input.readInt(true));
                if (input.readBoolean()) {
                    playerData.getConsoleState().setX(input.readFloat(0.02f, false));
                    playerData.getConsoleState().setY(input.readFloat(0.02f, false));
                    int additParamsLength = input.readInt(true);
                    playerData.getConsoleState().setAdditParams(input.readFloats(additParamsLength));
                    convert(playerData.getConsoleState().getLongFlags(), input.readLong());
                }

                return null;
            }

        });

        kryo.register(EveryBodyPool.class, new Serializer<EveryBodyPool>() {

            @Override
            public void write(Kryo kryo, Output output, EveryBodyPool everyBodyPool) {  ///

                Array<Array<GameObject>> typedObjects = everyBodyPool.getAllObjects();

                output.writeInt(everyBodyPool.getTypeObjectsCount(), true);     //2
                for (int i = 0; i < typedObjects.size; i++) {
                    Array<GameObject> allObjectsFromOneType = typedObjects.get(i);
                    if (allObjectsFromOneType.size != 0) {

                        output.writeInt(allObjectsFromOneType.size, true);  //2 4
                        output.writeInt(i, true);   //2 4 0
                        for (int j = 0; j < allObjectsFromOneType.size; j++) {
                            GameObject gameObject = allObjectsFromOneType.get(j);

                            output.writeLong(gameObject.getPlayerId(), true);
                            output.writeFloat(gameObject.getX(), 0.02f, false);
                            output.writeFloat(gameObject.getY(), 0.02f, false);
                            output.writeInt(gameObject.getAdditParams().length, true);
                            output.writeFloats(gameObject.getAdditParams());
                            output.writeLong(gameObject.getLongFlags());
                        }

                    }
                }

            }

            @Override
            public EveryBodyPool read(Kryo kryo, Input input, Class<EveryBodyPool> type) {

                int typeObjectsCount = input.readInt(true); // 2
                for (int i = 0; i < typeObjectsCount; i++) {
                    int objectsFromOneTypeCount = input.readInt(true); //2 4
                    int localObjectsFromOneTypeCount = everyBodyPool.getAllObjects().get(i).size;
                    int objectTypeIdx = input.readInt(true);
                    balance(objectTypeIdx, localObjectsFromOneTypeCount, objectsFromOneTypeCount);
                    for (int j = 0; j < objectsFromOneTypeCount; j++) {
                        GameObject gameObject = everyBodyPool.getAllObjects().get(objectTypeIdx).get(j);

                        gameObject.setPlayerId(input.readLong(true));
                        gameObject.setX(input.readFloat(0.02f, true));
                        gameObject.setY(input.readFloat(0.02f, true));
                        int lenghtAdditParams = input.readInt(true);
                        gameObject.setAdditParams(input.readFloats(lenghtAdditParams));
                        gameObject.setLongFlags(input.readLong());
                    }
                }

                return null;
            }
        });


//        kryo.register(GameObject.class, new Serializer<GameObject>(){
//
//            @Override
//            public void write(Kryo kryo, Output output, GameObject gameObject) {
//
//            }
//
//            @Override
//            public GameObject read(Kryo kryo, Input input, Class<GameObject> aClass) {
//                GameObject gameObject = everyBodyPool.obtain();
//                gameObject.setPlayerId(input.readLong(true));
//                gameObject.setType(input.readInt(true));
//                gameObject.setX(input.readFloat(0.02f, true));
//                gameObject.setY(input.readFloat(0.02f, true));
//                int lenghtAdditParams = input.readInt(true);
//                gameObject.setDataFloats(input.readFloats(lenghtAdditParams));
//                gameObject.setLongFlags(input.readLong());
//                return gameObject;
//            }
//
//        });

    }

    private void balance(int type, int oldSize, int newSize){
        for (int i = 0; i < Math.abs(newSize - oldSize); i++)
            if (oldSize < newSize)
                everyBodyPool.obtain(GameObjectsType.values()[type]);
            else
                everyBodyPool.free(GameObjectsType.values()[type]);
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
