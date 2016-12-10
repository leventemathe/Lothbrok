package com.lothbrok.game.screens.loadingscreens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.constants.Resolution;

public class EssentialsLoadingScreen implements Screen {

    public enum Size {
        XL(3840, 2160),
        L(2560, 1440),
        M(1920, 1080),
        S(1280, 720);

        public final int width;
        public final int height;

        Size(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }

    public static Size size;

    private static final String TAG = EssentialsLoadingScreen.class.getSimpleName();

    private Assets assets;

    public EssentialsLoadingScreen(Assets assets) {
        this.assets = assets;
    }

    @Override
    public void show() {
        setSize();
        Resolution.instance.init(size);
        this.assets.init();
        assets.loadEssentials();
    }

    private void setSize() {
        int width = Gdx.graphics.getWidth();

        if(width > Size.L.width) {
            size = Size.XL;
        } else if(width > Size.M.width) {
            size = Size.L;
        } else if(width > Size.S.width) {
            size = Size.M;
        } else {
            size = Size.S;
        }
        //size = Size.XL;
        Gdx.app.debug(TAG, size.toString());
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
