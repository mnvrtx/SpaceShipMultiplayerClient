package com.fogok.spaceships.net.controllers;

import com.fogok.spaceships.net.relaybalancer.RelayBalancerHandler;

public class NetRelayBalancerController extends DefaultController{

    //region Native
    private NetRootController netRootController;
    //endregion

    private String ssIp;

    NetRelayBalancerController(NetRootController netRootController) {
        this.netRootController = netRootController;
    }

    void setIp(String ip) {
        this.ip = ip;
    }

    void openConnection(String token, NetAuthController.AuthCallBack authCallBack){
        openConnection(new RelayBalancerHandler(netRootController, token, authCallBack), authCallBack, netRootController);
    }

    public void receiveSSInfo(String ssIp) {
        this.ssIp = ssIp;
    }

    public void connectToSS(){
        openConnection(new RelayBalancerHandler(netRootController, netRootController.getToken(), authCallBack), authCallBack, netRootController);
    }


}
