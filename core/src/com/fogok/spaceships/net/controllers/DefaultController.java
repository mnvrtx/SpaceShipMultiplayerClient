package com.fogok.spaceships.net.controllers;

import com.fogok.dataobjects.ConnectToServiceImpl;
import com.fogok.spaceships.net.exception.DefaultExceptionCallBack;
import com.fogok.spaceships.net.exception.DefaultExceptionHandler;
import com.fogok.spaceships.net.exception.DefaultOtherExceptionHandler;

import io.netty.channel.ChannelInboundHandlerAdapter;

public abstract class DefaultController {

    String ip;

    public <T extends ChannelInboundHandlerAdapter> void openConnection(T handler, DefaultExceptionCallBack defaultExceptionCallBack, NetRootController netRootController){
        ConnectToServiceImpl.getInstance().connect(handler, new DefaultExceptionHandler(defaultExceptionCallBack),
                new DefaultOtherExceptionHandler(netRootController), ip.split(":")[0], Integer.parseInt(ip.split(":")[1]));
    }
}
