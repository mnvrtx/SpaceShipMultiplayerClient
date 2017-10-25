package com.fogok.spaceships.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.fogok.spaceships.Main;

public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.width = 854;
        config.height = 480;//Ctrl + Shift + Alt + J

        new LwjglApplication(new Main(), config);
    }
}
