package com.fogok.dataobjects.transactions.pvp;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fogok.dataobjects.datastates.ConnectionToServiceType;
import com.fogok.dataobjects.datastates.ServerToClientDataStates;
import com.fogok.dataobjects.transactions.common.BaseTransaction;
import com.fogok.dataobjects.utils.EveryBodyPool;
import com.fogok.dataobjects.utils.Serialization;

public class EveryBodyPoolTransaction extends BaseTransaction {

    private EveryBodyPool everyBodyPool;

    public EveryBodyPoolTransaction(BaseTransaction baseTransaction) {
        super(baseTransaction);
        setEveryBodyPoolFromSerializateInstance();
    }

    public EveryBodyPoolTransaction() {
        super(ConnectionToServiceType.SERVICE_TO_CLIENT, ServerToClientDataStates.EVERY_BODY_POOL.ordinal());
        setEveryBodyPoolFromSerializateInstance();
    }

    public void setEveryBodyPoolFromSerializateInstance() {
        this.everyBodyPool = Serialization.getInstance().getEveryBodyPool();
    }

    @Override
    public String toString() {
        return everyBodyPool.toString();
    }

    @Override
    public void write(Kryo kryo, Output output) {
        super.write(kryo, output);
        kryo.writeObject(output, everyBodyPool);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        super.read(kryo, input);
        kryo.readObject(input, EveryBodyPool.class);
    }
}