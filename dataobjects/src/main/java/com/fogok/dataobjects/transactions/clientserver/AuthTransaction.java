package com.fogok.dataobjects.transactions.clientserver;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fogok.dataobjects.datastates.ClientToServerDataStates;
import com.fogok.dataobjects.datastates.ConnectionToServiceType;
import com.fogok.dataobjects.transactions.BaseTransaction;

public class AuthTransaction extends BaseTransaction {

    private String login;
    private String password;

    public AuthTransaction(BaseTransaction baseTransaction) {
        super(baseTransaction);
    }

    public AuthTransaction(String login, String password) {
        super(ConnectionToServiceType.ClientToService, ClientToServerDataStates.CONNECT_TO_SERVER.ordinal());
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return String.format("Login: '%s', Password: '%s'", getLogin(), getPassword());
    }

    @Override
    public void write(Kryo kryo, Output output) {
        super.write(kryo, output);
        output.writeString(login);
        output.writeString(password);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        super.read(kryo, input);
        login = input.readString();
        password = input.readString();
    }
}
