package com.fogok.spaceships.net.controllers;

import com.fogok.spaceships.net.NetRootController;
import com.fogok.spaceships.net.handlers.SocServHandler;

public class NetSocServController extends DefaultController{

    private SocServHandler socServHandler;

    public NetSocServController(NetRootController netRootController) {
        super(netRootController);
    }

    public void connectToSS(){
        openConnection(socServHandler = new SocServHandler(netRootController), netRootController.getAuthCallBack(),
                netRootController, netRootController.getNetRelayBalancerController().getSocServIp());
    }

    public void disconnect(){
        socServHandler.getCtx().channel().disconnect();
    }
}
