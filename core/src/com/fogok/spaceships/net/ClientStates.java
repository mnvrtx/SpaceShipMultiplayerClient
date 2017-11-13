package com.fogok.spaceships.net;

import com.fogok.dataobjects.ServerState;

public interface ClientStates {
    void connectToServer();
    void responseServerState(ServerState serverState);
    void startSearchGame();
    void throwError(); //errorToConnect
}
