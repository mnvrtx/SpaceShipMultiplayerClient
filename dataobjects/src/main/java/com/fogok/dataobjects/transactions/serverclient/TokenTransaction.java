package com.fogok.dataobjects.transactions.serverclient;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fogok.dataobjects.datastates.ConnectionToServiceType;
import com.fogok.dataobjects.datastates.ServerToClientDataStates;
import com.fogok.dataobjects.transactions.BaseTransaction;

public class TokenTransaction extends BaseTransaction {

    private String relayBalancerIp;
    private String token;
    private String nickName;

    public TokenTransaction(BaseTransaction baseTransaction) {
        super(baseTransaction);
    }

    public TokenTransaction(String token, String nickName, String relayBalancerIp) {
        super(ConnectionToServiceType.ServiceToClient, ServerToClientDataStates.TOKEN.ordinal());
        this.token = token;
        this.nickName = nickName;
        this.relayBalancerIp = relayBalancerIp;
    }

    public String getNickName() {
        return nickName;
    }

    public String getToken() {
        return token;
    }

    public String getRelayBalancerIp() {
        return relayBalancerIp;
    }

    @Override
    public void write(Kryo kryo, Output output) {
        super.write(kryo, output);
        output.writeString(token);
        output.writeString(nickName);
        output.writeString(relayBalancerIp);
    }

    @Override
    public String toString() {
        return String.format("Token: %s, Nickname: %s", token, nickName);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        super.read(kryo, input);
        token = input.readString();
        nickName = input.readString();
        relayBalancerIp = input.readString();
    }
}
