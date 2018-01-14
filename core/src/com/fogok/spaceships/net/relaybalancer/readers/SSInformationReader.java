package com.fogok.spaceships.net.relaybalancer.readers;

import com.fogok.dataobjects.transactions.BaseReaderFromTransaction;
import com.fogok.dataobjects.transactions.relaybalancerservice.SSInformationTransaction;
import com.fogok.dataobjects.transactions.utils.TransactionExecutor;
import com.fogok.spaceships.net.controllers.NetRelayBalancerController;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

public class SSInformationReader implements BaseReaderFromTransaction<SSInformationTransaction> {

    private NetRelayBalancerController netRelayBalancerController;

    public SSInformationReader(NetRelayBalancerController netRelayBalancerController) {
        this.netRelayBalancerController = netRelayBalancerController;
    }

    @Override
    public ChannelFuture read(Channel channel, SSInformationTransaction transaction, TransactionExecutor transactionExecutor) {
        netRelayBalancerController.receiveSSInfo(transaction.getSocialServerIp());
        return channel.disconnect();
    }

    @Override
    public boolean isNeedActionAfterRead() {
        return true;
    }

    @Override
    public void actionAfterRead(ChannelFuture channelFuture) {
        netRelayBalancerController.connectToSS();
    }
}
