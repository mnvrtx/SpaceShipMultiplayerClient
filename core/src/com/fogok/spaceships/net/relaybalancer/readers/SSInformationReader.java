package com.fogok.spaceships.net.relaybalancer.readers;

import com.fogok.dataobjects.transactions.BaseReaderFromTransaction;
import com.fogok.dataobjects.transactions.relaybalancerservice.SSInformationTransaction;
import com.fogok.dataobjects.transactions.utils.TransactionExecutor;
import com.fogok.spaceships.net.commonhandlers.BaseChannelHandler;
import com.fogok.spaceships.net.controllers.NetRootController;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

public class SSInformationReader implements BaseReaderFromTransaction<SSInformationTransaction> {

    private NetRootController netRootController;
    private BaseChannelHandler handler;

    public SSInformationReader(NetRootController netRootController, BaseChannelHandler handler) {
        this.netRootController = netRootController;
        this.handler = handler;
    }

    @Override
    public ChannelFuture read(Channel channel, SSInformationTransaction transaction, TransactionExecutor transactionExecutor) {
        netRootController.getNetRelayBalancerController().recieveSocServIp(transaction.getSocialServerIp());
        handler.channelCompleteAction();
        return channel.disconnect();
    }

    @Override
    public boolean isNeedActionAfterRead() {
        return true;
    }

    @Override
    public void actionAfterRead(ChannelFuture channelFuture) {
        netRootController.getNetSocServController().connectToSS();
    }
}
