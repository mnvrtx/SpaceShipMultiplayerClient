package com.fogok.spaceships.net.controllers;

import com.fogok.dataobjects.ServerState;

public class NetHallController {

    //region Native
    private NetRootController netRootController;
    //endregion

    NetHallController(NetRootController netRootController) {
        this.netRootController = netRootController;
    }

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
