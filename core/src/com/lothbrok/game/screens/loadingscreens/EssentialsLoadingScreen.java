package com.lothbrok.game.screens.loadingscreens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.lothbrok.game.assets.Assets;

public class EssentialsLoadingScreen implements Screen {

    private static final String TAG = EssentialsLoadingScreen.class.getSimpleName();

    Assets assets;

    public EssentialsLoadingScreen(Assets assets) {
        this.assets = assets;
    }

    @Override
    public void show() {
        assets.loadEssentials();
    }

    @Override
    public void render(float delta) {
        Gdx.app.debug(TAG, "loading: " + assets.getProgess());
        if(assets.isDoneLoading()) {
            ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenuLoadingScreen(assets));
        }
    }

    @Override
    public void resize(int width, int height) {

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

    }
}
