package com.fogok.spaceships.net;

import com.badlogic.gdx.Gdx;
import com.fogok.dataobjects.datastates.ClientState;
import com.fogok.dataobjects.transactions.utils.BaseTransactionReader;
import com.fogok.dataobjects.utils.EveryBodyPool;
import com.fogok.dataobjects.utils.Pool;
import com.fogok.dataobjects.utils.Serialization;
import com.fogok.spaceships.net.controllers.NetAuthController;
import com.fogok.spaceships.net.controllers.NetPvpController;
import com.fogok.spaceships.net.controllers.NetRelayBalancerController;
import com.fogok.spaceships.net.controllers.NetSocServController;

import java.net.DatagramPacket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

public class NetRootController {

    private ClientState clientState;

    private String token;
    private String nickName;

    private NetAuthController netAuthController;
    private NetSocServController netSocServController;
    private NetPvpController netPvpController;
    private NetRelayBalancerController netRelayBalancerController;

    private UICallBacks uiCallBacks = new UICallBacks();

    public NetRootController() {
        setClientState(ClientState.READY_TO_LOGIN);

        netAuthController = new NetAuthController(this);
        netRelayBalancerController = new NetRelayBalancerController(this);
        netSocServController = new NetSocServController(this);
        netPvpController = new NetPvpController(this);
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
            if (baseTransactionReader != null) {
                baseTransactionReader.readByteBufFromChannel(channel, (ByteBuf) msg);
            } else {
                Serialization.getInstance().getInput().setBuffer(((DatagramPacket)msg).getData());
                Serialization.getInstance().getKryo().readObject(Serialization.getInstance().getInput(), EveryBodyPool.class);
            }
        }

        @Override
        public void reset() {
//            msg = null;
//            channel = null;
//            baseTransactionReader = null;
        }
    }
    //endregion

    //region Getters
    public ClientState getClientState() {
        return clientState;
    }


    public UICallBacks getUiCallBacks() {
        return uiCallBacks;
    }

    public UICallBacks.AuthCallBack getAuthCallBack() {
        return uiCallBacks.getAuthCallBack();
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

    public NetPvpController getNetPvpController() {
        return netPvpController;
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
