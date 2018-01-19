package com.fogok.spaceships.net.controllers;

import com.badlogic.gdx.Gdx;
import com.fogok.dataobjects.datastates.ClientState;
import com.fogok.dataobjects.transactions.utils.BaseTransactionReader;
import com.fogok.dataobjects.utils.Pool;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

public class NetRootController {

    private ClientState clientState;

    private String token;
    private String nickName;

    private NetAuthController netAuthController;
    private NetSocServController netSocServController;
    private NetSessionController netSessionController;
    private NetRelayBalancerController netRelayBalancerController;

    public NetRootController() {
        setClientState(ClientState.READY_TO_LOGIN);

        netAuthController = new NetAuthController(this);
        netRelayBalancerController = new NetRelayBalancerController(this);
        netSocServController = new NetSocServController(this);
        netSessionController = new NetSessionController();
    }

    //region PostLogicExecutor
    public void readServerChannel(Channel channel, Object msg, BaseTransactionReader baseTransactionReader){
        ReadServerRunnable serverReader = serverReaders.obtain();
        serverReader.channel = channel;
        serverReader.msg = msg;
        serverReader.baseTransactionReader = baseTransactionReader;
        Gdx.app.postRunnable(serverReader);
    }

    private final Pool<ReadServerRunnable> serverReaders = new Pool<ReadServerRunnable>(10) {
        @Override
        protected ReadServerRunnable newObject() {
            return new ReadServerRunnable();
        }
    };

    private static class ReadServerRunnable implements Runnable, Pool.Poolable{

        private Object msg;
        private Channel channel;
        private BaseTransactionReader baseTransactionReader;

        @Override
        public void run() {
            baseTransactionReader.readByteBufFromChannel(channel, (ByteBuf) msg);
        }

        @Override
        public void reset() {
            msg = null;
            channel = null;
            baseTransactionReader = null;
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

    public NetSocServController getNetSocServController() {
        return netSocServController;
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
