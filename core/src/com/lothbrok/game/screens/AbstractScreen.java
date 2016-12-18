package com.lothbrok.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.renderers.DebugRenderer;

public abstract class AbstractScreen implements Screen {

    private static final String TAG = AbstractScreen.class.getSimpleName();

    protected Assets assets;

    protected SpriteBatch spriteBatch;
    protected ShapeRenderer shapeRenderer;

    private DebugRenderer debugRenderer;

    public AbstractScreen(Assets assets) {
        this.assets = assets;
    }

    @Override
    public void show() {
        this.spriteBatch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        debugRenderer = new DebugRenderer(spriteBatch, assets);
    }

    @Override
    public void render(float delta) {
        debugRenderer.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        debugRenderer.resize(width, height);

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
