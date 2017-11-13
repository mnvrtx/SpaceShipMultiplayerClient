package com.fogok.spaceships.view.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.fogok.spaceships.Main;

public class NativeGdxHelper {

    private final static float sizeWidthGame = 20f;
    private OrthographicCamera uiCamera;
    private OrthographicCamera gameSessionCamera;
    private SpriteBatch uiSpriteBatch;
    private BitmapFont bitmapFont, uiBitmapFont;
    private Stage stage;

    public NativeGdxHelper() {
        uiSpriteBatch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        stage.setDebugAll(true);
        bitmapFont = new BitmapFont();  setUPFont();
        uiBitmapFont = new BitmapFont(); setUPUIFont();
        Gdx.input.setInputProcessor(stage);
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private void setUPUIFont(){
        uiBitmapFont.getData().markupEnabled = true;
        uiBitmapFont.getRegion(0).getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    private void setUPFont(){
        bitmapFont.setColor(Color.WHITE);
        bitmapFont.getData().setScale(0.03f);
        bitmapFont.setUseIntegerPositions(false);
        bitmapFont.getData().markupEnabled = true;
        bitmapFont.getRegion(0).getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    public void resize(int width, int height) {
        float aspectRatio = (float) height / width;
        uiCamera = new OrthographicCamera(sizeWidthGame, sizeWidthGame * aspectRatio);
        uiCamera.position.set(uiCamera.viewportWidth / 2f, uiCamera.viewportHeight / 2f, 0f);
        uiCamera.update();

        gameSessionCamera = new OrthographicCamera(sizeWidthGame, sizeWidthGame * aspectRatio);
        gameSessionCamera.position.set(gameSessionCamera.viewportWidth / 2f, gameSessionCamera.viewportHeight / 2f, 0f);
        gameSessionCamera.update();

        stage.getViewport().setCamera(uiCamera);
        stage.getViewport().update(width, height, true);

        Main.WIDTH = uiCamera.viewportWidth;
        Main.HEIGHT = uiCamera.viewportHeight;
        Main.DGNL = (int) Math.sqrt(Math.pow(Gdx.graphics.getWidth(), 2) + Math.pow(Gdx.graphics.getHeight(), 2));   //1325 in pixels
    }

    public OrthographicCamera getUiCamera() {
        return uiCamera;
    }

    public Stage getStage() {
        return stage;
    }

    public BitmapFont getBitmapFont() {
        return bitmapFont;
    }

    public BitmapFont getUiBitmapFont() {
        return uiBitmapFont;
    }

    public SpriteBatch getUiSpriteBatch() {
        return uiSpriteBatch;
    }

    public OrthographicCamera getGameSessionCamera() {
        return gameSessionCamera;
    }

    public void dispose(){
        uiSpriteBatch.dispose();
        stage.dispose();
    }

}
