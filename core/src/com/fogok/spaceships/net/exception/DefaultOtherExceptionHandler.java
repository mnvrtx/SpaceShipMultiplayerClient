package com.fogok.spaceships.net.exception;

import com.esotericsoftware.minlog.Log;
import com.fogok.dataobjects.transactions.ErrorConnectionToServiceCallback;
import com.fogok.spaceships.net.NetRootController;


public class DefaultOtherExceptionHandler implements ErrorConnectionToServiceCallback{

    private NetRootController netRootController;
    public DefaultOtherExceptionHandler(NetRootController netRootController) {
        this.netRootController = netRootController;
    }

    @Override
    public void error(Throwable cause) {
        Log.error(String.format("OtherUnhandledException: %s", cause));
    }
}
