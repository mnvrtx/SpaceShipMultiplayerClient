package com.fogok.spaceships.net.controllers;

import com.fogok.spaceships.net.NetRootController;
import com.fogok.spaceships.net.handlers.PvpHandler;

import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import io.netty.channel.nio.NioEventLoopGroup;

public class NetPvpController extends DefaultController{

    private PvpHandler pvpHandler;
    private String sessionId;
    private NioEventLoopGroup workerGroup;

    public NetPvpController(NetRootController netRootController) {
        super(netRootController);
    }

    public void connectToPvp(String sessionId) throws SocketException, UnknownHostException {
        String ip = "192.168.1.102:15504";
        InetSocketAddress inetSocketAddress = new InetSocketAddress(ip.split(":")[0], Integer.parseInt(ip.split(":")[1]));
        this.sessionId = sessionId;
        workerGroup = new NioEventLoopGroup(2);
        pvpHandler = new PvpHandler(netRootController, inetSocketAddress);
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
