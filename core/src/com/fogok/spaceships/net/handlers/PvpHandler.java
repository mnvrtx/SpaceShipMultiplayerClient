package com.fogok.spaceships.net.handlers;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryo.io.Output;
import com.fogok.dataobjects.PlayerData;
import com.fogok.dataobjects.transactions.pvp.PvpTransactionHeaderType;
import com.fogok.dataobjects.utils.Serialization;
import com.fogok.spaceships.net.NetRootController;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static com.esotericsoftware.minlog.Log.error;
import static com.esotericsoftware.minlog.Log.info;

public class PvpHandler extends SimpleChannelInboundHandler<io.netty.channel.socket.DatagramPacket>{

//    private final static float TIMEITERSSLEEP = 0.016f;  //in seconds
    private final static float TIMEITERSSLEEP = 2f;  //in seconds

//    private SimpleTransactionReader transactionReader;
    private boolean isConnected;
    private NetRootController netRootController;
    private DatagramSocket datagramSocket = new DatagramSocket();
    private DatagramPacket datagramPacket;
    private String ip;

    public PvpHandler(NetRootController netRootController, String ip) throws UnknownHostException, SocketException {
        this.netRootController = netRootController;
        this.ip = ip;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        datagramPacket = new DatagramPacket(new byte[0], 0);
        datagramPacket.setAddress(InetAddress.getByName(ip.split(":")[0]));
        datagramPacket.setPort(Integer.parseInt(ip.split(":")[1]));
        sendStartData();
    }

    private void sendStartData(){
        Output output = Serialization.getInstance().getOutput();
        output.clear();
        //header
        output.writeInt(PvpTransactionHeaderType.START_DATA.ordinal(), true);
        //content
        output.writeString(netRootController.getNetPvpController().getSessionId());
        output.writeString(netRootController.getAuthPlayerToken());
        datagramPacket.setData(output.getBuffer());
        try {
            datagramSocket.send(datagramPacket);
            info("Start data sended!");
        } catch (IOException e) {
            error("Error in datagram send action:\n" + e);
        }
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, io.netty.channel.socket.DatagramPacket msg) throws Exception {
        info("Read channel information from: " + ctx.channel().remoteAddress());
        netRootController.readServerChannel(null, msg, null, this);
    }

    public void startLoopPingPong(){
        isConnected = true;
        int sleepTimeMilliSeconds = (int) (TIMEITERSSLEEP * 1000);
        netRootController.getNetPvpController().getWorkerGroup().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        Output output = Serialization.getInstance().getOutput();
                        output.clear();

                        output.writeInt(PvpTransactionHeaderType.CONSOLE_STATE.ordinal(), true);
                        Serialization.getInstance().getKryo().writeObject(output, PlayerData.class);
                        datagramPacket.setData(output.getBuffer());
                        try {
                            datagramSocket.send(datagramPacket);
                            info("Send playerData " + Serialization.getInstance().getPlayerData());
                        } catch (IOException e) {
                            error("Error in datagram send action:\n" + e);
                        }
                    }
                });
            }
        }, sleepTimeMilliSeconds, sleepTimeMilliSeconds, TimeUnit.MILLISECONDS);
    }

    public boolean isConnected() {
        return isConnected;
    }
}
