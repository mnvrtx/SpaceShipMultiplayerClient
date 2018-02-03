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

import java.io.ByteArrayOutputStream;
import java.util.BitSet;

import static com.esotericsoftware.minlog.Log.info;

public enum Serialization {

    instance;


    private Kryo kryo;

    private Input input = new Input();
    public Input getInput() {
        return input;
    }

    private Output output = new Output(new ByteArrayOutputStream());

    public Output getCleanedOutput(){
        output.clear();
        return getOutput();
    }

    public Output getOutput() {
        return output;
    }

    private EveryBodyPool everyBodyPool;
    public void setEveryBodyPoolToSync(EveryBodyPool everyBodyPool) {
        this.everyBodyPool = everyBodyPool;
    }
    public EveryBodyPool getEveryBodyPool() {
        return everyBodyPool;
    }

    private PlayerData playerData;
    public void setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
    }
    public PlayerData getPlayerData() {
        return playerData;
    }

    private ServerState serverState;
    public void setServerState(ServerState serverState) {
        this.serverState = serverState;
    }
    public ServerState getServerState() {
        return serverState;
    }

    {
        info("Init Serialization");
        kryo = new Kryo();

        //TODO: перенести сериализацию в сами классы! тут реальное говно с копипастом, нужно переделать по хорошему

        kryo.register(com.fogok.dataobjects.datastates.ServerToClientDataStates.class, new Serializer<com.fogok.dataobjects.datastates.ServerToClientDataStates>(){

            @Override
            public void write(Kryo kryo, Output output, com.fogok.dataobjects.datastates.ServerToClientDataStates object) {
                output.writeInt(object.ordinal(), true);
            }

            @Override
            public com.fogok.dataobjects.datastates.ServerToClientDataStates read(Kryo kryo, Input input, Class<com.fogok.dataobjects.datastates.ServerToClientDataStates> type) {
                return com.fogok.dataobjects.datastates.ServerToClientDataStates.values()[input.readInt(true)];
            }
        });

        kryo.register(com.fogok.dataobjects.datastates.ClientToServerDataStates.class, new Serializer<com.fogok.dataobjects.datastates.ClientToServerDataStates>(){

            @Override
            public void write(Kryo kryo, Output output, com.fogok.dataobjects.datastates.ClientToServerDataStates object) {
                output.writeInt(object.ordinal(), true);
            }

            @Override
            public com.fogok.dataobjects.datastates.ClientToServerDataStates read(Kryo kryo, Input input, Class<com.fogok.dataobjects.datastates.ClientToServerDataStates> type) {
                return com.fogok.dataobjects.datastates.ClientToServerDataStates.values()[input.readInt(true)];
            }
        });

//        kryo.register(ServerState.class, new Serializer<ServerState>() {
//            @Override
//            public void write(Kryo kryo, Output output, ServerState serverState) {
//
//                output.writeInt(serverState.getPlayersOnline(), true);
//
//                output.writeLong(serverState.getPlayerGlobalData().getPlayerId(), true);
//                output.writeFloat(serverState.getPlayerGlobalData().getX(), 0.02f, false);
//                output.writeFloat(serverState.getPlayerGlobalData().getY(), 0.02f, false);
//                output.writeInt(serverState.getPlayerGlobalData().getAdditParams().length, true);
//                output.writeFloats(serverState.getPlayerGlobalData().getAdditParams());
//                output.writeLong(serverState.getPlayerGlobalData().getLongFlags());
//
//            }
//
//            @Override
//            public ServerState read(Kryo kryo, Input input, Class<ServerState> type) {
//
//                serverState.setPlayersOnline(input.readInt(true));
//
//                serverState.getPlayerGlobalData().setPlayerId(input.readLong(true));
//                serverState.getPlayerGlobalData().setX(input.readFloat(0.02f, true));
//                serverState.getPlayerGlobalData().setY(input.readFloat(0.02f, true));
//                int lenghtAdditParams = input.readInt(true);
//                serverState.getPlayerGlobalData().setAdditParams(input.readFloats(lenghtAdditParams));
//                serverState.getPlayerGlobalData().setLongFlags(input.readLong());
//
//                return null;
//            }
//        });

        kryo.register(PlayerData.class, new Serializer<PlayerData>(){

            @Override
            public void write(Kryo kryo, Output output, PlayerData playerData) {

                output.writeLong(playerData.getConsoleState().getPlayerId(), true);
                output.writeFloat(playerData.getConsoleState().getX(), 1000f, false);
                output.writeFloat(playerData.getConsoleState().getY(), 1000f, false);
                output.writeInt(playerData.getConsoleState().getAdditParams().length, true);
                output.writeFloats(playerData.getConsoleState().getAdditParams());
                output.writeInt(playerData.getConsoleState().getStringInformation().length, true);
                output.writeChars(playerData.getConsoleState().getStringInformation());
                output.writeLong(playerData.getConsoleState().getLongFlags());

            }

            @Override
            public PlayerData read(Kryo kryo, Input input, Class<PlayerData> aClass) {

                playerData.getConsoleState().setPlayerId(input.readLong(true));
                playerData.getConsoleState().setX(input.readFloat(1000f, false));
                playerData.getConsoleState().setY(input.readFloat(1000f, false));
                int lenghtAdditParams = input.readInt(true);
                playerData.getConsoleState().setAdditParams(input.readFloats(lenghtAdditParams));
                int lenghtStringInformParams = input.readInt(true);
                playerData.getConsoleState().setStringInformation(input.readChars(lenghtStringInformParams));
                playerData.getConsoleState().setLongFlags(input.readLong());

                return null;
            }

        });

        kryo.register(EveryBodyPool.class, new Serializer<EveryBodyPool>() {

            @Override
            public void write(Kryo kryo, Output output, EveryBodyPool everyBodyPool) {  ///

                Array<Array<GameObject>> typedObjects = everyBodyPool.getAllObjects();
                output.writeInt(typedObjects.size, true);   //objectTypeSize
                ////////////////////////////////////////////////////////////////////////////////////////////////////////
                for (int i = 0; i < typedObjects.size; i++) {
                    Array<GameObject> allObjectsFromOneType = typedObjects.get(i);

                    output.writeInt(i, true);   //objectTypeIdx
                    output.writeInt(allObjectsFromOneType.size, true);  //objectsFromOneTypeCount

                    for (int j = 0; j < allObjectsFromOneType.size; j++) {
                        GameObject gameObject = allObjectsFromOneType.get(j);

                        output.writeLong(gameObject.getPlayerId(), true);
                        output.writeFloat(gameObject.getX(), 1000f, false);
                        output.writeFloat(gameObject.getY(), 1000f, false);
                        output.writeInt(gameObject.getAdditParams().length, true);
                        output.writeFloats(gameObject.getAdditParams());
                        output.writeInt(gameObject.getStringInformation().length, true);
                        output.writeChars(gameObject.getStringInformation());
                        output.writeLong(gameObject.getLongFlags());
                    }
                }
            }

            @Override
            public EveryBodyPool read(Kryo kryo, Input input, Class<EveryBodyPool> type) {

                Array<Array<GameObject>> typedObjects = everyBodyPool.getAllObjects();
                int targetTypedObjectsSize = input.readInt(true);   //objectTypeSize;
                ////////////////////////////////////////////////////////////////////////////////////////////////////////
                for (int i = 0; i < targetTypedObjectsSize; i++) {
                    int objectTypeIdx = input.readInt(true);
                    int objectsFromOneTypeCount = input.readInt(true);

                    int localObjectsFromOneTypeCount = typedObjects.get(objectTypeIdx).size;
                    balance(objectTypeIdx, localObjectsFromOneTypeCount, objectsFromOneTypeCount);

                    for (int j = 0; j < objectsFromOneTypeCount; j++) {
                        GameObject gameObject = everyBodyPool.getAllObjects().get(objectTypeIdx).get(j);

                        gameObject.setPlayerId(input.readLong(true));
                        gameObject.setX(input.readFloat(1000f, false));
                        gameObject.setY(input.readFloat(1000f, false));
                        int lenghtAdditParams = input.readInt(true);
                        gameObject.setAdditParams(input.readFloats(lenghtAdditParams));
                        int lenghtStringInformParams = input.readInt(true);
                        gameObject.setStringInformation(input.readChars(lenghtStringInformParams));
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
