package com.lothbrok.game.screens.loadingscreens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.lothbrok.game.assets.Assets;

public class EssentialsLoadingScreen implements Screen {

    public enum Size {
        XL(3840),
        L(2560),
        M(1920),
        S(1280);

        private int value;

        Size(int value){
            this.value = value;
        }
    }

    public static Size size;

    private static final String TAG = EssentialsLoadingScreen.class.getSimpleName();

    private Assets assets;

    public EssentialsLoadingScreen(Assets assets) {
        this.assets = assets;
        this.assets.clear();
    }

    @Override
    public void show() {
        assets.loadEssentials();
        setSize();
    }

    private void setSize() {
        int width = Gdx.graphics.getWidth();

        if(width > Size.XL.value) {
            size = Size.XL;
        } else if(width > Size.L.value) {
            size = Size.L;
        } else if(width > Size.M.value) {
            size = Size.M;
        } else {
            size = Size.S;
        }
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
