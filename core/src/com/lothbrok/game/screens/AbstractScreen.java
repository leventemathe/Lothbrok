package com.lothbrok.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.lothbrok.game.assets.Assets;

public abstract class AbstractScreen implements Screen {
    public abstract void resize(int width, int height);
    public abstract void hide();
    public abstract void pause();

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    //TODO after done with Assets, check these init/dispose things
    @Override
    public void resume() {
        Assets.instance.init(new AssetManager());
    }

    @Override
    public void dispose() {
        Assets.instance.dispose();
    }
}
