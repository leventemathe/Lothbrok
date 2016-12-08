package com.lothbrok.game.screens.loadingscreens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.screens.AbstractScreen;
import com.lothbrok.game.screens.GameScreen;

public class MainMenuLoadingScreen extends AbstractScreen {

    public static final String TAG = MainMenuLoadingScreen.class.getSimpleName();

    @Override
    public void show() {
        Gdx.app.log(TAG, "show");
        Assets.instance.loadMainMenuAssets();
        Assets.instance.loadPlayerAnimationAssets();
        Assets.instance.loadEnemyAnimationAssets();
        Assets.instance.loadMap(1);
        Assets.instance.loadMobileControlsSkin();
        Assets.instance.loadUI();
        Assets.instance.loadMusicAssets();
        Assets.instance.loadSoundAssets();
    }

    @Override
    public void render(float deltaTime) {
        Gdx.app.debug(TAG, "loading: " + Assets.instance.getProgess());
        if(Assets.instance.isDoneLoading()) {
            ((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen());
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
