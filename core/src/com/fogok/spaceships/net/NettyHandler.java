package com.fogok.spaceships.net;

import com.fogok.spaceships.model.NetworkData;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyHandler extends ChannelInboundHandlerAdapter {

    private NetworkData networkData;
    private String uuid;
    private final static String encoding = "UTF-8";
    private boolean blocker;

    public static float TIMEITERSSLEEP = 0.016f;  //in seconds

    public NettyHandler(NetworkData networkData) throws IOException {
        this.networkData = networkData;
        uuid = UUID.randomUUID().toString();
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        final Channel channel = ctx.channel();
        final int sleepTimeMilliSeconds = (int) (TIMEITERSSLEEP * 1000);

        channel.eventLoop().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                if (!blocker) {
                    System.out.print("Sending data to server...");
                    String dataS = networkData.getJSON();
                    ByteBuf byteBuf = Unpooled.copiedBuffer(dataS.getBytes(Charset.forName(encoding)));
                    channel.write(byteBuf);
                    ctx.flush();
                    blocker = false;
                    System.out.println("Complete:" + dataS);
                }


            }
        }, sleepTimeMilliSeconds, sleepTimeMilliSeconds, TimeUnit.MILLISECONDS);
    }



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf)msg;
        try {
            byte[] req = new byte[buf.readableBytes()];
            buf.readBytes(req);

            String json = new String(req, encoding);
            networkData.refreshOtherDatas(json);
        } finally {
            buf.release();
        }

//        blocker = false;
//        System.out.println("Server response:" + json);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.toString());
        ctx.close();
    }
}