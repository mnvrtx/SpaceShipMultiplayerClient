package com.fogok.spaceships.net.controllers;

import com.fogok.dataobjects.ConnectToServiceImpl;
import com.fogok.spaceships.net.relaybalancer.RelayBalancerHandler;

import static com.esotericsoftware.minlog.Log.info;

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
        ConnectToServiceImpl.getInstance().isThreadOnly = false;
        openConnection(new RelayBalancerHandler(netRootController, token, authCallBack), authCallBack, netRootController);
    }

    public void receiveSSInfo(String ssIp) {
        this.ssIp = ssIp;
    }

    public void connectToSS(){
        info(String.format("tryToConnectSS: %s", ssIp));
    }


}
