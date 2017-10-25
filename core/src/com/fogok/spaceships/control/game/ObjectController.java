package com.fogok.spaceships.control.game;

import com.fogok.spaceships.model.game.dataobjects.GameObject;

public interface ObjectController {

    /**
     * Интерфейс, который применяется ко всем базовым контроллерам
     * */

    void setHandledObject(GameObject handledObject);
    void handleClient(boolean pause);
    boolean isAlive();
}
