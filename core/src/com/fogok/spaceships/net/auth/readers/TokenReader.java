package com.fogok.spaceships.net.auth.readers;

import com.fogok.dataobjects.transactions.BaseReaderFromTransaction;
import com.fogok.dataobjects.transactions.authservice.TokenToClientTransaction;
import com.fogok.dataobjects.transactions.utils.TransactionExecutor;
import com.fogok.spaceships.net.controllers.NetAuthController;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

public class TokenReader implements BaseReaderFromTransaction<TokenToClientTransaction> {

    private NetAuthController netAuthController;

    public TokenReader(NetAuthController netAuthController) {
        this.netAuthController = netAuthController;
    }

    /**
     * Получили токен, все хорошо
     */
    @Override
    public ChannelFuture read(Channel channel, TokenToClientTransaction transaction, TransactionExecutor transactionExecutor) {
        netAuthController.receiveToken(transaction.getToken(), transaction.getNickName(), transaction.getRelayBalancerIp());
        return channel.disconnect();
    }

    @Override
    public boolean isNeedActionAfterRead() {
        return true;
    }

    @Override
    public void actionAfterRead(ChannelFuture channelFuture) {
        netAuthController.connectToRelayBalancer();
    }
}
