package com.lothbrok.game.assets.animation.spriter;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

public class SpriterAnimationLoaderS extends SynchronousAssetLoader {

    public SpriterAnimationLoaderS(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public Object load(AssetManager assetManager, String fileName, FileHandle file, AssetLoaderParameters parameter) {
        return null;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, AssetLoaderParameters parameter) {
        return null;
    }
}
