package com.lothbrok.game.assets.animation;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.lothbrok.game.assets.animation.spriter.LibGdxDrawer;
import com.lothbrok.game.assets.animation.spriter.LibGdxLoader;

public class SpriterAnimationLoader extends AsynchronousAssetLoader<SpriterAnimation, SpriterAnimationLoader.SpriterAnimationParameter> {
    public SpriterAnimationLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    SpriterAnimation animation;

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, SpriterAnimationParameter parameter) {
        animation = null;
        animation = new SpriterAnimation(fileName);
    }

    @Override
    public SpriterAnimation loadSync(AssetManager manager, String fileName, FileHandle file, SpriterAnimationParameter parameter) {
        LibGdxLoader loader = new LibGdxLoader(animation.getData());
        loader.load(animation.getHandle().file());
        LibGdxDrawer drawer = new LibGdxDrawer(loader);
        this.animation.setLoader(loader);
        this.animation.setDrawer(drawer);

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
