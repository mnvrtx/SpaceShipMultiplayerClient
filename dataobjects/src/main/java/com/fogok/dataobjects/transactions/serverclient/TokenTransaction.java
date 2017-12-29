package com.fogok.dataobjects.transactions.serverclient;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fogok.dataobjects.datastates.ConnectionToServiceType;
import com.fogok.dataobjects.datastates.ServerToClientDataStates;
import com.fogok.dataobjects.transactions.BaseTransaction;

public class TokenTransaction extends BaseTransaction {

    private String token;

    public TokenTransaction(BaseTransaction baseTransaction) {
        super(baseTransaction);
    }

    public TokenTransaction(String token) {
        super(ConnectionToServiceType.ServiceToClient, ServerToClientDataStates.TOKEN.ordinal());
        this.token = token;
    }

    @Override
    public void write(Kryo kryo, Output output) {
        super.write(kryo, output);
        output.writeString(token);
    }

    @Override
    public String toString() {
        return String.format("Token: %s", token);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        super.read(kryo, input);
        token = input.readString();
    }
}
