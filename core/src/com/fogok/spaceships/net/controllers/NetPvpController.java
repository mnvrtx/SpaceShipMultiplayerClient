package com.fogok.spaceships.net.controllers;

import com.fogok.spaceships.net.NetRootController;
import com.fogok.spaceships.net.handlers.PvpHandler;

public class NetPvpController extends DefaultController{

    private PvpHandler pvpHandler;

    public NetPvpController(NetRootController netRootController) {
        super(netRootController);
    }


    public void connectToPvp(){

    }
}
