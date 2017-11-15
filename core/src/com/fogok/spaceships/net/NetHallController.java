package com.fogok.spaceships.net;

import com.fogok.dataobjects.ServerState;

public class NetHallController {

    //region Native
    private NetRootController netRootController;
    //endregion

    private String login, password;

    public NetHallController(NetRootController netRootController) {
        this.netRootController = netRootController;
    }

    public void openConnection(String login, String password){
        this.login = login;
        this.password = password;
        ServerLogicWrapper.openServerSocket(netRootController);
    }

    //region Getters
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
    //endregion

    //region ConnectionCallBack
    private ConnectionCallBack connectionCallBack;

    public void setConnectionCallBack(ConnectionCallBack connectionCallBack) {
        this.connectionCallBack = connectionCallBack;
    }

    ConnectionCallBack getConnectionCallBack() {
        return connectionCallBack;
    }

    public interface ConnectionCallBack{
        void exceptionConnect();
        void succesConnect(String token);
    }
    //endregion

    //region HallCallBack
    private HallCallBack hallCallBack;

    public void setHallCallBack(HallCallBack hallCallBack) {
        this.hallCallBack = hallCallBack;
    }

    public HallCallBack getHallCallBack() {
        return hallCallBack;
    }

    public interface HallCallBack{
        void serverState(ServerState serverState);
    }
    //endregion
}
