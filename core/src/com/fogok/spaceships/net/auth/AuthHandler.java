package com.fogok.spaceships.net.auth;

import com.fogok.dataobjects.datastates.ConnectionToServiceType;
import com.fogok.dataobjects.datastates.ServerToClientDataStates;
import com.fogok.dataobjects.transactions.authservice.AuthTransaction;
import com.fogok.dataobjects.transactions.common.BaseTransaction;
import com.fogok.spaceships.net.auth.readers.TokenReader;
import com.fogok.spaceships.net.commonhandlers.BaseChannelHandler;
import com.fogok.spaceships.net.controllers.NetRootController;

import io.netty.channel.ChannelHandlerContext;

public class AuthHandler extends BaseChannelHandler {

    private String login, passwordEncrypted;

    public AuthHandler(NetRootController netRootController, String login, String passwordEncrypted){
        super(netRootController);
        this.login = login;
        this.passwordEncrypted = passwordEncrypted;
        simpleTransactionReader.getTransactionsAndReadersResolver()
                .addToResolve(new TokenReader(netRootController.getNetAuthController()), new BaseTransaction(ConnectionToServiceType.SERVICE_TO_CLIENT, ServerToClientDataStates.TOKEN.ordinal()));
    }

    /**
     * Соединение с сервисом авторизации установлено - отправляем транзакцию на получение токена
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        simpleTransactionReader.getTransactionExecutor().execute(ctx.channel(), new AuthTransaction(login, passwordEncrypted));
    }

}
