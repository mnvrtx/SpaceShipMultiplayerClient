package com.fogok.spaceships.net.handlers;

import com.esotericsoftware.kryo.io.ByteBufferInput;
import com.esotericsoftware.kryo.io.ByteBufferOutput;
import com.fogok.dataobjects.ConnectToServiceImpl;
import com.fogok.dataobjects.transactions.pvp.PvpTransactionHeaderType;
import com.fogok.dataobjects.utils.Serialization;
import com.fogok.spaceships.net.NetRootController;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;

import static com.esotericsoftware.minlog.Log.info;

public class PvpHandler {

    private final static long TIMEITERSSLEEP = 16;
    private final static int TRY_COUNT = 30;

    private boolean isConnected;
    private NetRootController netRootController;
    private DatagramSocket datagramSocket;
    final ByteBuffer writeBuffer;
    private final ByteBufferInput input = new ByteBufferInput();
    private final ByteBufferOutput output = new ByteBufferOutput();

    DatagramPacket datagramSend = new DatagramPacket(new byte[0], 0);
    DatagramPacket datagramReceive = new DatagramPacket(new byte[0], 0);

    private InetSocketAddress inetSocketAddress;

    public PvpHandler(NetRootController netRootController, InetSocketAddress inetSocketAddress){
        this.netRootController = netRootController;
        this.inetSocketAddress = inetSocketAddress;

        datagramSend.setSocketAddress(inetSocketAddress);

//        readBuffer = ByteBuffer.allocate(ConnectToServiceImpl.BUFFER_SIZE);
        writeBuffer = ByteBuffer.allocate(ConnectToServiceImpl.BUFFER_SIZE);
//        readBuffer.clear();
        writeBuffer.clear();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sendStartData(TRY_COUNT);
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
     * Send start data.
     * @param tryCount
     */
    private void sendStartData(int tryCount) throws SocketException {
        //init
        datagramSocket = new DatagramSocket(61233);
        datagramSocket.setSoTimeout(50);
        datagramSocket.setReuseAddress(true);

        for (int i = 0; i < tryCount; i++) {
            try {
                trySendStartData();
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void trySendStartData() throws IOException {
        info("Try to send start data");

        //prepare to serialize
        writeBuffer.clear();
        output.setBuffer(writeBuffer);

        //serialize
        //header
        output.writeInt(PvpTransactionHeaderType.START_DATA.ordinal(), true);
        //content
        output.writeString(netRootController.getNetPvpController().getSessionId());
        output.writeString(netRootController.getAuthPlayerToken());

        //commit
        output.flush();

        //send
        writeBuffer.flip();
        datagramSend.setData(writeBuffer.array());
        datagramSocket.send(datagramSend);

        //receive
        datagramReceive.setData(new byte[ConnectToServiceImpl.BUFFER_SIZE]);
        datagramSocket.receive(datagramReceive);

        //deserialize
        input.setBuffer(datagramReceive.getData());
        netRootController.readUdpResponse(this, input);
    }

    private volatile boolean stop = false;
    private int countRetry = 0;
    public void startLoopPingPong(){
        info(String.format("Started loop pingpong to %s service: ", inetSocketAddress.getAddress()));
        final PvpHandler pvpHandler = this;
        new Thread(new Runnable() {
            @Override
            public void run() {
                isConnected = true;
                while (!stop) {
                    if (!netRootController.blockReader) {
                        try {
                            if (countRetry == 0) {
                                //prepare to serialize
                                writeBuffer.clear();
                                output.setBuffer(writeBuffer);

                                //serialize
                                //header
                                output.writeInt(PvpTransactionHeaderType.CONSOLE_STATE.ordinal(), true);
                                //content
                                Serialization.instance.getKryo().writeObject(output, Serialization.instance.getPlayerData());

                                //commit
                                output.flush();

                                //send prepare
                                writeBuffer.flip();
                                datagramSend.setData(writeBuffer.array());
                            }

                            //send
                            datagramSocket.send(datagramSend);

                            //maybe receive
                            datagramReceive.setData(new byte[ConnectToServiceImpl.BUFFER_SIZE]);
                            datagramSocket.receive(datagramReceive);

                            //deserialize received
                            input.setBuffer(datagramReceive.getData());
                            netRootController.readUdpResponse(pvpHandler, input);
                            countRetry = 0;
                        } catch (IOException e) {
                            //not received... return to start and increment countRetry
                            countRetry++;
                            if (countRetry > TRY_COUNT) {
                                stop = true;
                                info("stop = true");
                            }
                        }
                    }
                }
                isConnected = false;
            }
        }).start();
    }

    public void stop() {
        stop = true;
    }

    public boolean isConnected() {
        return isConnected;
    }
}
