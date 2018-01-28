package com.fogok.spaceships.net.handlers;

import com.fogok.dataobjects.datastates.ClientToServerDataStates;
import com.fogok.dataobjects.datastates.ConnectionToServiceType;
import com.fogok.dataobjects.datastates.RequestTypeInTokenToServiceTrnsn;
import com.fogok.dataobjects.datastates.ServerToClientDataStates;
import com.fogok.dataobjects.transactions.common.BaseTransaction;
import com.fogok.dataobjects.transactions.common.ConnectionInformationTransaction;
import com.fogok.dataobjects.transactions.common.TokenToServiceTransaction;
import com.fogok.spaceships.net.NetRootController;
import com.fogok.spaceships.net.readers.ConInformCallBack;
import com.fogok.spaceships.net.readers.ConInformReader;
import com.fogok.spaceships.net.readers.SocSrvServerStateReader;

import java.util.concurrent.TimeUnit;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public class SocServHandler extends BaseChannelHandler implements ConInformCallBack {

    private ChannelHandlerContext ctx;

    public SocServHandler(NetRootController netRootController) {
        super(netRootController);
        simpleTransactionReader.getTransactionsAndReadersResolver()
                .addToResolve(
                        new ConInformReader(this, netRootController.getAuthCallBack()),
                        new BaseTransaction(ConnectionToServiceType.SERVICE_TO_CLIENT, ServerToClientDataStates.CONNECTION_TO_SERVICE_INFORMATION.ordinal()))
                .addToResolve(
                        new SocSrvServerStateReader(netRootController),
                        new BaseTransaction(ConnectionToServiceType.CLIENT_TO_SERVICE, ClientToServerDataStates.KEEP_ALIVE_TO_SOC_SERV.ordinal()));

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.ctx = ctx;
        simpleTransactionReader.getTransactionExecutor().execute(ctx.channel(),
                new TokenToServiceTransaction(netRootController.getAuthPlayerToken(), RequestTypeInTokenToServiceTrnsn.CHECK_VALID));
    }

    @Override
    public void receiveConInformResponse(Channel channel, int responseCode) {
        if (responseCode == ConnectionInformationTransaction.RESPONSE_CODE_OK) {
            netRootController.getAuthCallBack().successConnectToSocServ();
            startKeepAliveLoop(channel);
        } else {
            netRootController.getAuthCallBack().exceptionConnect(new Throwable("BAD TOKEN"));
            channel.disconnect();
        }
    }

    private void startKeepAliveLoop(Channel channel){
        final TokenToServiceTransaction tokenToClientTransaction = new TokenToServiceTransaction(netRootController.getAuthPlayerToken(), RequestTypeInTokenToServiceTrnsn.KEEP_ALIVE);
        channel.eventLoop().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                simpleTransactionReader.getTransactionExecutor().execute(ctx.channel(), tokenToClientTransaction);
            }
        }, 0, 5000, TimeUnit.MILLISECONDS);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {

    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }
}
