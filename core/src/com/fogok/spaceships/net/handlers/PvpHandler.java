package com.fogok.spaceships.net.handlers;

import com.esotericsoftware.kryo.io.ByteBufferInput;
import com.esotericsoftware.kryo.io.ByteBufferOutput;
import com.fogok.dataobjects.ConnectToServiceImpl;
import com.fogok.dataobjects.transactions.pvp.PvpTransactionHeaderType;
import com.fogok.dataobjects.utils.Serialization;
import com.fogok.spaceships.net.NetRootController;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import static com.esotericsoftware.minlog.Log.info;

public class PvpHandler {

    private final static long TIMEITERSSLEEP = 16;
    private final static int TRY_COUNT = 200;
    private final static int TIMEOUT = 4;

    private boolean isConnected;
    private NetRootController netRootController;
    private DatagramChannel datagramChannel;
    private final ByteBufferInput input = new ByteBufferInput(ByteBuffer.allocate(ConnectToServiceImpl.BUFFER_SIZE));
    private final ByteBufferOutput output = new ByteBufferOutput(ByteBuffer.allocateDirect(ConnectToServiceImpl.BUFFER_SIZE));

//    DatagramPacket datagramSend = new DatagramPacket(new byte[0], 0);
//    DatagramPacket datagramReceive = new DatagramPacket(new byte[0], 0);

    private InetSocketAddress inetSocketAddress;

    public PvpHandler(NetRootController netRootController, InetSocketAddress inetSocketAddress){
        this.netRootController = netRootController;
        this.inetSocketAddress = inetSocketAddress;

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
        //get free port
        ServerSocket serverSocket = new ServerSocket(0);
        int port = serverSocket.getLocalPort();
        serverSocket.close();
        serverSocket = null;
        //init
        datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        datagramChannel.socket().bind(new InetSocketAddress(port));
        datagramChannel.connect(inetSocketAddress);

        trySendStartData(tryCount);
    }

    private void trySendStartData(int tryCount){
        for (int i = 0; i < tryCount; i++) {
            try {
                info("Try to send start data");

                //prepare to serialize
                output.clear();

                //serialize
                //header
                output.writeInt(0);
                output.writeInt(PvpTransactionHeaderType.START_DATA.ordinal(), true);
                //content
                output.writeString(netRootController.getNetPvpController().getSessionId());
                output.writeString(netRootController.getAuthPlayerToken());

                //set output header size bytes
                writeBytesSizeToHeader(output);
                //send
                datagramChannel.send((ByteBuffer) output.getByteBuffer().flip(), inetSocketAddress);

                //receive
                ByteBuffer buffer = (ByteBuffer) input.getByteBuffer().clear();

                Thread.sleep(30);

                if (!datagramChannel.isConnected())
                    datagramChannel.receive(buffer); // always null on Android >= 5.0}
                else
                    datagramChannel.read(buffer);


                //if receive - read
                if (buffer.position() != 0) {
                    //check bytes count
                    buffer.flip();
                    if (!checkBytesCount(input))
                        continue;

                    //deserialize received
                    netRootController.readUdpResponse(this, input);
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static volatile boolean stop = false;
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
                            output.clear();

                            //serialize
                            //header
                            output.writeInt(0);
                            output.writeInt(PvpTransactionHeaderType.CONSOLE_STATE.ordinal(), true);
                            //content
                            Serialization.instance.getKryo().writeObject(output, Serialization.instance.getPlayerData());

                            //set output header size bytes
                            writeBytesSizeToHeader(output);

                            //send
                            datagramChannel.send((ByteBuffer) output.getByteBuffer().flip(), inetSocketAddress);

                            //maybe receive
                            long start = System.currentTimeMillis();

                            ByteBuffer buffer = (ByteBuffer) input.getByteBuffer().clear();

                            Thread.sleep(TIMEOUT);

                            if (!datagramChannel.isConnected())
                                datagramChannel.receive(buffer); // always null on Android >= 5.0}
                            else
                                datagramChannel.read(buffer);

                            //if receive - read
                            if (buffer.position() != 0) {
                                //check bytes count
                                buffer.flip();
                                if (!checkBytesCount(input))
                                    continue;

                                //save ping
                                ping = System.currentTimeMillis() - start;
                                //deserialize received
                                netRootController.readUdpResponse(pvpHandler, input);
                                countRetry = 0;
                            }

                        } catch (IOException e) {
                            //not received... return to start and increment countRetry
                            e.printStackTrace();
//                            countRetry++;
//                            if (countRetry > TRY_COUNT) {
//                                stop = true;
//                                info("stop = true");
//                            }
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (BufferUnderflowException e) {
                            //nothing
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                isConnected = false;
            }
        }).start();
    }

    private boolean checkBytesCount(ByteBufferInput input) {
        int targetBytes = input.readInt();
        return input.getByteBuffer().limit() == targetBytes;
    }

    private void writeBytesSizeToHeader(ByteBufferOutput output){
        int pos = output.getByteBuffer().position();
        output.setPosition(0);
        output.writeInt(pos);
        output.setPosition(pos);
    }

    public void stop() {
        stop = true;
        try {
            datagramChannel.close();
        } catch (IOException e) {
            //mute
        }
    }

    public boolean isConnected() {
        return isConnected;
    }
}
