package com.lothbrok.game.assets.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class AssetsErrorListenerImplementation implements AssetErrorListener {
    public static final String TAG = AssetsErrorListenerImplementation.class.getName();

    private static Stage stage = new Stage();

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset '" +
                asset.fileName + "'", throwable);
        //TODO add popup window, proper error handling
        //TODO Gdx debug log levels
    }
}
