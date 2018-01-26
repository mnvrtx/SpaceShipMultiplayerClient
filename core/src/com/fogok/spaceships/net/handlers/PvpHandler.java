package com.fogok.spaceships.net.handlers;

import com.fogok.spaceships.net.NetRootController;

import java.net.DatagramPacket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class PvpHandler extends SimpleChannelInboundHandler<DatagramPacket>{

    private boolean isConnected;
    private NetRootController netRootController;

    public PvpHandler(NetRootController netRootController) {
        this.netRootController = netRootController;
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        isConnected = true;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        netRootController.readServerChannel(ctx.channel(), msg, null);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        isConnected = false;
    }

    public void startLoopPingPong(){

    }

    public boolean isConnected() {
        return isConnected;
    }
}
