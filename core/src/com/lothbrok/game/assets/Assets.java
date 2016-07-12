package com.lothbrok.game.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.PixmapLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Disposable;
import com.brashmonkey.spriter.Loader;
import com.lothbrok.game.assets.animation.SpriterAnimation;
import com.lothbrok.game.assets.animation.SpriterAnimationLoader;
import com.lothbrok.game.assets.entities.PlayerAssets;

public class Assets implements Disposable {
    public static final String TAG = Assets.class.getName();

    public static final Assets instance = new Assets();

    public AssetManager assetManager;

    private Assets() {}

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.setErrorListener(new AssetsErrorListenerImplementation());

        assetManager.setLoader(SpriterAnimation.class, new SpriterAnimationLoader(new InternalFileHandleResolver()));
    }

    public void loadAnimation() {
        assetManager.load(AssetsConstants.PLAYER_ANIMATION_PATH, SpriterAnimation.class);
    }

    public SpriterAnimation getAnimation() {
        return assetManager.get(AssetsConstants.PLAYER_ANIMATION_PATH);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        //playerAssets.dispose(); //TODO playerAssets could be null
    }
}
