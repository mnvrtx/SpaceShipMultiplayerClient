package com.fogok.spaceships.net;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryo.io.Input;
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

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import static com.esotericsoftware.minlog.Log.info;
import static com.esotericsoftware.minlog.Log.warn;

public class NetRootController {
    private boolean blockReader;

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
    public void readServerChannel(Channel channel, Object msg, BaseTransactionReader baseTransactionReader, PvpHandler pvpHandler){
        if (!blockReader) {
            blockReader = true;
            readServerRunnable.context = this;
            readServerRunnable.channel = channel;
            readServerRunnable.msg = msg;
            readServerRunnable.baseTransactionReader = baseTransactionReader;
            readServerRunnable.pvpHandler = pvpHandler;
            Gdx.app.postRunnable(readServerRunnable);
        }else
            warn("Request to read during another read");
    }

//    private final Pool<ReadServerRunnable> serverReaders = new Pool<ReadServerRunnable>(10) {
//        @Override
//        protected ReadServerRunnable newObject() {
//            return new ReadServerRunnable();
//        }
//    };

    private ReadServerRunnable readServerRunnable = new ReadServerRunnable();

    private static class ReadServerRunnable implements Runnable/*, Pool.Poolable */{

        private NetRootController context;
        private Object msg;
        private Channel channel;
        private BaseTransactionReader baseTransactionReader;
        private PvpHandler pvpHandler;

        @Override
        public void run() {
            //warning - maybe long read object
            if (pvpHandler == null) {
                baseTransactionReader.readByteBufFromChannel(channel, (ByteBuf) msg);
            } else {
                Input input = Serialization.instance.getInput();
                byte[] response = new byte[((ByteBuf) msg).readableBytes()];
                ((ByteBuf) msg).readBytes(response);
                input.setBuffer(response);
                switch (PvpTransactionHeaderType.values()[input.readInt(true)]) {
                    case START_DATA:
                        info(String.format("Started data received success"));
                        pvpHandler.startLoopPingPong();
                        break;
                    case EVERYBODY_POOL:
                        Serialization.instance.getKryo().readObject(input, EveryBodyPool.class);
                        info("everyBodyPool - "  + Serialization.instance.getEveryBodyPool().toString(true));
                        break;
                }
            }
            context.blockReader = false;
        }

//        @Override //many reads in one iteration
//        public void reset() {
//            msg = null;
//            channel = null;
//            baseTransactionReader = null;
//        }

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
