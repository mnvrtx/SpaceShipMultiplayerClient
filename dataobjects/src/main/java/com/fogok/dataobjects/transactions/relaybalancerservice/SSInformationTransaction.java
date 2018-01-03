package com.fogok.dataobjects.transactions.relaybalancerservice;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fogok.dataobjects.datastates.ConnectionToServiceType;
import com.fogok.dataobjects.datastates.ServerToClientDataStates;
import com.fogok.dataobjects.transactions.common.BaseTransaction;

public class SSInformationTransaction extends BaseTransaction{

    private String socialServerIp;

    public SSInformationTransaction(BaseTransaction baseTransaction) {
        super(baseTransaction);
    }

    public SSInformationTransaction(String socialServerIp) {
        super(ConnectionToServiceType.SERVICE_TO_CLIENT, ServerToClientDataStates.SS_INFORMATION.ordinal());
        this.socialServerIp = socialServerIp;
    }

    public String getSocialServerIp() {
        return socialServerIp;
    }

    @Override
    public void write(Kryo kryo, Output output) {
        super.write(kryo, output);
        output.writeString(socialServerIp);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        super.read(kryo, input);
        socialServerIp = input.readString();
    }
}
