package com.fogok.spaceships.net.auth;

import com.fogok.dataobjects.datastates.ConnectionToServiceType;
import com.fogok.dataobjects.datastates.ServerToClientDataStates;
import com.fogok.dataobjects.transactions.authservice.AuthTransaction;
import com.fogok.dataobjects.transactions.common.BaseTransaction;
import com.fogok.dataobjects.transactions.common.ConnectionInformationTransaction;
import com.fogok.spaceships.net.auth.readers.TokenReader;
import com.fogok.spaceships.net.commonhandlers.BaseChannelHandler;
import com.fogok.spaceships.net.commonreaders.ConInformCallBack;
import com.fogok.spaceships.net.commonreaders.ConInformReader;
import com.fogok.spaceships.net.controllers.NetRootController;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public class AuthHandler extends BaseChannelHandler implements ConInformCallBack{

    private String login, passwordEncrypted;

    public AuthHandler(NetRootController netRootController, String login, String passwordEncrypted){
        super(netRootController);
        this.login = login;
        this.passwordEncrypted = passwordEncrypted;
        this.ex = netRootController.getAuthCallBack();
        simpleTransactionReader.getTransactionsAndReadersResolver()
                .addToResolve(
                        new TokenReader(netRootController.getNetAuthController()),
                        new BaseTransaction(ConnectionToServiceType.SERVICE_TO_CLIENT, ServerToClientDataStates.TOKEN.ordinal()))
                .addToResolve(
                        new ConInformReader(this, netRootController.getAuthCallBack()),
                        new BaseTransaction(ConnectionToServiceType.SERVICE_TO_CLIENT, ServerToClientDataStates.CONNECTION_TO_SERVICE_INFORMATION.ordinal()));
    }

    /**
     * Соединение с сервисом авторизации установлено - отправляем транзакцию на получение токена
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        simpleTransactionReader.getTransactionExecutor().execute(ctx.channel(), new AuthTransaction(login, passwordEncrypted));
        startTimeOut(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        channelCompleteAction();
    }

    @Override
    public void receiveResponse(Channel channel, int responseCode) {
        if (responseCode == ConnectionInformationTransaction.RESPONSE_CODE_ERROR) {
            netRootController.getAuthCallBack().exceptionConnect(
                    new Exception("Неправильный логин или пароль"));
            channel.close();
        }
    }
}
