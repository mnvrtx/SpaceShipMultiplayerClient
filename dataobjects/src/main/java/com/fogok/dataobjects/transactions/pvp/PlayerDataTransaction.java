package com.fogok.dataobjects.transactions.pvp;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fogok.dataobjects.PlayerData;
import com.fogok.dataobjects.datastates.ClientToServerDataStates;
import com.fogok.dataobjects.datastates.ConnectionToServiceType;
import com.fogok.dataobjects.transactions.common.BaseTransaction;
import com.fogok.dataobjects.utils.Serialization;

public class PlayerDataTransaction extends BaseTransaction {

    private PlayerData playerData;

    public PlayerDataTransaction(BaseTransaction baseTransaction) {
        super(baseTransaction);
        setPlayerDataFromSerializateInstance();
    }

    public PlayerDataTransaction() {
        super(ConnectionToServiceType.CLIENT_TO_SERVICE, ClientToServerDataStates.PLAYER_DATA_WITH_CONSOLE_STATE.ordinal());
        setPlayerDataFromSerializateInstance();
    }

    public void setPlayerDataFromSerializateInstance() {
        this.playerData = Serialization.getInstance().getPlayerData();
    }

    @Override
    public String toString() {
        return playerData.toString();
    }

    @Override
    public void write(Kryo kryo, Output output) {
        super.write(kryo, output);
        kryo.writeObject(output, playerData);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        super.read(kryo, input);
        kryo.readObject(input, PlayerData.class);
    }
}
