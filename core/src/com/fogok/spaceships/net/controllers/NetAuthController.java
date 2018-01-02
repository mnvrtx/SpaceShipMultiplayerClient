package com.fogok.spaceships.net.controllers;

import com.fogok.spaceships.net.auth.AuthHandler;
import com.fogok.spaceships.net.exception.DefaultExceptionCallBack;

import static com.esotericsoftware.minlog.Log.info;

public class NetAuthController extends DefaultController{

    //region Native
    private NetRootController netRootController;
    //endregion

    private static final String authIp = "127.0.0.1:15501";

    public NetAuthController(NetRootController netRootController) {
        this.netRootController = netRootController;
        ip = authIp;
    }
    
    public void openConnection(String login, String passwordEncrypted){
        openConnection(new AuthHandler(netRootController, login, passwordEncrypted), authCallBack, netRootController);
    }

    public void successConnect(String token, String nickName, String relayBalancerIp){
        info(String.format("Auth complete - token: %s", token));

        netRootController.setNickName(nickName);
        netRootController.setToken(token);

        authCallBack.succesConnect(nickName);

//        Main.getScreenSwitcher().setCurrentScreen(ScreenSwitcher.Screens.HALL);
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
