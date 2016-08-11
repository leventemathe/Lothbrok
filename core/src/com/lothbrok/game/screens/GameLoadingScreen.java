package com.lothbrok.game.screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.lothbrok.game.assets.Assets;

public class GameLoadingScreen extends AbstractScreen {
    public static final String TAG = GameLoadingScreen.class.getSimpleName();

    @Override
    public void render(float deltaTime) {
        Gdx.app.debug(TAG, "loading: " + Assets.instance.getProgess());
        if(Assets.instance.isDoneLoading()) {
            ((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen());
        }
        //TODO draw loading screen
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        Assets.instance.loadPlayerAssets();
        Assets.instance.loadMap(1);
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }
}
