package com.lothbrok.game.screens.loadingscreens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.assets.spriter.SpriterAnimation;
import com.lothbrok.game.constants.AnimationConstants;
import com.lothbrok.game.constants.Resolution;
import com.lothbrok.game.screens.AbstractScreen;
import com.lothbrok.game.screens.MainMenuScreen;

public class MainMenuLoadingScreen extends AbstractScreen {

    public static final String TAG = MainMenuLoadingScreen.class.getSimpleName();

    private Camera camera;
    private Viewport viewport;

    private SpriterAnimation loadingAnimation;

    public MainMenuLoadingScreen(Assets assets) {
        super(assets);
    }

    @Override
    public void show() {
        super.show();
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(2f, 2f, camera);
        loadingAnimation = new SpriterAnimation(assets.getLoadingAnimationAssets());
        loadingAnimation.setCurrentEntity(AnimationConstants.LOADING_ANIMATION_ENTITY_LOADING);
        loadingAnimation.setPlayAlways(AnimationConstants.LOADING_ANIMATION_LOADING);
        //TODO scale accoridng to size here to
        loadingAnimation.setScale(Resolution.instance.getWorldScale());
        loadingAnimation.setPosition(0f, 0f);

        assets.loadAll();
    }

    @Override
    public void render(float deltaTime) {
        Gdx.app.debug(TAG, "loading: " + assets.getProgess());

        if(assets.isDoneLoading()) {
            ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(assets));
        }

        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        loadingAnimation.update(deltaTime);
        loadingAnimation.render(spriteBatch, shapeRenderer);
        spriteBatch.end();
        super.render(deltaTime);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        super.resize(width, height);
    }
}
