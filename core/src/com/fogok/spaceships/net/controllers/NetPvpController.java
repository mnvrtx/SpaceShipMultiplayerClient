package com.fogok.spaceships.net.controllers;

import com.fogok.spaceships.net.NetRootController;
import com.fogok.spaceships.net.exception.DefaultExceptionCallBack;
import com.fogok.spaceships.net.handlers.PvpHandler;

import java.net.SocketException;
import java.net.UnknownHostException;

public class NetPvpController extends DefaultController{

    private PvpHandler pvpHandler;

    public NetPvpController(NetRootController netRootController) {
        super(netRootController);
    }

    public void connectToPvp(String sessionId) throws SocketException, UnknownHostException {
        String ip = "127.0.0.1:15504";
        openConnection(pvpHandler = new PvpHandler(netRootController, sessionId), new DefaultExceptionCallBack() {
            @Override
            public void exceptionConnect(Throwable cause) {

            }
        }, netRootController, ip);
    }

    public PvpHandler getPvpHandler() {
        return pvpHandler;
    }
}
