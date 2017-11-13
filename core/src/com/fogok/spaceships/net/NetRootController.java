package com.fogok.spaceships.net;

public class NetRootController {

    private NetHallController netHallController;
    private NetSessionController netSessionController;

    public NetRootController() {
        netHallController = new NetHallController(this);
        netSessionController = new NetSessionController();
    }

    public NetHallController getNetHallController() {
        return netHallController;
    }

    public NetSessionController getNetSessionController() {
        return netSessionController;
    }
}
