package com.fogok.spaceships.net.controllers;

import com.fogok.dataobjects.ServerState;
import com.fogok.spaceships.net.NetRootController;
import com.fogok.spaceships.net.exception.DefaultExceptionCallBack;
import com.fogok.spaceships.net.handlers.SocServHandler;

public class NetSocServController extends DefaultController{

    public NetSocServController(NetRootController netRootController) {
        super(netRootController);
    }

    public void connectToSS(){
        openConnection(new SocServHandler(netRootController), netRootController.getAuthCallBack(),
                netRootController, netRootController.getNetRelayBalancerController().getSocServIp());
    }

    //region SocServCallBack
    private SocServCallBack socServCallBack;

    public void setSocServCallBack(SocServCallBack socServCallBack) {
        this.socServCallBack = socServCallBack;
    }

    public SocServCallBack getSocServCallBack() {
        return socServCallBack;
    }

    public interface SocServCallBack extends DefaultExceptionCallBack {
        void serverState(ServerState serverState);
    }
    //endregion
}
