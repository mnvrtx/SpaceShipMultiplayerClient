package com.fogok.spaceships.net.controllers;

import com.fogok.spaceships.net.NetRootController;
import com.fogok.spaceships.net.handlers.AuthHandler;

import static com.esotericsoftware.minlog.Log.info;

public class NetAuthController extends DefaultController{

    private static final String authIp = "127.0.0.1:15501";

    public NetAuthController(NetRootController netRootController) {
        super(netRootController);
    }

    public void openConnection(String login, String passwordEncrypted){
        openConnection(new AuthHandler(netRootController, login, passwordEncrypted),
                netRootController.getAuthCallBack(), netRootController, authIp);
    }

    public void receiveToken(String token, String nickName){
        info(String.format("Auth complete - token: %s", token));

        netRootController.setNickName(nickName);
        netRootController.setToken(token);
    }

    public void connectToRelayBalancer(String relayBalIp){
        netRootController.getAuthCallBack().successConnectToAuth();
        netRootController.getNetRelayBalancerController().openConnection(relayBalIp);
    }
}
