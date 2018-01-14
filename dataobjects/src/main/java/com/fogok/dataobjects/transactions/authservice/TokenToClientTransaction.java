package com.fogok.dataobjects.transactions.authservice;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fogok.dataobjects.datastates.ConnectionToServiceType;
import com.fogok.dataobjects.datastates.ServerToClientDataStates;
import com.fogok.dataobjects.transactions.common.BaseTransaction;
import com.fogok.dataobjects.transactions.common.TokenizedTransaction;

public class TokenToClientTransaction extends TokenizedTransaction {

    private String relayBalancerIp;
    private String nickName;

    public TokenToClientTransaction(BaseTransaction baseTransaction) {
        super(baseTransaction);
    }

    public TokenToClientTransaction(String token, String nickName, String relayBalancerIp) {
        super(token, ConnectionToServiceType.SERVICE_TO_CLIENT, ServerToClientDataStates.TOKEN.ordinal());
        this.nickName = nickName;
        this.relayBalancerIp = relayBalancerIp;
    }

    public String getNickName() {
        return nickName;
    }

    public String getRelayBalancerIp() {
        return relayBalancerIp;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", Nickname: '%s', RelayBalancerIp: '%s'", getNickName(), getRelayBalancerIp());
    }

    @Override
    public void write(Kryo kryo, Output output) {
        super.write(kryo, output);
        output.writeString(nickName);
        output.writeString(relayBalancerIp);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        super.read(kryo, input);
        nickName = input.readString();
        relayBalancerIp = input.readString();
    }
}
