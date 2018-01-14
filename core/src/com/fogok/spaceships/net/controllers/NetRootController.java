package com.fogok.spaceships.net.controllers;

import com.badlogic.gdx.Gdx;
import com.fogok.dataobjects.datastates.ClientState;
import com.fogok.dataobjects.transactions.utils.BaseTransactionReader;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

public class NetRootController {

    private ClientState clientState;

    private String token;
    private String nickName;

    private NetAuthController netAuthController;
    private NetHallController netHallController;
    private NetSessionController netSessionController;
    private NetRelayBalancerController netRelayBalancerController;

    public NetRootController() {
        setClientState(ClientState.READY_TO_LOGIN);

        netAuthController = new NetAuthController(this);
        netRelayBalancerController = new NetRelayBalancerController(this);
        netHallController = new NetHallController(this);
        netSessionController = new NetSessionController();
    }

    //region PostLogicExecutor
    public void readServerChannel(Channel channel, Object msg, BaseTransactionReader baseTransactionReader){
        serverReader.channel = channel;
        serverReader.msg = msg;
        serverReader.baseTransactionReader = baseTransactionReader;
        Gdx.app.postRunnable(serverReader);
    }

    private ReadServerRunnable serverReader = new ReadServerRunnable();

    private static class ReadServerRunnable implements Runnable{

        private Object msg;
        private Channel channel;
        private BaseTransactionReader baseTransactionReader;

        @Override
        public void run() {
            baseTransactionReader.readByteBufFromChannel(channel, (ByteBuf) msg);
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

    public NetRelayBalancerController getNetRelayBalancerController() {
        return netRelayBalancerController;
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

    public void setClientState(ClientState clientState) {
        this.clientState = clientState;
    }

    //endregion
}
