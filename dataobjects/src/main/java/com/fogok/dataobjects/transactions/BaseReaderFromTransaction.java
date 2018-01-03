package com.fogok.dataobjects.transactions;

import com.fogok.dataobjects.transactions.common.BaseTransaction;
import com.fogok.dataobjects.transactions.utils.TransactionExecutor;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

public interface BaseReaderFromTransaction<T extends BaseTransaction> {
    ChannelFuture read(Channel channel, T transaction, TransactionExecutor transactionExecutor);
    boolean isNeedActionAfterRead();
    void actionAfterRead(ChannelFuture channelFuture);
}
