package com.lothbrok.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.screens.utils.ColorRectangleActor;
import com.lothbrok.game.constants.ScreensConstants;

public class MainMenuScreen extends AbstractScreen {

    public static final String TAG = MainMenuScreen.class.getSimpleName();

    private Stage stage;
    private Skin skin;

    private Image logo;

    private ColorRectangleActor sky;
    private Image greenHills;
    private Image blueHills;
    private Image clouds;
    private float cloudsSpeed = ScreensConstants.SPEED_MENU_CLOUDS;

    private TextButton btnStart;
    private TextButton btnOptions;
    private TextButton btnQuit;

    private SpriteBatch batch;

    @Override
    public void show() {
        super.show();
        Gdx.app.log(TAG, "show");
        //TODO make a proper viewport
        stage = new Stage(new FitViewport(ScreensConstants.VIEWPORT_MENU_WIDTH, ScreensConstants.VIEWPORT_MENU_HEIGHT));
        skin = Assets.instance.getMainMenuAssets().getSkin();
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(stage);
        rebuildStage();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cloudLogic(delta);

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
    public void hide() {
        super.hide();
        stage.dispose();
    }

    private void cloudLogic(float delta) {
        if(clouds.getX() >= 0.0f) {
            cloudsSpeed = -1 * ScreensConstants.SPEED_MENU_CLOUDS;
        } else if(clouds.getX() + clouds.getPrefWidth() <= stage.getViewport().getWorldWidth()) {
            cloudsSpeed = ScreensConstants.SPEED_MENU_CLOUDS;
        }
        clouds.setPosition(clouds.getX() + cloudsSpeed * delta, clouds.getY());
    }

    private void rebuildStage() {
        Group backgroundLayer = buildBackgroundLayer();
        Table logoLayer = buildLogoLayer();
        Table buttonLayer = buildButtonLayer();

        stage.clear();
        Stack stack = new Stack();
        stack.setFillParent(true);
        stage.addActor(stack);

        stack.add(backgroundLayer);

        Table ui = new Table();
        stack.add(ui);
        ui.add(logoLayer).padBottom(30.0f).row();
        ui.add(buttonLayer);
    }

    private Group buildBackgroundLayer() {
        Group layer = new Group();

        Color skyColor = skin.get("sky", Color.class);
        sky = new ColorRectangleActor(shapeRenderer, skyColor, new Rectangle(0.0f, 0.0f, stage.getViewport().getWorldWidth(), stage.getViewport()
                .getWorldHeight()));

        blueHills = skin.get("blueHills", Image.class);
        blueHills.setPosition(0.0f, 0.0f);
        blueHills.setSize(blueHills.getPrefWidth(),blueHills.getPrefHeight());

        greenHills = skin.get("greenHills", Image.class);
        greenHills.setPosition(0.0f, 0.0f);
        greenHills.setSize(greenHills.getPrefWidth(), greenHills.getPrefHeight());

        clouds = skin.get("clouds", Image.class);
        clouds.setPosition(0.0f,  ScreensConstants.POSITION_MENU_CLOUDS);
        clouds.setSize(clouds.getPrefWidth(), clouds.getPrefHeight());

        layer.addActor(sky);
        layer.addActor(clouds);
        layer.addActor(blueHills);
        layer.addActor(greenHills);
        return layer;
    }

    private Table buildLogoLayer() {
        Table layer = new Table();
        layer.top().center();
        logo = skin.get("logo", Image.class);
        layer.add(logo);
        return layer;
    }

    private Table buildButtonLayer() {
        Table layer = new Table();
        TextButton.TextButtonStyle style = skin.get("default", TextButton.TextButtonStyle.class);
        style.font = Assets.instance.getMainMenuAssets().getFont48();
        style.fontColor = skin.get("white", Color.class);

        //TODO move ALL strings to constants class
        btnStart = new TextButton("PLAY", style);
        btnOptions = new TextButton("OPTIONS", style);
        btnQuit = new TextButton("QUIT", style);

        btnStart.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onPlayClicked();
            }
        });

        layer.add(btnStart).padBottom(30.0f).row();
        layer.add(btnOptions).padBottom(30.0f).row();
        layer.add(btnQuit);
        return layer;
    }

    private void onPlayClicked() {
        ((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen());
    }

    @Override
    public void dispose() {
        stage.dispose();
        super.dispose();
    }
}
