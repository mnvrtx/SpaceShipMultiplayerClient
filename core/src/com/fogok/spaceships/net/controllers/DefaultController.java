package com.fogok.spaceships.net.controllers;

import com.fogok.dataobjects.ConnectToServiceImpl;
import com.fogok.spaceships.net.exception.DefaultExceptionCallBack;
import com.fogok.spaceships.net.exception.DefaultExceptionHandler;
import com.fogok.spaceships.net.exception.DefaultOtherExceptionHandler;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInboundHandlerAdapter;

public abstract class DefaultController {

    String ip;

    <T extends ChannelInboundHandlerAdapter> void openConnection(T handler, DefaultExceptionCallBack defaultExceptionCallBack, NetRootController netRootController){
        ConnectToServiceImpl.getInstance().connect(handler, new DefaultExceptionHandler(defaultExceptionCallBack),
                new DefaultOtherExceptionHandler(netRootController), new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {

                    }
                }, ip.split(":")[0], Integer.parseInt(ip.split(":")[1]));
    }
}
