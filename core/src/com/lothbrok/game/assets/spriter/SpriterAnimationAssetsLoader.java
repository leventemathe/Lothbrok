package com.lothbrok.game.assets.spriter;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

public class SpriterAnimationAssetsLoader extends AsynchronousAssetLoader<SpriterAnimationAssets,
        SpriterAnimationAssetsLoader.SpriterAnimationAssetsParameter> {

    SpriterAnimationAssets animationAssets;

    public SpriterAnimationAssetsLoader() {
        super(new InternalFileHandleResolver());
    }

    public SpriterAnimationAssetsLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, SpriterAnimationAssetsParameter parameter) {
        animationAssets = null;
        animationAssets = new SpriterAnimationAssets();

        animationAssets.loadScml(resolve(fileName).toString());
    }

    @Override
    public SpriterAnimationAssets loadSync(AssetManager manager, String fileName, FileHandle file, SpriterAnimationAssetsParameter parameter) {
        this.animationAssets.loadImages();

        SpriterAnimationAssets animationAssets = this.animationAssets;
        this.animationAssets = null;
        return animationAssets;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, SpriterAnimationAssetsParameter parameter) {
        return null;
    }

    static public class SpriterAnimationAssetsParameter extends AssetLoaderParameters<SpriterAnimationAssets> {
    }
}
