package com.fogok.dataobjects.transactions.common;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fogok.dataobjects.datastates.ConnectionToServiceType;

public class TokenizedTransaction extends BaseTransaction{

    private String token;

    public TokenizedTransaction(BaseTransaction baseTransaction) {
        super(baseTransaction);
    }

    public TokenizedTransaction(String token, ConnectionToServiceType connectionToServiceType, int clientOrServiceToServerDataState) {
        super(connectionToServiceType, clientOrServiceToServerDataState);
        this.token = token;
    }

    @Override
    public String toString() {
        return String.format("Token: '%s'", token);
    }

    public String getToken() {
        return token;
    }

    @Override
    public void write(Kryo kryo, Output output) {
        super.write(kryo, output);
        output.writeString(token);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        super.read(kryo, input);
        token = input.readString();
    }
}
