package com.lothbrok.game.assets.resolvers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;

public class SFileHandleResolver implements FileHandleResolver {

    @Override
    public FileHandle resolve(String fileName) {
        if(fileName.substring(0,1).equals("s")) {
            return Gdx.files.internal(fileName);
        }
        return Gdx.files.internal("s/" + fileName);
    }
}
