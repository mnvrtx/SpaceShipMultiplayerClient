package com.fogok.spaceships.net.handlers;

import com.esotericsoftware.kryo.io.ByteBufferInput;
import com.esotericsoftware.kryo.io.ByteBufferOutput;
import com.fogok.dataobjects.ConnectToServiceImpl;
import com.fogok.dataobjects.transactions.pvp.PvpTransactionHeaderType;
import com.fogok.dataobjects.utils.Serialization;
import com.fogok.spaceships.net.NetRootController;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import static com.esotericsoftware.minlog.Log.info;

public class PvpHandler {

    private final static long TIMEITERSSLEEP = 16;
    private final static int TRY_COUNT = 30;
    private final static int TIMEOUT = 60;

    private boolean isConnected;
    private NetRootController netRootController;
    private DatagramChannel datagramChannel;
    final ByteBuffer writeBuffer, readBuffer;
    private final ByteBufferInput input = new ByteBufferInput();
    private final ByteBufferOutput output = new ByteBufferOutput();

//    DatagramPacket datagramSend = new DatagramPacket(new byte[0], 0);
//    DatagramPacket datagramReceive = new DatagramPacket(new byte[0], 0);

    private InetSocketAddress inetSocketAddress;

    public PvpHandler(NetRootController netRootController, InetSocketAddress inetSocketAddress){
        this.netRootController = netRootController;
        this.inetSocketAddress = inetSocketAddress;

//        datagramSend.setSocketAddress(inetSocketAddress);

        readBuffer = ByteBuffer.allocate(ConnectToServiceImpl.BUFFER_SIZE);
        writeBuffer = ByteBuffer.allocateDirect(ConnectToServiceImpl.BUFFER_SIZE);
        readBuffer.clear();
        writeBuffer.clear();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sendStartData(TRY_COUNT);
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
     * Send start data.
     * @param tryCount
     */
    private void sendStartData(int tryCount) throws IOException {
        //init
        datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
//        datagramChannel.bind(null);
        datagramChannel.socket().bind(new InetSocketAddress(61233));
//        datagramChannel.socket().setReuseAddress(true);
        datagramChannel.connect(inetSocketAddress);

        for (int i = 0; i < tryCount; i++) {
            try {
                if (trySendStartData())
                    break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean trySendStartData() throws IOException {
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
        datagramChannel.send(writeBuffer, inetSocketAddress);

        //receive
        long start = System.currentTimeMillis() + 5000;
        readBuffer.clear();
        while (System.currentTimeMillis() < start && readBuffer.position() == 0)
            if (!datagramChannel.isConnected())
                datagramChannel.receive(readBuffer); // always null on Android >= 5.0}
            else
                datagramChannel.read(readBuffer);

        if (readBuffer.position() == 0)
            return false;


        //deserialize
        readBuffer.flip();
        input.setBuffer(readBuffer);
        netRootController.readUdpResponse(this, input);
        return true;
    }

    private volatile boolean stop = false;
    public static volatile long ping;
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

                            //send
                            datagramChannel.send(writeBuffer, inetSocketAddress);

                            //maybe receive
                            long start = System.currentTimeMillis();
                            long startTimeout = start + TIMEOUT;

                            readBuffer.clear();
                            while (System.currentTimeMillis() < startTimeout && readBuffer.position() == 0)
                                if (!datagramChannel.isConnected())
                                    datagramChannel.receive(readBuffer); // always null on Android >= 5.0}
                                else
                                    datagramChannel.read(readBuffer);

                            if (readBuffer.position() == 0) {
                                countRetry++;
                                if (countRetry > TRY_COUNT) {
                                    stop = true;
                                    info("stop = true");
                                }
                                continue;
                            }

                            ping = System.currentTimeMillis() - start;

                            //deserialize received
                            readBuffer.flip();
                            input.setBuffer(readBuffer);
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
