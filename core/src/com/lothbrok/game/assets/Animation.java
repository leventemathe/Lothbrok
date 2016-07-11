package com.lothbrok.game.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.Loader;
import com.brashmonkey.spriter.Player;
import com.brashmonkey.spriter.SCMLReader;
import com.lothbrok.game.spriter.LibGdxDrawer;
import com.lothbrok.game.spriter.LibGdxLoader;

import java.util.HashMap;
import java.util.Map;

public class Animation implements Disposable {
    //TODO add error handling
    //TODO dispose
    private SCMLReader reader;
    private Data data;

    private Map<String, Player> players;

    private Loader loader;
    private Drawer drawer;

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    public Animation(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        this.batch = batch;
        this.shapeRenderer = shapeRenderer;

        this.players = new HashMap<String, Player>();
    }

    public void load(String path) {
        //laod scml
        FileHandle handle = Gdx.files.internal(path);
        this.reader = new SCMLReader(handle.read());
        this.data = reader.getData();

        //laod images
        this.loader = new LibGdxLoader(data);
        this.loader.load(handle.file());

        //prepare for drawing
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();

        this.drawer = new LibGdxDrawer(loader, batch, shapeRenderer);
    }

    public void addEntity(String entity) {
        //TODO add error handling
        this.players.put(entity, new Player((data.getEntity(0))));
    }

    public void playEntity(String entity) {
        Player playMe = players.get(entity);
        playMe.update();
        //batch.begin();
        drawer.draw(playMe);
        //batch.end();
    }

    @Override
    public void dispose() {

    }
}
