package com.lothbrok.game.assets.entities.fonts;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

public class FontLoader extends SynchronousAssetLoader<Font, FontLoader.FontLoaderParameter> {

    public FontLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public Font load(AssetManager assetManager, String fileName, FileHandle file, FontLoaderParameter parameter) {
        return new Font(file);
    }

    public Font loadSync(AssetManager manager, String fileName, FileHandle file, FontLoaderParameter parameter) {
        return null;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, FontLoaderParameter parameter) {
        return null;
    }

    static public class FontLoaderParameter extends AssetLoaderParameters<Font> {}
}
