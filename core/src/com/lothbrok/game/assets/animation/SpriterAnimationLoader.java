package com.lothbrok.game.assets.animation;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.lothbrok.game.assets.animation.spriter.SpriterAnimation;

public class SpriterAnimationLoader extends AsynchronousAssetLoader<SpriterAnimation, SpriterAnimationLoader.SpriterAnimationParameter> {
    public  SpriterAnimationLoader() {
        super(new InternalFileHandleResolver());
    }

    public SpriterAnimationLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    SpriterAnimation animation;

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, SpriterAnimationParameter parameter) {
        animation = null;
        animation = new SpriterAnimation();

        animation.loadScml(fileName);
    }

    @Override
    //TODO move the pixmap loading parts of LibGdxLoader to loadAsync, load only Texture(Region) here
    public SpriterAnimation loadSync(AssetManager manager, String fileName, FileHandle file, SpriterAnimationParameter parameter) {
        //TODO move pixmap parts of loader to async, call texture parts here
        this.animation.loadImages();

        SpriterAnimation animation = this.animation;
        this.animation = null;
        return animation;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, SpriterAnimationParameter parameter) {
        return null;
    }

    static public class SpriterAnimationParameter extends AssetLoaderParameters<SpriterAnimation> {
    }
}
