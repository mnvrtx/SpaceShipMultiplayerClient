package com.fogok.spaceships.net.handlers;

import com.fogok.dataobjects.datastates.ConnectionToServiceType;
import com.fogok.dataobjects.datastates.ServerToClientDataStates;
import com.fogok.dataobjects.transactions.authservice.AuthTransaction;
import com.fogok.dataobjects.transactions.common.BaseTransaction;
import com.fogok.dataobjects.transactions.common.ConnectionInformationTransaction;
import com.fogok.spaceships.net.readers.TokenReader;
import com.fogok.spaceships.net.readers.ConInformCallBack;
import com.fogok.spaceships.net.readers.ConInformReader;
import com.fogok.spaceships.net.NetRootController;
import com.fogok.spaceships.view.screens.login.LoginScreen;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public class AuthHandler extends BaseChannelHandler implements ConInformCallBack{

    private String login, passwordEncrypted;
    private boolean isRegistration;

    public AuthHandler(NetRootController netRootController, String login, String passwordEncrypted, boolean isRegistration){
        super(netRootController);
        this.login = login;
        this.passwordEncrypted = passwordEncrypted;
        this.isRegistration = isRegistration;
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
        super.channelActive(ctx);
        simpleTransactionReader.getTransactionExecutor().execute(ctx.channel(), new AuthTransaction(login, passwordEncrypted, isRegistration));
        startTimeOut(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        channelCompleteAction();
    }

    @Override
    public void receiveConInformResponse(Channel channel, int responseCode) {
        if (responseCode == ConnectionInformationTransaction.RESPONSE_CODE_ERROR) {
            netRootController.getAuthCallBack().exceptionConnect(
                    LoginScreen.isRegistrationAction ? new Exception("Аккаунт с таким email зарегистрирован") : new Exception("Неправильный логин или пароль"));
            channel.close();
        }
    }
}
