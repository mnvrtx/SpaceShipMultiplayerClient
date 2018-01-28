package com.fogok.spaceships.net.handlers;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryo.io.Output;
import com.fogok.dataobjects.transactions.pvp.PvpTransactionHeaderType;
import com.fogok.dataobjects.utils.Serialization;
import com.fogok.spaceships.net.NetRootController;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramPacket;

import static com.esotericsoftware.minlog.Log.info;

public class PvpHandler extends SimpleChannelInboundHandler<DatagramPacket>{

//    private final static float TIMEITERSSLEEP = 0.016f;  //in seconds
    private final static float TIMEITERSSLEEP = 2f;  //in seconds

//    private SimpleTransactionReader transactionReader;
    private boolean isConnected;
    private NetRootController netRootController;
    private DatagramChannel datagramChannel;
    private String ip;

    private ByteBuf byteBuf = Unpooled.directBuffer();

    public PvpHandler(NetRootController netRootController, String ip) throws UnknownHostException, SocketException {
        this.netRootController = netRootController;
        this.ip = ip;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        info(String.format("Startup udp client service '%s' success with '%s' to '%s",
                this.getClass().getSimpleName(), ctx.channel().localAddress(), ctx.channel().remoteAddress()));

        datagramChannel = (DatagramChannel) ctx.channel();

        sendStartData();
    }

    private void sendStartData(){
        Output output = Serialization.getInstance().getCleanedOutput();
        //header
        output.writeInt(PvpTransactionHeaderType.START_DATA.ordinal(), true);
        //content
        output.writeString(netRootController.getNetPvpController().getSessionId());
        output.writeString(netRootController.getAuthPlayerToken());

        //send
        byteBuf.writeBytes(output.getBuffer());
        datagramChannel.writeAndFlush(new DatagramPacket(byteBuf, datagramChannel.remoteAddress()));
        info("Start data sended! ");
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket datagramPacket) throws Exception {
        info("Read channel information from: " + datagramChannel.remoteAddress());
        netRootController.readServerChannel(null, datagramPacket.content().retain(), null, this);
    }

    public void startLoopPingPong(){
        info(String.format("Started loop pingpong to %s service: ", datagramChannel.remoteAddress()));
        isConnected = true;
        int sleepTimeMilliSeconds = (int) (TIMEITERSSLEEP * 1000);
        netRootController.getNetPvpController().getWorkerGroup().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
//                        Output output = Serialization.getInstance().getCleanedOutput();
//
//                        output.writeInt(PvpTransactionHeaderType.CONSOLE_STATE.ordinal(), true);
//                        Serialization.getInstance().getKryo().writeObject(output, PlayerData.class);
//                        datagramPacket.setData(output.getBuffer());
//                        try {
//                            datagramSocket.send(datagramPacket);
//                            info("Send playerData " + Serialization.getInstance().getPlayerData());
//                        } catch (IOException e) {
//                            error("Error in datagram send action:\n" + e);
//                        }
                    }
                });
            }
        }, sleepTimeMilliSeconds, sleepTimeMilliSeconds, TimeUnit.MILLISECONDS);
    }

    public boolean isConnected() {
        return isConnected;
    }
}
