package com.fogok.spaceships.net.exception;

import java.net.SocketAddress;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import static com.esotericsoftware.minlog.Log.error;

public class DefaultExceptionHandler extends ChannelDuplexHandler {

    private DefaultExceptionCallBack defaultExceptionCallBack;

    public DefaultExceptionHandler(DefaultExceptionCallBack defaultExceptionCallBack){
        this.defaultExceptionCallBack = defaultExceptionCallBack;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        throwException(cause);
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
        ctx.connect(remoteAddress, localAddress, promise.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                if (!future.isSuccess()) {
                    throwException(future.cause());
                }
            }
        }));
    }

    private void throwException(Throwable cause) {
        defaultExceptionCallBack.exceptionConnect(cause);
        error(String.format("Exception in connect to service: %s", cause));
        cause.printStackTrace();
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        ctx.write(msg, promise.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                if (!future.isSuccess()) {
                    error(String.format("Unhandled exception in write to channel: %s", future.cause()));
                }
            }
        }));
    }


}
