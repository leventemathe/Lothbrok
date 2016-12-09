package com.lothbrok.game.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.constants.MainMenuConstants;
import com.lothbrok.game.constants.ScreensConstants;

public class MainMenuScreen extends AbstractScreen {

    public static final String TAG = MainMenuScreen.class.getSimpleName();

    private Stage stage;
    private Skin skin;

    private Color colorSky;

    public MainMenuScreen(Assets assets) {
        super(assets);
    }

    @Override
    public void show() {
        super.show();
        Gdx.app.debug(TAG, "show");
        stage = new Stage(new FitViewport(ScreensConstants.VIEWPORT_MENU_WIDTH, ScreensConstants.VIEWPORT_MENU_HEIGHT));
        skin = assets.getMainMenuSkin();
        colorSky = skin.getColor(MainMenuConstants.MAIN_MENU_COLOR_SKY);
        Gdx.input.setInputProcessor(stage);
        rebuildStage();
    }

    private void rebuildStage() {
        stage.clear();
        Stack rootStack = new Stack();
        rootStack.setFillParent(true);
        Table rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootStack);

        Image background = skin.get(MainMenuConstants.MAIN_MENU_BACKGROUND_IMAGE, Image.class);
        Container<Image> backgroundContainer = new Container<>();
        backgroundContainer.setActor(background);
        backgroundContainer.bottom();
        rootStack.addActor(backgroundContainer);

        Image logo = skin.get(MainMenuConstants.MAIN_MENU_LOGO_IMAGE, Image.class);
        rootTable.add(logo).expand().center().row();
        TextButton btnStart = buildButton(MainMenuConstants.MAIN_MENU_BTN_START);
        btnStart.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(Gdx.app.getType() == Application.ApplicationType.Android ||
                        Gdx.app.getType() == Application.ApplicationType.iOS) {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new MobileGameScreen(assets));
                } else {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(assets));
                }
            }
        });
        rootTable.add(btnStart).expand().center().row();

        TextButton btnQuit = buildButton(MainMenuConstants.MAIN_MENU_BTN_QUIT);
        btnQuit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        rootTable.add(btnQuit).expand().center().row();

        rootStack.addActor(rootTable);
    }

    private TextButton buildButton(String text) {
        TextButton.TextButtonStyle style = skin.get(MainMenuConstants.MAIN_MENU_TEXT_BUTTON_STYLE, TextButton.TextButtonStyle.class);
        style.font = assets.getPrVikingFont().getFont96();
        style.fontColor = skin.getColor(MainMenuConstants.MAIN_MENU_COLOR_WHITE);
        TextButton btn = new TextButton(text, style);
        return btn;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(colorSky.r, colorSky.g, colorSky.b, colorSky.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
        super.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        Gdx.app.debug(TAG, "dispose");
        stage.dispose();
        super.dispose();
    }
}
