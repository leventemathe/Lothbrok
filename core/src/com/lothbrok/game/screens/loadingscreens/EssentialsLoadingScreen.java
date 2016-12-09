package com.lothbrok.game.screens.loadingscreens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.lothbrok.game.assets.Assets;

public class EssentialsLoadingScreen implements Screen {

    private static final String TAG = EssentialsLoadingScreen.class.getSimpleName();

    @Override
    public void show() {
        Assets.instance.loadRalewayLightFont();
        Assets.instance.loadLoadingAnimationAssets();
    }

    @Override
    public void render(float delta) {
        Gdx.app.debug(TAG, "loading: " + Assets.instance.getProgess());
        if(Assets.instance.isDoneLoading()) {
            ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenuLoadingScreen());
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
        Assets.instance.dispose();
    }
}
