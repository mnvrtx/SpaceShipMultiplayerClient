package com.fogok.spaceships.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.fogok.spaceships.Main;

public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.width = (int)(854 * 0.65f);
        config.height = 480 / 2;

        //Ctrl + Shift + Alt + J    //multicursor
        //Ctrl + Shift + F8     //show breakpoints
        //Shift + Alt + i     //inspect all (maybe)

        new LwjglApplication(new Main(), config);
    }
}
