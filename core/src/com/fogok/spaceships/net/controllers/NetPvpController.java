package com.fogok.spaceships.net.controllers;

import com.fogok.dataobjects.ConnectToServiceImpl;
import com.fogok.spaceships.net.NetRootController;
import com.fogok.spaceships.net.exception.DefaultExceptionCallBack;
import com.fogok.spaceships.net.exception.DefaultExceptionHandler;
import com.fogok.spaceships.net.exception.DefaultOtherExceptionHandler;
import com.fogok.spaceships.net.handlers.PvpHandler;

import java.net.SocketException;
import java.net.UnknownHostException;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.nio.NioEventLoopGroup;

public class NetPvpController extends DefaultController{

    private PvpHandler pvpHandler;
    private String sessionId;
    private NioEventLoopGroup workerGroup;

    public NetPvpController(NetRootController netRootController) {
        super(netRootController);
    }

    public void connectToPvp(String sessionId) throws SocketException, UnknownHostException {
        String ip = "127.0.0.1:15504";
        this.sessionId = sessionId;
        ConnectToServiceImpl.getInstance().connect(
                pvpHandler = new PvpHandler(netRootController, ip),
                workerGroup = new NioEventLoopGroup(2),

                new DefaultExceptionHandler(new DefaultExceptionCallBack() {
                    @Override
                    public void exceptionConnect(Throwable cause) {

                    }
                }),

                new DefaultOtherExceptionHandler(netRootController), new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {

                    }
                }, ip.split(":")[0], Integer.parseInt(ip.split(":")[1]), false);
    }

    public PvpHandler getPvpHandler() {
        return pvpHandler;
    }

    public String getSessionId() {
        return sessionId;
    }

    public NioEventLoopGroup getWorkerGroup() {
        return workerGroup;
    }
}
