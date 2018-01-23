package com.fogok.spaceships.net.readers;

import com.fogok.dataobjects.transactions.BaseReaderFromTransaction;
import com.fogok.dataobjects.transactions.socserv.KeepAliveTransaction;
import com.fogok.dataobjects.transactions.utils.TransactionExecutor;
import com.fogok.spaceships.net.NetRootController;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

public class SocSrvServerStateReader implements BaseReaderFromTransaction<KeepAliveTransaction> {

    private NetRootController netRootController;

    public SocSrvServerStateReader(NetRootController netRootController) {
        this.netRootController = netRootController;
    }

    @Override
    public ChannelFuture read(Channel channel, KeepAliveTransaction transaction, TransactionExecutor transactionExecutor) {
        netRootController.getUiCallBacks().getSocServCallBack().recieveServerState(transaction.getServerState());
        return null;
    }

    @Override
    public boolean isNeedActionAfterRead() {
        return false;
    }

    @Override
    public void actionAfterRead(ChannelFuture channelFuture) {

    }
}
