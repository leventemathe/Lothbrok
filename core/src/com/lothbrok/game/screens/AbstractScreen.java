package com.lothbrok.game.screens;

import com.badlogic.gdx.Screen;
import com.lothbrok.game.assets.Assets;

public abstract class AbstractScreen implements Screen {

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    //TODO after done with Assets, check these init/dispose things
    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        Assets.instance.dispose();
    }
}
