package com.lothbrok.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lothbrok.game.assets.Assets;

public abstract class AbstractScreen implements Screen {

    private static final String TAG = AbstractScreen.class.getSimpleName();

    protected Assets assets;

    protected SpriteBatch spriteBatch;
    protected ShapeRenderer shapeRenderer;

    public AbstractScreen(Assets assets) {
        this.assets = assets;
    }

    @Override
    public void show() {
        this.spriteBatch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
    }
}
