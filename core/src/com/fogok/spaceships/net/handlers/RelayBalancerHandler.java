package com.fogok.spaceships.net.handlers;

import com.fogok.dataobjects.datastates.ConnectionToServiceType;
import com.fogok.dataobjects.datastates.RequestTypeInTokenToServiceTrnsn;
import com.fogok.dataobjects.datastates.ServerToClientDataStates;
import com.fogok.dataobjects.transactions.common.BaseTransaction;
import com.fogok.dataobjects.transactions.common.TokenToServiceTransaction;
import com.fogok.spaceships.net.readers.ConInformCallBack;
import com.fogok.spaceships.net.readers.ConInformReader;
import com.fogok.spaceships.net.NetRootController;
import com.fogok.spaceships.net.readers.SSInformationReader;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public class RelayBalancerHandler extends BaseChannelHandler implements ConInformCallBack{

    public RelayBalancerHandler(NetRootController netRootController){
        super(netRootController);
        this.ex = netRootController.getAuthCallBack();
        simpleTransactionReader.getTransactionsAndReadersResolver()
                .addToResolve(new SSInformationReader(netRootController, this), new BaseTransaction(ConnectionToServiceType.SERVICE_TO_CLIENT, ServerToClientDataStates.SS_INFORMATION.ordinal()))
                .addToResolve(new ConInformReader(netRootController.getAuthCallBack()), new BaseTransaction(ConnectionToServiceType.SERVICE_TO_CLIENT, ServerToClientDataStates.CONNECTION_TO_SERVICE_INFORMATION.ordinal()));
    }

    /**
     * Соединение с реле балансером установлено - отправлем ему токен, чтобы он нам евернул либо
     * отрицательный ответ (ConInformReader), либо инфу по сервисам (SSInformationReader)
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        simpleTransactionReader.getTransactionExecutor().execute(ctx.channel(), new TokenToServiceTransaction(netRootController.getToken(), RequestTypeInTokenToServiceTrnsn.SS_INFORMATION));
    }


    @Override
    public void receiveConInformResponse(Channel channel, int responseCode) {
        channelCompleteAction();
    }
}
