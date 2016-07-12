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
import com.lothbrok.game.spriter.LibGdxDrawer;
import com.lothbrok.game.spriter.LibGdxLoader;

import java.util.HashMap;
import java.util.Map;

public class SpriterAnimation extends AbstractAnimation {
    //TODO add error handling
    private SCMLReader reader;
    private Data data;

    private Map<String, Player> players;

    private Loader<Sprite> loader;
    private Drawer drawer;

    public SpriterAnimation(String path) {
        this.players = new HashMap<String, Player>();
        load(path);
    }

    private void load(String path) {
        //laod scml
        FileHandle handle = Gdx.files.internal(path);
        this.reader = new SCMLReader(handle.read());
        this.data = reader.getData();

        //laod images
        this.loader = new LibGdxLoader(data);
        this.loader.load(handle.file());

        this.drawer = new LibGdxDrawer(loader);
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
}
