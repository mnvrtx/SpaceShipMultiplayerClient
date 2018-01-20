package com.fogok.spaceships.net;

import com.fogok.spaceships.net.exception.DefaultExceptionCallBack;

public class UICallBacks {

    private AuthCallBack authCallBack;

    public void setAuthCallBack(AuthCallBack authCallBack) {
        this.authCallBack = authCallBack;
    }

    public AuthCallBack getAuthCallBack() {
        return authCallBack;
    }

    public interface AuthCallBack extends DefaultExceptionCallBack {
        void successConnectToAuth();
        void successConnectToRelayBalancer();
        void successConnectToSocServ();
    }

}
