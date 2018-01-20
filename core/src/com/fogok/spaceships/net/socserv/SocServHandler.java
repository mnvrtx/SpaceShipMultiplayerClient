package com.fogok.spaceships.net.socserv;

import com.fogok.dataobjects.datastates.ConnectionToServiceType;
import com.fogok.dataobjects.datastates.RequestTypeInTokenToServiceTrnsn;
import com.fogok.dataobjects.datastates.ServerToClientDataStates;
import com.fogok.dataobjects.transactions.common.BaseTransaction;
import com.fogok.dataobjects.transactions.common.ConnectionInformationTransaction;
import com.fogok.dataobjects.transactions.common.TokenToServiceTransaction;
import com.fogok.spaceships.net.commonhandlers.BaseChannelHandler;
import com.fogok.spaceships.net.commonreaders.ConInformCallBack;
import com.fogok.spaceships.net.commonreaders.ConInformReader;
import com.fogok.spaceships.net.controllers.NetRootController;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public class SocServHandler extends BaseChannelHandler implements ConInformCallBack {

    public SocServHandler(NetRootController netRootController) {
        super(netRootController);
        simpleTransactionReader.getTransactionsAndReadersResolver()
                .addToResolve(
                        new ConInformReader(this, netRootController.getAuthCallBack()),
                        new BaseTransaction(ConnectionToServiceType.SERVICE_TO_CLIENT, ServerToClientDataStates.CONNECTION_TO_SERVICE_INFORMATION.ordinal()));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        simpleTransactionReader.getTransactionExecutor().execute(ctx.channel(),
                new TokenToServiceTransaction(netRootController.getToken(), RequestTypeInTokenToServiceTrnsn.CHECK_VALID));
    }

    @Override
    public void receiveResponse(Channel channel, int responseCode) {
        if (responseCode == ConnectionInformationTransaction.RESPONSE_CODE_OK)
            netRootController.getAuthCallBack().successConnectToSocServ();
    }
}
