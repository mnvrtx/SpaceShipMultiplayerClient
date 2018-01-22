package com.fogok.spaceships.net.controllers;

import com.fogok.dataobjects.ConnectToServiceImpl;
import com.fogok.spaceships.net.exception.DefaultExceptionCallBack;
import com.fogok.spaceships.net.exception.DefaultExceptionHandler;
import com.fogok.spaceships.net.exception.DefaultOtherExceptionHandler;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.nio.NioEventLoopGroup;

public abstract class DefaultController {

    protected NetRootController netRootController;

    public DefaultController(NetRootController netRootController) {
        this.netRootController = netRootController;
    }

    private String ip;

    public String getIp() {
        return ip;
    }

    <T extends ChannelInboundHandlerAdapter> void openConnection(T handler, DefaultExceptionCallBack defaultExceptionCallBack, NetRootController netRootController, String ip){
        this.ip = ip;
        ConnectToServiceImpl.getInstance().connect(handler, new NioEventLoopGroup(1), new DefaultExceptionHandler(defaultExceptionCallBack),
                new DefaultOtherExceptionHandler(netRootController), new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        connectionComplete(future);
                    }
                }, ip.split(":")[0], Integer.parseInt(ip.split(":")[1]));
    }

    void connectionComplete(ChannelFuture future){

    }

//    private NioEventLoopGroup nioEventLoopGroup;
//    private NioEventLoopGroup getNioEventLoopGroup(){
//        if (nioEventLoopGroup == null)
//            nioEventLoopGroup = ;
//        return nioEventLoopGroup;
//    }
}
