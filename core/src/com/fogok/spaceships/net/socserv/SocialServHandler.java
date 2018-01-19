package com.fogok.spaceships.net.socserv;

import com.fogok.spaceships.net.exception.DefaultExceptionCallBack;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SocialServHandler extends ChannelInboundHandlerAdapter {

    private DefaultExceptionCallBack defaultExceptionCallBack;

    public SocialServHandler(DefaultExceptionCallBack defaultExceptionCallBack) {
        this.defaultExceptionCallBack = defaultExceptionCallBack;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    }
}
