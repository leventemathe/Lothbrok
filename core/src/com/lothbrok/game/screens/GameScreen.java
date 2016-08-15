package com.lothbrok.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.lothbrok.game.screens.renderers.DebugRenderer;
import com.lothbrok.game.screens.renderers.GameRenderer;

public class GameScreen extends AbstractScreen {

    public static final String TAG = GameScreen.class.getSimpleName();

    private GameRenderer gameRenderer;
    private DebugRenderer debugRenderer;

    @Override
    public void show() {
        gameRenderer = new GameRenderer();
        debugRenderer = new DebugRenderer();
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameRenderer.render(deltaTime);
        debugRenderer.render(deltaTime);
    }

    @Override
    public void resize(int width, int height) {
        gameRenderer.resize(width, height);
        debugRenderer.resize(width, height);
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
        gameRenderer.dispose();
        debugRenderer.dispose();
        super.dispose();
    }
}
