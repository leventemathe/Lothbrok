package com.lothbrok.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.lothbrok.game.assets.Assets;

public class MainMenuLoadingScreen extends AbstractScreen {

    public static final String TAG = MainMenuLoadingScreen.class.getSimpleName();

    @Override
    public void show() {
        Gdx.app.log(TAG, "show");
        Assets.instance.loadMainMenuAssets();
    }

    @Override
    public void render(float deltaTime) {
        Gdx.app.debug(TAG, "loading: " + Assets.instance.getProgess());
        if(Assets.instance.isDoneLoading()) {
            ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void hide() {

    }
}
