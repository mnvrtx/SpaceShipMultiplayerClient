package com.fogok.spaceships.net;

import java.net.SocketAddress;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public class ExceptionHandler extends ChannelDuplexHandler {

    private NetRootController netRootController;

    public ExceptionHandler(NetRootController netRootController){
        this.netRootController = netRootController;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Uncaught exceptions from inbound handlers will propagate up to this handler
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
        ctx.connect(remoteAddress, localAddress, promise.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                if (!future.isSuccess()) {
                    NetHallController.ConnectionCallBack connectionCallBack = netRootController.getNetHallController().getConnectionCallBack();
                    if (connectionCallBack != null) {
                        connectionCallBack.exceptionConnect();
                    }
                }
            }
        }));
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        ctx.write(msg, promise.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                if (!future.isSuccess()) {
                    System.out.println("qwkqwpwqdkwqd;klwqd");
                }
            }
        }));
    }


}
