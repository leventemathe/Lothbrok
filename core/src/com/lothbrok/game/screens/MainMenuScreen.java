package com.lothbrok.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

public class MainMenuScreen extends AbstractScreen {

    public static final String TAG = MainMenuScreen.class.getSimpleName();

    private Stage stage;
    private Skin skin;

    private BitmapFont fontBtn;

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

        if(clouds.getX() >= 0.0f) {
            cloudsSpeed = -1 * ScreensConstants.SPEED_MENU_CLOUDS;
        } else if(clouds.getX() + clouds.getPrefWidth() <= stage.getViewport().getWorldWidth()) {
            cloudsSpeed = ScreensConstants.SPEED_MENU_CLOUDS;
        }
        clouds.setPosition(clouds.getX() + cloudsSpeed * delta, clouds.getY());

        stage.act(delta);
        stage.draw();
        batch.begin();
        renderFpsCounter(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void hide() {
        stage.dispose();
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
        //stack.add(logoLayer);

        Table ui = new Table();
        stack.add(ui);
        ui.add(logoLayer).padBottom(30.0f).row();
        ui.add(buttonLayer);
    }

    private Group buildBackgroundLayer() {
        Group layer = new Group();

        Color skyColor = skin.get("sky", Color.class);
        sky = new ColorRectangleActor(skyColor, 0.0f, 0.0f, stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight());

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
        //TODO remove font from folder, set it dynamically
        TextButton.TextButtonStyle style = skin.get("default", TextButton.TextButtonStyle.class);

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
        ((Game)Gdx.app.getApplicationListener()).setScreen(new GameLoadingScreen());
    }

    private void renderFpsCounter (SpriteBatch batch) {
        float x = 10;
        float y = 100;
        int fps = Gdx.graphics.getFramesPerSecond();
        BitmapFont fpsFont = Assets.instance.getMainMenuAssets().getFont();
        if (fps >= 45) {
            // 45 or more FPS show up in green
            fpsFont.setColor(0, 1, 0, 1);
        } else if (fps >= 30) {
            // 30 or more FPS show up in yellow
            fpsFont.setColor(1, 1, 0, 1);
        } else {
            // less than 30 FPS show up in red
            fpsFont.setColor(1, 0, 0, 1);
        }
        fpsFont.draw(batch, "FPS: " + fps, x, y);
        fpsFont.setColor(1, 1, 1, 1); // white
    }
}
