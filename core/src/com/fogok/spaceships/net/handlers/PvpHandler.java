package com.fogok.spaceships.net.handlers;

import com.esotericsoftware.kryo.io.Output;
import com.fogok.dataobjects.ConnectToServiceImpl;
import com.fogok.dataobjects.transactions.pvp.PvpTransactionHeaderType;
import com.fogok.dataobjects.utils.Serialization;
import com.fogok.spaceships.net.NetRootController;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.concurrent.TimeUnit;

import static com.esotericsoftware.minlog.Log.info;

public class PvpHandler {

    private final static long TIMEITERSSLEEP = 25;

    private boolean isConnected;
    private NetRootController netRootController;
    private DatagramSocket datagramSocket;
    private InetSocketAddress inetSocketAddress;

    private DatagramPacket datagramPacketToSend;
    private DatagramPacket datagramPacketToReceive;

    public PvpHandler(NetRootController netRootController, InetSocketAddress inetSocketAddress){
        this.netRootController = netRootController;
        this.inetSocketAddress = inetSocketAddress;
        datagramPacketToSend = new DatagramPacket(new byte[0], 0, inetSocketAddress);
        datagramPacketToReceive = new DatagramPacket(new byte[0], 0);
        netRootController.getNetPvpController().getWorkerGroup().schedule(new Runnable() {
            @Override
            public void run() {
                sendStartData();
            }
        }, 0, TimeUnit.MILLISECONDS);
    }



    private void sendStartData(){
        try {
            info("Try to send start data");
            Output output = Serialization.instance.getCleanedOutput();
            //header
            output.writeInt(PvpTransactionHeaderType.START_DATA.ordinal(), true);
            //content
            output.writeString(netRootController.getNetPvpController().getSessionId());
            output.writeString(netRootController.getAuthPlayerToken());

            //init
            datagramSocket = new DatagramSocket(63123);

            //send
            datagramPacketToSend.setData(output.getBuffer());
            datagramSocket.send(datagramPacketToSend);

            //receive
            datagramPacketToReceive.setData(new byte[ConnectToServiceImpl.RECEIVE_BUFFER_SIZE]);
            datagramSocket.setSoTimeout(ConnectToServiceImpl.TIMEOUT);
            datagramSocket.receive(datagramPacketToReceive);
            netRootController.readServerChannel(null, datagramPacketToReceive.getData(), null, this);
        } catch (SocketException e) {
            e.printStackTrace();
            datagramSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            datagramSocket.close();
        }

    }


//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket datagramPacket) throws Exception {
//        datagramChannel = (DatagramChannel) ctx.channel();
//
//        ByteBuf byteBuf = datagramPacket.content();
//        byte[] response = new byte[byteBuf.readableBytes()];
//        byteBuf.readBytes(response);
//        netRootController.readServerChannel(null, response, null, this);
//        byteBuf.release();

//    }

    private boolean stop = false;
    public void startLoopPingPong(){
        info(String.format("Started loop pingpong to %s service: ", datagramPacketToReceive.getAddress()));
        final PvpHandler pvpHandler = this;
        new Thread(new Runnable() {
            @Override
            public void run() {
                isConnected = true;
                while (!stop) {
                    if (!netRootController.blockReader) {
                        try {
                            //write data
                            Serialization.instance.getCleanedOutput().writeInt(PvpTransactionHeaderType.CONSOLE_STATE.ordinal(), true);
                            Serialization.instance.getKryo().writeObject(Serialization.instance.getOutput(), Serialization.instance.getPlayerData());

                            //send
                            datagramPacketToSend.setData(Serialization.instance.getOutput().getBuffer());
                            datagramSocket.send(datagramPacketToSend);
//                        info("Sent - " + Arrays.toString(datagramPacketToSend.getData()));

                            //receive
                            datagramSocket.setSoTimeout(ConnectToServiceImpl.TIMEOUT);
                            datagramSocket.receive(datagramPacketToReceive);
                            netRootController.readServerChannel(null, datagramPacketToReceive.getData(), null, pvpHandler);
//                        info("Received - " + Arrays.toString(datagramPacketToReceive.getData()));
                        } catch (SocketException e) {
                            e.printStackTrace();
                            datagramSocket.close();
                            netRootController.getNetPvpController().getWorkerGroup().shutdownGracefully();
                            stop = true;
                        } catch (IOException e) {
                            e.printStackTrace();
                            datagramSocket.close();
                            netRootController.getNetPvpController().getWorkerGroup().shutdownGracefully();
                            stop = true;
                        }
                    }
                    try {
                        Thread.sleep(TIMEITERSSLEEP);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        isConnected = false;
                    }
                }
                isConnected = false;
            }
        }).start();
    }

    public boolean isConnected() {
        return isConnected;
    }
}
