package com.fogok.spaceships.view.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.fogok.spaceships.Main;

public class NativeGdxHelper {

    private final static float sizeWidthGame = 20f;
    private OrthographicCamera uiCamera;
    private OrthographicCamera gameSessionCamera;

    public NativeGdxHelper() {
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void resize(int width, int height) {
        float aspectRatio = (float) height / width;
        uiCamera = new OrthographicCamera(sizeWidthGame, sizeWidthGame * aspectRatio);
        uiCamera.position.set(uiCamera.viewportWidth / 2f, uiCamera.viewportHeight / 2f, 0f);
        uiCamera.update();

        gameSessionCamera = new OrthographicCamera(sizeWidthGame, sizeWidthGame * aspectRatio);
        gameSessionCamera.position.set(gameSessionCamera.viewportWidth / 2f, gameSessionCamera.viewportHeight / 2f, 0f);
        gameSessionCamera.update();

        Main.WIDTH = uiCamera.viewportWidth;
        Main.HEIGHT = uiCamera.viewportHeight;
        Main.DGNL = (int) Math.sqrt(Math.pow(Gdx.graphics.getWidth(), 2) + Math.pow(Gdx.graphics.getHeight(), 2));   //1325 in pixels
    }

    public OrthographicCamera getUiCamera() {
        return uiCamera;
    }

    public OrthographicCamera getGameSessionCamera() {
        return gameSessionCamera;
    }
}
