package com.fogok.dataobjects.transactions.common;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fogok.dataobjects.datastates.ConnectionToServiceType;
import com.fogok.dataobjects.datastates.ServerToClientDataStates;

public class ConnectionInformationTransaction extends BaseTransaction{

    public static final int RESPONSE_CODE_OK = 2;
    public static final int RESPONSE_CODE_ERROR = 1;
    public static final int RESPONSE_CODE_SERVICE_SHUTDOWN = 0;

    private int responseCode;

    public ConnectionInformationTransaction(BaseTransaction baseTransaction) {
        super(baseTransaction);
    }

    public ConnectionInformationTransaction(int responseCode) {
        super(ConnectionToServiceType.SERVICE_TO_CLIENT, ServerToClientDataStates.CONNECTION_TO_SERVICE_INFORMATION.ordinal());
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return responseCode;
    }

    @Override
    public String toString() {
        return String.format("ResponseCode: '%s'", getResponseCode());
    }

    @Override
    public void write(Kryo kryo, Output output) {
        super.write(kryo, output);
        output.writeInt(responseCode, true);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        super.read(kryo, input);
        responseCode = input.readInt(true);
    }
}
