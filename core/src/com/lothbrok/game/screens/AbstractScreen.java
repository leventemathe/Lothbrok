package com.lothbrok.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public abstract class AbstractScreen implements Screen {
    protected Game game;

    public AbstractScreen(Game game) {
        this.game = game;
    }

    public abstract void render(float deltaTime);
    public abstract void resize(int width, int height);
    public abstract void show();
    public abstract void hide();
    public abstract void pause();

    public void resume() {
        //Assets.instance.init(new AssetManager());
    }

    public void dispose() {
        //Assets.instance.dispose();
    }
}
