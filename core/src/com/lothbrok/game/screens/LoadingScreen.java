package com.lothbrok.game.screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.lothbrok.game.assets.Assets;

public class LoadingScreen extends AbstractScreen {
    public static final String TAG = LoadingScreen.class.toString();

    public LoadingScreen(Game game) {
        super(game);
    }

    @Override
    public void render(float deltaTime) {
        if(Assets.instance.assetManager.update()) {
            game.setScreen(new GameScreen(game));
        }
        //TODO draw loading screen
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        Assets.instance.loadPlayerAssets();
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }
}
