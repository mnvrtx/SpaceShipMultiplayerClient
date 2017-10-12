package com.fogok.spaceships.net;

import com.fogok.spaceships.model.NetworkData;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyHandler extends ChannelInboundHandlerAdapter {

    private NetworkData _networkData;
    private final static String encoding = "UTF-8";

    public NettyHandler(NetworkData networkData) throws IOException {
        _networkData = networkData;

    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        final Channel channel = ctx.channel();

        channel.eventLoop().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.print("sending data to server...");

                String dataS = new Gson().toJson(_networkData);
                channel.write(Unpooled.copiedBuffer(dataS.getBytes(Charset.forName(encoding))));
                ctx.flush();
                System.out.println("complete: " + dataS);
            }
        }, 300, 300, TimeUnit.MILLISECONDS);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf)msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);

        String body = new String(req, encoding);
        System.out.println("Server response : " + body);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}