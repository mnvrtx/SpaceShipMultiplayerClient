package com.fogok.dataobjects.transactions.common;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fogok.dataobjects.datastates.ClientToServerDataStates;
import com.fogok.dataobjects.datastates.ConnectionToServiceType;
import com.fogok.dataobjects.datastates.RequestTypeInTokenToServiceTrnsn;

public class TokenToServiceTransaction extends BaseTransaction {

    private RequestTypeInTokenToServiceTrnsn requestTypeInTokenToServiceTrnsn;
    private String token;

    public TokenToServiceTransaction(BaseTransaction baseTransaction) {
        super(baseTransaction);
    }

    public TokenToServiceTransaction(String token, RequestTypeInTokenToServiceTrnsn requestTypeInTokenToServiceTrnsn) {
        super(ConnectionToServiceType.CLIENT_TO_SERVICE, ClientToServerDataStates.TOKEN_WITH_ADDITIONAL_INFORMATION.ordinal());
        this.requestTypeInTokenToServiceTrnsn = requestTypeInTokenToServiceTrnsn;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public RequestTypeInTokenToServiceTrnsn getRequestTypeInTokenToServiceTrnsn() {
        return requestTypeInTokenToServiceTrnsn;
    }

    @Override
    public String toString() {
        return String.format("Token: '%s', RequestType: '%s'", getToken(), getRequestTypeInTokenToServiceTrnsn().name());
    }

    @Override
    public void write(Kryo kryo, Output output) {
        super.write(kryo, output);
        output.writeInt(requestTypeInTokenToServiceTrnsn.ordinal(), true);
        output.writeString(token);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        super.read(kryo, input);
        requestTypeInTokenToServiceTrnsn = RequestTypeInTokenToServiceTrnsn.values()[input.readInt(true)];
        token = input.readString();
    }
}
