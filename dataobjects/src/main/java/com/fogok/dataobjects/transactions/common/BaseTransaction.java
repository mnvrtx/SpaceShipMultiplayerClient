package com.fogok.dataobjects.transactions.common;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fogok.dataobjects.datastates.ConnectionToServiceType;

public class BaseTransaction implements KryoSerializable {

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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + connectionToServiceType.ordinal();
        result = prime * result + clientOrServiceToServerDataState;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        BaseTransaction other = (BaseTransaction)obj;
        if (connectionToServiceType.ordinal() != other.connectionToServiceType.ordinal()) return false;
        if (clientOrServiceToServerDataState != other.clientOrServiceToServerDataState) return false;
        return true;
    }

    @Override
    public String toString() {
        return "";
    }
}
