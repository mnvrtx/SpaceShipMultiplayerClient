package com.fogok.spaceships.view.screens.login;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.fogok.spaceships.Main;
import com.fogok.spaceships.net.UICallBacks;
import com.fogok.spaceships.net.controllers.NetRootController;
import com.fogok.dataobjects.utils.Base64;
import com.fogok.spaceships.utils.gamedepended.Assets;
import com.fogok.spaceships.view.screens.ScreenSwitcher;
import com.fogok.spaceships.view.utils.NativeGdxHelper;
import com.fogok.spaceships.view.utils.NormalLabel;

import java.util.regex.Pattern;

public class LoginScreen implements Screen{

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private Stage stage;
    private NetRootController netRootController;

    private enum ConnectionToServiceStates {
                WELCOME("ВВЕДИТЕ СВОЙ E-MAIL И ПАРОЛЬ."),
                CONNECT_TO_AUTH("ПОДКЛЮЧЕНИЕ К СЕРВИСУ АВТОРИЗАЦИИ"),
                CONNECT_TO_RELAY("ПОЛУЧЕНИЕ ИНФОРМАЦИИ О СЕРВИСАХ"),
                CONNECT_TO_SOC_SERV("ПОДКЛЮЧЕНИЕ К СЕРВИСУ ЛОББИ"),
                ERROR_CONNECT("ОШИБКА ПОДКЛЮЧЕНИЯ:\n %s");

        private final String str;
        ConnectionToServiceStates(final String str) { this.str = str; }
        public String toString() { return str; }
    }

    public LoginScreen(NativeGdxHelper nativeGdxHelper, final NetRootController netRootController) {
        stage = nativeGdxHelper.getStage();
        this.netRootController = netRootController;

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        Label.LabelStyle labelStyle = new Label.LabelStyle(nativeGdxHelper.getUiBitmapFont(), Color.CYAN);
//        labelStyle.background = new TextureRegionDrawable(Assets.getRegion(5));



        Label loginText = new Label("E-MAIL", labelStyle);
        Label passwordText = new Label("ПАРОЛЬ", labelStyle);

        labelStyle.fontColor = null;
        final NormalLabel statusBar = new NormalLabel(ConnectionToServiceStates.WELCOME.toString(), labelStyle);

        textFieldStyle.font = nativeGdxHelper.getUiBitmapFont();
        textFieldStyle.fontColor = new Color(Color.WHITE);
        final TextField login = new TextField("", textFieldStyle);
        final TextField password = new TextField("", textFieldStyle);
        password.setPasswordCharacter('*');
        password.setPasswordMode(true);

        login.setText("test1@test.com");
        password.setText("123456");

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = new TextureRegionDrawable(Assets.getRegion(4));
        textButtonStyle.down = new TextureRegionDrawable(Assets.getRegion(5));
        textButtonStyle.font = nativeGdxHelper.getUiBitmapFont();
        final TextButton loginButton = new TextButton("ПОДКЛЮЧИТЬСЯ", textButtonStyle);


        netRootController.getUiCallBacks().setAuthCallBack(new UICallBacks.AuthCallBack() {
            @Override
            public void successConnectToAuth() {
                statusBar.updateText(ConnectionToServiceStates.CONNECT_TO_RELAY.toString());
                statusBar.setColor(Color.BLUE);
            }

            @Override
            public void successConnectToRelayBalancer() {
                statusBar.updateText(ConnectionToServiceStates.CONNECT_TO_SOC_SERV.toString());
                statusBar.setColor(Color.BLUE);
            }

            @Override
            public void successConnectToSocServ() {
                Main.getScreenSwitcher().setCurrentScreen(ScreenSwitcher.Screens.HALL);
            }

            @Override
            public void exceptionConnect(Throwable cause) {
                statusBar.updateText(
                        String.format(ConnectionToServiceStates.ERROR_CONNECT.toString(),
                                cause.getMessage()
                                .split(System.getProperty("line.separator"))[0])
                );
                statusBar.setColor(Color.FIREBRICK);
                loginButton.setDisabled(false);
            }
        });

        loginButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                boolean emailCorrect = VALID_EMAIL_ADDRESS_REGEX.matcher(login.getText()).find();
                boolean passwordCorrect = password.getText().length() > 5;
                if (emailCorrect && passwordCorrect) {
                    statusBar.setColor(Color.BLUE);
                    statusBar.updateText(ConnectionToServiceStates.CONNECT_TO_AUTH.toString());
                    loginButton.setDisabled(true);
                    netRootController.getNetAuthController().openConnection(login.getText(),
                            Base64.encode(password.getText().getBytes()));
                } else if (!emailCorrect) {
                    statusBar.setColor(Color.FIREBRICK);
                    statusBar.updateText("Неправильный e-mail. Перепроверьте правильность");
                } else {
                    statusBar.setColor(Color.FIREBRICK);
                    statusBar.updateText("Длина пароля должна стостоять >= 6 символов");
                }

            }
        });



        float width = 300f, height = 40f;

        Table table = new Table();
        table.add(loginText).align(Align.right);
        table.add(login).width(width).height(height);
        table.row();
        table.add(passwordText).align(Align.right);
        table.add(password).width(width).height(height);
        table.row();
        table.add(loginButton).colspan(2);
        table.row();
        table.add(statusBar).colspan(2);

        table.setPosition(Main.WIDTH / 2f, Main.HEIGHT / 2f, Align.center);
        stage.addActor(table);

    }

    @Override
    public void show() {
        
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.02f, 0.02f, 0.02f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.clear();
        netRootController.getUiCallBacks().setAuthCallBack(null);
    }
}
