package com.fogok.spaceships.net.controllers;

import com.fogok.spaceships.net.NetRootController;
import com.fogok.spaceships.net.handlers.RelayBalancerHandler;

public class NetRelayBalancerController extends DefaultController{

    private String socServIp;

    public NetRelayBalancerController(NetRootController netRootController) {
        super(netRootController);
    }

    void openConnection(String ip){
        openConnection(new RelayBalancerHandler(netRootController), netRootController.getAuthCallBack(), netRootController, ip);
    }

    public void recieveSocServIp(String socServIp) {
        this.socServIp = socServIp;
        netRootController.getAuthCallBack().successConnectToRelayBalancer();
    }

    public String getSocServIp() {
        return socServIp;
    }
}
