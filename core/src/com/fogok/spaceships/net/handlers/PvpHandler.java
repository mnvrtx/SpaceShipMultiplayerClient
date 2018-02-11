package com.fogok.spaceships.net.handlers;

import com.esotericsoftware.kryo.io.ByteBufferInput;
import com.esotericsoftware.kryo.io.ByteBufferOutput;
import com.fogok.dataobjects.ConnectToServiceImpl;
import com.fogok.dataobjects.transactions.pvp.PvpTransactionHeaderType;
import com.fogok.dataobjects.utils.Serialization;
import com.fogok.spaceships.net.NetRootController;

import java.nio.ByteBuffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

import static com.esotericsoftware.minlog.Log.info;

public class PvpHandler extends BaseChannelHandler {

    private String sessionId;

    public final Object lock = new Object();

    public static int ping;

    ChannelHandlerContext ctx;

    private final ByteBufferInput input = new ByteBufferInput(ByteBuffer.allocate(ConnectToServiceImpl.BUFFER_SIZE));
    private final ByteBufferOutput output = new ByteBufferOutput(ByteBuffer.allocateDirect(ConnectToServiceImpl.BUFFER_SIZE));

    public PvpHandler(NetRootController netRootController, String sessionId){
        super(netRootController);
        this.sessionId = sessionId;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.ctx = ctx;
        sendStartData();
    }

    private void sendStartData(){
        info("Try to send start data");
        //prepare to serialize
        output.clear();

        //serialize
        //header
        output.writeInt(PvpTransactionHeaderType.START_DATA.ordinal(), true);
        //content
        output.writeString(sessionId);
        output.writeString(netRootController.getAuthPlayerToken());

        //send
        ctx.channel().writeAndFlush(Unpooled.wrappedBuffer((ByteBuffer) output.getByteBuffer().flip()));
        startTimeOut(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!netRootController.fastReadBlock) {
            netRootController.fastReadBlock = true;
            ByteBuf byteBuf = (ByteBuf) msg;
            input.setBuffer(byteBuf.retain().nioBuffer());
            netRootController.fastReadChannel(this, input, byteBuf);
            synchronized (lock) {
                netRootController.isRead = false;
            }
        }

    }

    private volatile boolean stop;
    public void startLoopPingPong() {
        info(String.format("Started loop pingpong to %s service: ", ctx.channel().remoteAddress()));
        channelCompleteAction();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!stop) {
                    synchronized (lock) {
                        if (!netRootController.isRead) {
                            netRootController.isRead = true;
                            //prepare to serialize
                            output.clear();

                            //serialize
                            //header
                            output.writeInt(PvpTransactionHeaderType.CONSOLE_STATE.ordinal(), true);
                            //content
                            Serialization.instance.getKryo().writeObject(output, Serialization.instance.getPlayerData());

                            //send
                            ctx.channel().writeAndFlush(Unpooled.wrappedBuffer((ByteBuffer) output.getByteBuffer().flip()));
                        }
                    }
                }
            }
        }).start();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }


    public void stop() {
        stop = true;
    }
}
