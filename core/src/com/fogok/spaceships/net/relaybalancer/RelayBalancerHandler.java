package com.fogok.spaceships.net.relaybalancer;

import com.fogok.dataobjects.datastates.ConnectionToServiceType;
import com.fogok.dataobjects.datastates.RequestTypeInTokenToServiceTrnsn;
import com.fogok.dataobjects.datastates.ServerToClientDataStates;
import com.fogok.dataobjects.transactions.common.BaseTransaction;
import com.fogok.dataobjects.transactions.common.TokenToServiceTransaction;
import com.fogok.dataobjects.transactions.utils.SimpleTransactionReader;
import com.fogok.spaceships.net.commonreaders.ConInformReader;
import com.fogok.spaceships.net.controllers.NetAuthController;
import com.fogok.spaceships.net.controllers.NetRootController;
import com.fogok.spaceships.net.relaybalancer.readers.SSInformationReader;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class RelayBalancerHandler extends ChannelInboundHandlerAdapter {

    private SimpleTransactionReader simpleTransactionReader = new SimpleTransactionReader();
    private String token;
    private NetRootController netRootController;

    public RelayBalancerHandler(NetRootController netRootController, String token, NetAuthController.AuthCallBack authCallBack){
        this.token = token;
        this.netRootController = netRootController;
        simpleTransactionReader.getTransactionsAndReadersResolver()
                .addToResolve(new SSInformationReader(netRootController.getNetRelayBalancerController()), new BaseTransaction(ConnectionToServiceType.SERVICE_TO_CLIENT, ServerToClientDataStates.SS_INFORMATION.ordinal()))
                .addToResolve(new ConInformReader(authCallBack), new BaseTransaction(ConnectionToServiceType.SERVICE_TO_CLIENT, ServerToClientDataStates.CONNECTION_TO_SERVICE_INFORMATION.ordinal()));

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        simpleTransactionReader.getTransactionExecutor().execute(ctx.channel(), new TokenToServiceTransaction(token, RequestTypeInTokenToServiceTrnsn.SS_INFORMATION));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        netRootController.readServerChannel(ctx.channel(), msg, simpleTransactionReader);
    }
}
