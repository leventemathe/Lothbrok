package com.lothbrok.game.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;

public class AssetsErrorListenerImplementation implements AssetErrorListener {
    public static final String TAG = AssetsErrorListenerImplementation.class.getName();

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset '" +
                asset.fileName + "'", throwable);
        //TODO add popup window, proper error handling
    }
}
