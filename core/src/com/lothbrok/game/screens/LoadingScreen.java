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
            Gdx.app.log(TAG, "yay");
            Assets.instance.getAnimation().addEntity("player");
            game.setScreen(new GameScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        Assets.instance.loadAnimation();
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }
}
