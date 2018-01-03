package com.fogok.dataobjects.transactions.utils;

import com.fogok.dataobjects.datastates.ConnectionToServiceType;
import com.fogok.dataobjects.transactions.common.BaseTransaction;
import com.fogok.dataobjects.transactions.BaseReaderFromTransaction;

import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import static com.esotericsoftware.minlog.Log.debug;
import static com.esotericsoftware.minlog.Log.error;

public abstract class BaseTransactionReader {

    private static String logName = "TransactionReader";

    private TransactionExecutor transactionExecutor = new TransactionExecutor();
    private TransactionsAndReadersResolver transactionsAndReadersResolver = new TransactionsAndReadersResolver();
    private BaseTransaction transactionForComparing = new BaseTransaction(ConnectionToServiceType.CLIENT_TO_SERVICE, 0);

    public void readByteBufFromChannel(Channel channel, ByteBuf byteBuf){
        byte[] bytes = transactionExecutor.readByteBufAndDispose(byteBuf);
        transactionExecutor.fillTransaction(bytes, transactionForComparing);
        final BaseReaderFromTransaction reader = transactionsAndReadersResolver.getResolved().get(transactionForComparing);
        if (reader == null){
            error(String.format("%s: Reader is not added to TransactionsAndReadersResolver", logName));
            return;
        }

        BaseTransaction concreteTransaction = transactionExecutor.findAppropriateTransaction(bytes);
        if (concreteTransaction == null){
            channel.close();
            error(String.format("%s: Transaction is not defined in TransactionHelper.ApprObjResolverClientServerImpl", logName));
            return;
        }
        transactionExecutor.fillTransaction(bytes, concreteTransaction);

        debug(String.format("%s: Start read %s from %s", logName, reader.getClass().getSimpleName(), channel.remoteAddress()));
        ChannelFuture channelFuture = reader.read(channel, concreteTransaction, transactionExecutor);

        if (reader.isNeedActionAfterRead())
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    callBackFunc(channelFuture, reader);
                }
            });
    }

    private void callBackFunc(ChannelFuture channelFuture, BaseReaderFromTransaction callbackAction){
        callbackAction.actionAfterRead(channelFuture);
        debug(String.format("%s: After %s execution", logName, callbackAction.getClass().getSimpleName()));
    }

    public TransactionExecutor getTransactionExecutor() {
        return transactionExecutor;
    }

    public TransactionsAndReadersResolver getTransactionsAndReadersResolver() {
        return transactionsAndReadersResolver;
    }

    public void dispose(){
        transactionsAndReadersResolver.dispose();
    }

    public static class TransactionsAndReadersResolver {

        private Map<BaseTransaction, BaseReaderFromTransaction> resolved = new HashMap<BaseTransaction, BaseReaderFromTransaction>();

        public <Q extends BaseReaderFromTransaction> TransactionsAndReadersResolver addToResolve(Q baseReaderFromTransaction, BaseTransaction baseTransaction) {
            resolved.put(baseTransaction, baseReaderFromTransaction);
            debug(String.format("%s: %s has added to TransactionsAndReadersResolver", logName, baseReaderFromTransaction.getClass().getSimpleName()));
            return this;
        }

        public Map<BaseTransaction, BaseReaderFromTransaction> getResolved() {
            return resolved;
        }

        public void dispose(){
            resolved.clear();
        }
    }
}
