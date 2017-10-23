package com.fogok.spaceships.control.game;

import com.fogok.spaceships.model.game.dataobjects.GameObject;

public interface ObjectController {
    void setHandledObject(GameObject handledObject);
    void handleClient(boolean pause);
    boolean isAlive();
}
