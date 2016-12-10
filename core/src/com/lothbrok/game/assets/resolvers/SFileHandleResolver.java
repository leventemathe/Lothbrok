package com.lothbrok.game.assets.resolvers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;

public class SFileHandleResolver implements FileHandleResolver {

    @Override
    public FileHandle resolve(String fileName) {
        if(fileName.substring(0,2).equals("s_")) {
            return Gdx.files.internal(fileName);
        }
        return Gdx.files.internal("s_/" + fileName);
    }
}
