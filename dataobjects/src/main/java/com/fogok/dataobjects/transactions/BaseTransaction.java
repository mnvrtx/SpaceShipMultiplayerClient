package com.fogok.dataobjects.transactions;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fogok.dataobjects.datastates.ConnectionToServiceType;

public abstract class BaseTransaction implements KryoSerializable {

    protected ConnectionToServiceType connectionToServiceType;
    protected int clientOrServiceToServerDataState;

    public BaseTransaction(BaseTransaction baseTransaction) {
        this.clientOrServiceToServerDataState = baseTransaction.clientOrServiceToServerDataState;
        this.connectionToServiceType = baseTransaction.connectionToServiceType;
    }

    public BaseTransaction(ConnectionToServiceType connectionToServiceType, int clientOrServiceToServerDataState) {
        this.connectionToServiceType = connectionToServiceType;
        this.clientOrServiceToServerDataState = clientOrServiceToServerDataState;
    }

    public ConnectionToServiceType getConnectionToServiceType() {
        return connectionToServiceType;
    }

    public int getClientOrServiceToServerDataState() {
        return clientOrServiceToServerDataState;
    }

    @Override
    public void write(Kryo kryo, Output output) {
        output.writeInt(connectionToServiceType.ordinal(), true);
        output.writeInt(clientOrServiceToServerDataState, true);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        connectionToServiceType = ConnectionToServiceType.values()[input.readInt(true)];
        clientOrServiceToServerDataState = input.readInt(true);
    }

    @Override
    public String toString() {
        return "";
    }
}
