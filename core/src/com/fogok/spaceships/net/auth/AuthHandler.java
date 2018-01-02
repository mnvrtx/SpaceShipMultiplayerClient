package com.fogok.spaceships.net.auth;

import com.fogok.dataobjects.datastates.ConnectionToServiceType;
import com.fogok.dataobjects.datastates.ServerToClientDataStates;
import com.fogok.dataobjects.transactions.BaseTransaction;
import com.fogok.dataobjects.transactions.clientserver.AuthTransaction;
import com.fogok.dataobjects.transactions.utils.SimpleTransactionExecutor;
import com.fogok.spaceships.net.auth.actions.TokenAction;
import com.fogok.spaceships.net.controllers.NetRootController;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class AuthHandler extends ChannelInboundHandlerAdapter {

    private SimpleTransactionExecutor simpleTransactionExecutor = new SimpleTransactionExecutor();
    private String login, passwordEncrypted;
    private NetRootController netRootController;

    public AuthHandler(NetRootController netRootController, String login, String passwordEncrypted){
        this.netRootController = netRootController;
        this.login = login;
        this.passwordEncrypted = passwordEncrypted;
        simpleTransactionExecutor.getTransactionsAndActionsResolver()
                .addToResolve(new TokenAction(netRootController), new BaseTransaction(ConnectionToServiceType.ServiceToClient, ServerToClientDataStates.TOKEN.ordinal()));
    }

    /**
     * Соединение с сервисом авторизации установлено - отправляем транзакцию на получение токена
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        simpleTransactionExecutor.getTransactionHelper().executeTransaction(ctx.channel(), new AuthTransaction(login, passwordEncrypted));
    }

    /**
     * Получили ответ от сервиса авторизации - просто читаем ето все с нашим эксекьютером, он сам знает какой экшн вызвать, т.к. мы сказали это в конструкторе
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        netRootController.readServerChannel(ctx.channel(), msg, simpleTransactionExecutor);
    }
}
