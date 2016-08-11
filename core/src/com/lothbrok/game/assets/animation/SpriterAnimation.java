package com.lothbrok.game.assets.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.Loader;
import com.brashmonkey.spriter.Player;
import com.brashmonkey.spriter.SCMLReader;
import com.lothbrok.game.assets.animation.spriter.LibGdxDrawer;

import java.util.HashMap;
import java.util.Map;

public class SpriterAnimation extends AbstractAnimation {
    //TODO add error handling? if its necessary, maybe assets error handliong is enough
    private FileHandle handle;

    private SCMLReader reader;
    private Data data;

    private Map<String, Player> players;

    private Loader<Sprite> loader;
    private Drawer drawer;

    public SpriterAnimation(String path) {
        this.players = new HashMap<>();
        this.handle = Gdx.files.internal(path);
        load(path);
    }

    private void load(String path) {
        //laod scml
        this.reader = new SCMLReader(handle.read());
        this.data = reader.getData();
    }

    @Override
    public void addEntity(String entity) {
        this.players.put(entity, new Player((data.getEntity(entity))));
    }

    @Override
    public void play(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, String entity, String animation) {
        ((LibGdxDrawer)drawer).setSpriteBatch(spriteBatch);
        ((LibGdxDrawer)drawer).setShapeRenderer(shapeRenderer);
        Player playMe = players.get(entity);
        playMe.setAnimation(animation);
        playMe.update();
        drawer.draw(playMe);
    }

    @Override
    public void dispose() {
        loader.dispose();
    }

    public Data getData() {
        return data;
    }

    public Loader<Sprite> getLoader() {
        return loader;
    }

    public void setLoader(Loader<Sprite> loader) {
        this.loader = loader;
    }

    public void setDrawer(Drawer drawer) {
        this.drawer = drawer;
    }

    public FileHandle getHandle() {
        return handle;
    }
}
