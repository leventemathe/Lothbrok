package com.lothbrok.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.lothbrok.game.constants.ScreensConstants;
import com.lothbrok.game.screens.utils.ColorRectangleActor;

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
    private TextButton btnQuit;

    @Override
    public void show() {
        super.show();
        stage = new Stage(new FitViewport(ScreensConstants.VIEWPORT_MENU_WIDTH, ScreensConstants.VIEWPORT_MENU_HEIGHT));
        Gdx.input.setInputProcessor(stage);
    }

    private void rebuildStage() {
        stage.clear();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
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

    private void onPlayClicked() {
        ((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen());
    }

    @Override
    public void dispose() {
        stage.dispose();
        super.dispose();
    }
}
