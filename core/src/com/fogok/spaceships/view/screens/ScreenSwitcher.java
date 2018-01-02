package com.fogok.spaceships.view.screens;

import com.badlogic.gdx.Screen;
import com.fogok.spaceships.net.controllers.NetRootController;
import com.fogok.spaceships.view.screens.game_session.GameProcess;
import com.fogok.spaceships.view.screens.hall.Hall;
import com.fogok.spaceships.view.screens.login.LoginScreen;
import com.fogok.spaceships.view.utils.NativeGdxHelper;

public abstract class ScreenSwitcher {

    private final NativeGdxHelper nativeGdxHelper;
    private final NetRootController netRootController;

    public ScreenSwitcher(NetRootController netRootController) {
        this.netRootController = netRootController;
        nativeGdxHelper = new NativeGdxHelper();
    }

    private Screen currentScreen;

    public enum Screens{
        LOGIN, GAMESESSION, HALL
    }

    public void setCurrentScreen(Screens screenEnum) {
        dispose();
        switch (screenEnum) {
            case LOGIN:
                currentScreen = new LoginScreen(nativeGdxHelper, netRootController);
                break;
            case GAMESESSION:
                currentScreen = new GameProcess(nativeGdxHelper, netRootController);
                break;
            case HALL:
                currentScreen = new Hall(nativeGdxHelper, netRootController);
                break;
            default:
                return;
        }
        flush(currentScreen);
    }

    public abstract void flush(Screen screen);

    public NativeGdxHelper getNativeGdxHelper() {
        return nativeGdxHelper;
    }

    public void disposeAll(){
        dispose();
        nativeGdxHelper.dispose();
    }

    public void dispose() {
        if (currentScreen != null)
            currentScreen.dispose();
    }
}
