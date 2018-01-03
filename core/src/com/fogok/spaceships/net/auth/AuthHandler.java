package com.fogok.spaceships.net.auth;

import com.fogok.dataobjects.datastates.ConnectionToServiceType;
import com.fogok.dataobjects.datastates.ServerToClientDataStates;
import com.fogok.dataobjects.transactions.common.BaseTransaction;
import com.fogok.dataobjects.transactions.authservice.AuthTransaction;
import com.fogok.dataobjects.transactions.utils.SimpleTransactionReader;
import com.fogok.spaceships.net.auth.readers.TokenReader;
import com.fogok.spaceships.net.controllers.NetRootController;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class AuthHandler extends ChannelInboundHandlerAdapter {

    private SimpleTransactionReader simpleTransactionReader = new SimpleTransactionReader();
    private String login, passwordEncrypted;
    private NetRootController netRootController;

    public AuthHandler(NetRootController netRootController, String login, String passwordEncrypted){
        this.netRootController = netRootController;
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

    /**
     * Получили ответ от сервиса авторизации - просто читаем ето все с нашим эксекьютером, он сам знает какой экшн вызвать, т.к. мы сказали это в конструкторе
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        netRootController.readServerChannel(ctx.channel(), msg, simpleTransactionReader);
    }
}
