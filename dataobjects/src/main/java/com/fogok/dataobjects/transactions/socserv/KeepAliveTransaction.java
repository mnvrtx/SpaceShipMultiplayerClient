package com.fogok.dataobjects.transactions.socserv;

import com.fogok.dataobjects.datastates.ClientToServerDataStates;
import com.fogok.dataobjects.datastates.ConnectionToServiceType;
import com.fogok.dataobjects.transactions.common.BaseTransaction;
import com.fogok.dataobjects.transactions.common.TokenizedTransaction;

public class KeepAliveTransaction extends TokenizedTransaction {

    public KeepAliveTransaction(BaseTransaction baseTransaction) {
        super(baseTransaction);
    }

    public KeepAliveTransaction(String token) {
        super(token, ConnectionToServiceType.CLIENT_TO_SERVICE, ClientToServerDataStates.KEEP_ALIVE_TO_SOC_SERV.ordinal());
    }

}
