package com.fogok.spaceships.net;

import com.fogok.dataobjects.ServerState;
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

    private SocServCallBack socServCallBack;

    public void setSocServCallBack(SocServCallBack socServCallBack) {
        this.socServCallBack = socServCallBack;
    }

    public SocServCallBack getSocServCallBack() {
        return socServCallBack;
    }

    public interface SocServCallBack extends DefaultExceptionCallBack {
        void recieveServerState(ServerState serverState);
    }

    private interface PvpCallBack{

    }

}
