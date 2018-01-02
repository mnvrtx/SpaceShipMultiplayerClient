package com.fogok.spaceships.net.auth.actions;

import com.fogok.dataobjects.transactions.actions.BaseActionFromTransaction;
import com.fogok.dataobjects.transactions.serverclient.TokenTransaction;
import com.fogok.dataobjects.transactions.utils.TransactionHelper;
import com.fogok.spaceships.net.controllers.NetRootController;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

public class TokenAction implements BaseActionFromTransaction<TokenTransaction>{

    private NetRootController netRootController;

    public TokenAction(NetRootController netRootController) {
        this.netRootController = netRootController;
    }

    /**
     * Получили токен, все хорошо
     */
    @Override
    public ChannelFuture execute(Channel channel, TokenTransaction transaction, TransactionHelper transactionHelper) {
        netRootController.getNetAuthController().successConnect(transaction.getToken(), transaction.getNickName(), transaction.getRelayBalancerIp());
        channel.disconnect();
        return null;
    }

    @Override
    public boolean isNeedActionAfterExecution() {
        return false;
    }

    @Override
    public void actionAfterExecution(ChannelFuture channelFuture) {

    }
}
