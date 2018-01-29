package com.fogok.dataobjects.transactions.socserv;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fogok.dataobjects.ServerState;
import com.fogok.dataobjects.datastates.ClientToServerDataStates;
import com.fogok.dataobjects.datastates.ConnectionToServiceType;
import com.fogok.dataobjects.transactions.common.BaseTransaction;
import com.fogok.dataobjects.transactions.common.TokenizedTransaction;
import com.fogok.dataobjects.utils.Serialization;

public class KeepAliveTransaction extends TokenizedTransaction {

    private ServerState serverState;

    public KeepAliveTransaction(BaseTransaction baseTransaction) {
        super(baseTransaction);
        Serialization.instance.setServerState(serverState = new ServerState());
    }

    public KeepAliveTransaction(String token, ServerState serverState) {
        super(token, ConnectionToServiceType.CLIENT_TO_SERVICE, ClientToServerDataStates.KEEP_ALIVE_TO_SOC_SERV.ordinal());
        this.serverState = serverState;
    }

    public ServerState getServerState() {
        return serverState;
    }

    @Override
    public String toString() {
        return super.toString() + ", " + serverState.toString();
    }

    @Override
    public void write(Kryo kryo, Output output) {
        super.write(kryo, output);
        kryo.writeObject(output, serverState);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        super.read(kryo, input);
        kryo.readObject(input, ServerState.class);
    }
}
