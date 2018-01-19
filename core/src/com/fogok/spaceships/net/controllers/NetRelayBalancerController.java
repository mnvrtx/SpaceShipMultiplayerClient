package com.fogok.spaceships.net.controllers;

import com.fogok.spaceships.net.relaybalancer.RelayBalancerHandler;

public class NetRelayBalancerController extends DefaultController{

    private String socServIp;

    NetRelayBalancerController(NetRootController netRootController) {
        super(netRootController);
    }

    void openConnection(NetAuthController.AuthCallBack authCallBack, String ip){
        openConnection(new RelayBalancerHandler(netRootController, authCallBack), authCallBack, netRootController, ip);
    }

    public void recieveSocServIp(String socServIp) {
        this.socServIp = socServIp;
    }

    public String getSocServIp() {
        return socServIp;
    }
}
