package com.fogok.spaceships.net.controllers;

import com.fogok.spaceships.net.auth.AuthHandler;
import com.fogok.spaceships.net.exception.DefaultExceptionCallBack;

import static com.esotericsoftware.minlog.Log.info;

public class NetAuthController extends DefaultController{

    private static final String authIp = "127.0.0.1:15501";

    NetAuthController(NetRootController netRootController) {
        super(netRootController);
    }

    public void openConnection(String login, String passwordEncrypted){
        openConnection(new AuthHandler(netRootController, login, passwordEncrypted), authCallBack, netRootController, authIp);
    }

    public void receiveToken(String token, String nickName){
        info(String.format("Auth complete - token: %s", token));

        netRootController.setNickName(nickName);
        netRootController.setToken(token);
    }

    public void connectToRelayBalancer(String relayBalIp){
        netRootController.getNetRelayBalancerController().openConnection(authCallBack, relayBalIp);
    }

    //region AuthCallBack
    private AuthCallBack authCallBack;

    public void setAuthCallBack(AuthCallBack authCallBack) {
        this.authCallBack = authCallBack;
    }

    public interface AuthCallBack extends DefaultExceptionCallBack {
        void succesConnect(String nickName);
    }
    //endregion
}
