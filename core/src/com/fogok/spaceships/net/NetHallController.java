package com.fogok.spaceships.net;

public class NetHallController {

    //region Native
    private ConnectionCallBack connectionCallBack;
    private NetRootController netRootController;
    //endregion

    private String login, password;

    public NetHallController(NetRootController netRootController) {
        this.netRootController = netRootController;
    }

    public void openConnection(String login, String password){
        this.login = login;
        this.password = password;
        System.out.println("login " + login + " " + "password " + password);
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

    //region CallBack
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
}
