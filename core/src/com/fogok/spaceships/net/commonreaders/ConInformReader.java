package com.fogok.spaceships.net.commonreaders;

import com.fogok.dataobjects.transactions.BaseReaderFromTransaction;
import com.fogok.dataobjects.transactions.common.ConnectionInformationTransaction;
import com.fogok.dataobjects.transactions.utils.TransactionExecutor;
import com.fogok.spaceships.net.exception.DefaultExceptionCallBack;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import static com.esotericsoftware.minlog.Log.error;
import static com.esotericsoftware.minlog.Log.info;
import static com.esotericsoftware.minlog.Log.warn;

public class ConInformReader implements BaseReaderFromTransaction<ConnectionInformationTransaction> {

    private DefaultExceptionCallBack defaultExceptionCallBack;
    private ConInformCallBack conInformCallBack;

    public ConInformReader(DefaultExceptionCallBack defaultExceptionCallBack) {
        this(null, defaultExceptionCallBack);
    }

    public ConInformReader(ConInformCallBack conInformCallBack, DefaultExceptionCallBack defaultExceptionCallBack) {
        this.defaultExceptionCallBack = defaultExceptionCallBack;
        this.conInformCallBack = conInformCallBack;
    }

    @Override
    public ChannelFuture read(Channel channel, ConnectionInformationTransaction transaction, TransactionExecutor transactionExecutor) {
        String cause;
        if (conInformCallBack != null)
            conInformCallBack.receiveResponse(channel, transaction.getResponseCode());
        else
            switch (transaction.getResponseCode()) {
                case ConnectionInformationTransaction.RESPONSE_CODE_OK:
                    info("OK connect to service with token");
                    break;
                case ConnectionInformationTransaction.RESPONSE_CODE_ERROR:
                    cause = "Bad token... Disconnect from service";
                    warn(cause);
                    defaultExceptionCallBack.exceptionConnect(new Exception(cause));
                    channel.close();
                    break;
                case ConnectionInformationTransaction.RESPONSE_CODE_SERVICE_SHUTDOWN:
                    cause = "Service is SHUTDOWN";
                    error(cause);
                    defaultExceptionCallBack.exceptionConnect(new Exception(cause));
                    break;
            }
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
