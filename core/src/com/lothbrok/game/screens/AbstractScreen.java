package com.lothbrok.game.screens;

import com.badlogic.gdx.Screen;
import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.renderers.DebugRenderer;

public abstract class AbstractScreen implements Screen {

    private DebugRenderer debugRenderer;

    @Override
    public void show() {
        debugRenderer = new DebugRenderer();
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
        debugRenderer.dispose();
        Assets.instance.dispose();
    }
}
