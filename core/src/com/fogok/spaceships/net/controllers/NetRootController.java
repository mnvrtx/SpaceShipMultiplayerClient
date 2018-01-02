package com.fogok.spaceships.net.controllers;

import com.badlogic.gdx.Gdx;
import com.fogok.dataobjects.ConnectToServiceImpl;
import com.fogok.dataobjects.datastates.ClientState;
import com.fogok.dataobjects.transactions.utils.BaseTransactionExecutor;
import com.fogok.dataobjects.transactions.utils.TransactionHelper;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public class NetRootController {

    private ClientState clientState;

    private String token;
    private String nickName;

    private ChannelHandlerContext currentChannelInformation;

    private NetAuthController netAuthController;
    private NetHallController netHallController;
    private NetSessionController netSessionController;

    private TransactionHelper transactionHelper = new TransactionHelper();

    public NetRootController() {
        ConnectToServiceImpl.getInstance().isThreadOnly = false;
        netAuthController = new NetAuthController(this);
        netHallController = new NetHallController(this);
        netSessionController = new NetSessionController();
    }

    //region PostLogicExecutor
    public void readServerChannel(Channel channel, Object msg, BaseTransactionExecutor baseTransactionExecutor){
        serverReader.channel = channel;
        serverReader.msg = msg;
        serverReader.baseTransactionExecutor = baseTransactionExecutor;
        Gdx.app.postRunnable(serverReader);
    }

    private ReadServerRunnable serverReader = new ReadServerRunnable();

    private static class ReadServerRunnable implements Runnable{

        private Object msg;
        private Channel channel;
        private BaseTransactionExecutor baseTransactionExecutor;

        @Override
        public void run() {
            baseTransactionExecutor.execute(channel, (ByteBuf) msg);
        }
    }
    //endregion

    //region Getters
    public ClientState getClientState() {
        return clientState;
    }

    public NetAuthController getNetAuthController() {
        return netAuthController;
    }

    public NetHallController getNetHallController() {
        return netHallController;
    }

    public NetSessionController getNetSessionController() {
        return netSessionController;
    }

    public String getNickName() {
        return nickName;
    }

    public String getToken() {
        return token;
    }

    //endregion

    //region Setters
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setToken(String token) {
        this.token = token;
    }
    //endregion
}
