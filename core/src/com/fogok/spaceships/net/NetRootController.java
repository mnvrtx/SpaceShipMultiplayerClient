package com.fogok.spaceships.net;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryo.io.ByteBufferInput;
import com.fogok.dataobjects.datastates.ClientState;
import com.fogok.dataobjects.transactions.pvp.PvpTransactionHeaderType;
import com.fogok.dataobjects.transactions.utils.BaseTransactionReader;
import com.fogok.dataobjects.utils.EveryBodyPool;
import com.fogok.dataobjects.utils.Serialization;
import com.fogok.spaceships.net.controllers.NetAuthController;
import com.fogok.spaceships.net.controllers.NetPvpController;
import com.fogok.spaceships.net.controllers.NetRelayBalancerController;
import com.fogok.spaceships.net.controllers.NetSocServController;
import com.fogok.spaceships.net.handlers.PvpHandler;

import io.netty.channel.Channel;

import static com.esotericsoftware.minlog.Log.info;
import static com.esotericsoftware.minlog.Log.warn;

public class NetRootController {
    public volatile boolean blockReader;

    private ClientState clientState;

    private String token;
    private String nickName;

    private NetAuthController netAuthController;
    private NetSocServController netSocServController;
    private NetPvpController netPvpController;
    private NetRelayBalancerController netRelayBalancerController;

    private UICallBacks uiCallBacks = new UICallBacks();

    public NetRootController(){
        setClientState(ClientState.READY_TO_LOGIN);

        netAuthController = new NetAuthController(this);
        netRelayBalancerController = new NetRelayBalancerController(this);
        netSocServController = new NetSocServController(this);
        netPvpController = new NetPvpController(this);
    }


    //region PostLogicExecutor
    public void readServerChannel(Channel channel, byte[] bytes, BaseTransactionReader baseTransactionReader){
        if (!blockReader) {
            blockReader = true;

            //set required data
            readServerRunnable.context = this;
            readServerRunnable.channel = channel;
            readServerRunnable.bytes = bytes;
            readServerRunnable.baseTransactionReader = baseTransactionReader;

            //invoke after draw
            Gdx.app.postRunnable(readServerRunnable);
        }else
            warn("Request to read during another read");
    }

    public void readUdpResponse(PvpHandler pvpHandler, ByteBufferInput input) {
        if (!blockReader) {
            blockReader = true;

            //set required data
            readUdpRunnable.context = this;
            readUdpRunnable.pvpHandler = pvpHandler;
            readUdpRunnable.input = input;

            //invoke after draw
            Gdx.app.postRunnable(readUdpRunnable);
        }else
            warn("Request to read during another read");
    }

    private ReadUdpRunnable readUdpRunnable = new ReadUdpRunnable();
    private static class ReadUdpRunnable implements Runnable {
        private NetRootController context;

        private PvpHandler pvpHandler;
        private ByteBufferInput input;

        @Override
        public void run() {
            switch (PvpTransactionHeaderType.values()[input.readInt(true)]) {
                case START_DATA:
                    if (input.readBoolean()) {
                        info("Connected success");
                        pvpHandler.startLoopPingPong();
                    }else
                        info("Already connected");
                    break;
                case EVERYBODY_POOL:
//                    info("" + Arrays.toString(input.getBuffer()));
                    Serialization.instance.getKryo().readObject(input, EveryBodyPool.class);
                    break;
            }
            context.blockReader = false;
        }
    }


    private ReadServerRunnable readServerRunnable = new ReadServerRunnable();

    private static class ReadServerRunnable implements Runnable{
        private NetRootController context;

        private byte[] bytes;
        private Channel channel;
        private BaseTransactionReader baseTransactionReader;

        @Override
        public void run() {
            baseTransactionReader.readByteBufFromChannel(channel, bytes);
            context.blockReader = false;
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

    public String getAuthPlayerToken() {
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
