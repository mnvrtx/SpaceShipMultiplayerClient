package com.fogok.spaceships.net.relaybalancer;

import com.fogok.dataobjects.datastates.ConnectionToServiceType;
import com.fogok.dataobjects.datastates.RequestTypeInTokenToServiceTrnsn;
import com.fogok.dataobjects.datastates.ServerToClientDataStates;
import com.fogok.dataobjects.transactions.common.BaseTransaction;
import com.fogok.dataobjects.transactions.common.TokenToServiceTransaction;
import com.fogok.spaceships.net.commonhandlers.BaseChannelHandler;
import com.fogok.spaceships.net.commonreaders.ConInformReader;
import com.fogok.spaceships.net.controllers.NetRootController;
import com.fogok.spaceships.net.relaybalancer.readers.SSInformationReader;

import io.netty.channel.ChannelHandlerContext;

public class RelayBalancerHandler extends BaseChannelHandler {

    public RelayBalancerHandler(NetRootController netRootController){
        super(netRootController);
        simpleTransactionReader.getTransactionsAndReadersResolver()
                .addToResolve(new SSInformationReader(netRootController), new BaseTransaction(ConnectionToServiceType.SERVICE_TO_CLIENT, ServerToClientDataStates.SS_INFORMATION.ordinal()))
                .addToResolve(new ConInformReader(netRootController.getAuthCallBack()), new BaseTransaction(ConnectionToServiceType.SERVICE_TO_CLIENT, ServerToClientDataStates.CONNECTION_TO_SERVICE_INFORMATION.ordinal()));
    }

    /**
     * Соединение с реле балансером установлено - отправлем ему токен, чтобы он нам евернул либо
     * отрицательный ответ (ConInformReader), либо инфу по сервисам (SSInformationReader)
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        simpleTransactionReader.getTransactionExecutor().execute(ctx.channel(), new TokenToServiceTransaction(netRootController.getToken(), RequestTypeInTokenToServiceTrnsn.SS_INFORMATION));
    }
}
