package com.fogok.spaceships.net.handlers;

import com.fogok.spaceships.net.NetRootController;

import io.netty.channel.ChannelHandlerContext;

public class PvpHandler extends BaseChannelHandler {

    public PvpHandler(NetRootController netRootController) {
        super(netRootController);
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }
}
