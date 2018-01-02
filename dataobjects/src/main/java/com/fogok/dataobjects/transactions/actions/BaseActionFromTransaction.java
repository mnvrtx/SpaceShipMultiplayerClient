package com.fogok.dataobjects.transactions.actions;

import com.fogok.dataobjects.transactions.BaseTransaction;
import com.fogok.dataobjects.transactions.utils.TransactionHelper;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

public interface BaseActionFromTransaction<T extends BaseTransaction> {
    //TODO: переименовать Action в Reader
    ChannelFuture execute(Channel channel, T transaction, TransactionHelper transactionHelper);
    boolean isNeedActionAfterExecution();
    void actionAfterExecution(ChannelFuture channelFuture);
}
