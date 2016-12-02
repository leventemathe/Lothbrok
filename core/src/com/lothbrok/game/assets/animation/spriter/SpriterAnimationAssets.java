package com.lothbrok.game.assets.animation.spriter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Disposable;
import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.Loader;
import com.brashmonkey.spriter.SCMLReader;

public class SpriterAnimationAssets implements Disposable {

    private FileHandle fileHandle;

    private SCMLReader scmlReader;
    private Data scmlData;

    private Loader<Sprite> spriteLoader;

    public void loadScml(String path) {
        this.fileHandle = Gdx.files.internal(path);
        this.scmlReader = new SCMLReader(fileHandle.read());
        this.scmlData = scmlReader.getData();
    }

    public void loadImages() {
        this.spriteLoader = new LibGdxLoader(this.scmlData);
        this.spriteLoader.load(fileHandle.file());
    }

    public Loader<Sprite> getSpriteLoader() {
        return spriteLoader;
    }

    public Data getScmlData() {
        return scmlData;
    }

    //TODO call this somewhere
    @Override
    public void dispose() {
        spriteLoader.dispose();
    }
}
