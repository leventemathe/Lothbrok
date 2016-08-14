package com.lothbrok.game.assets.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.Loader;
import com.brashmonkey.spriter.Player;
import com.brashmonkey.spriter.SCMLReader;
import com.lothbrok.game.assets.animation.spriter.ScalingDrawer;

import java.util.HashMap;
import java.util.Map;

public class SpriterAnimation {
    //TODO add error handling? if its necessary, maybe assets error handliong is enough
    private FileHandle handle;

    private SCMLReader reader;
    private Data data;

    private Map<String, Player> players;

    private Loader<Sprite> loader;
    private ScalingDrawer drawer;

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

    public void addEntity(String entity) {
        this.players.put(entity, new Player((data.getEntity(entity))));
    }

    //TODO maybe move this to constructor, like unitscale in tiled
    public void scale(float scale) {
        for(Map.Entry<String, Player> entry : players.entrySet()) {
            Player player = entry.getValue();
            player.scale(scale);
            drawer.scale(player, scale);
        }
    }

    public void play(float deltaTime, SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, String entity, String animation) {
        drawer.setSpriteBatch(spriteBatch);
        drawer.setShapeRenderer(shapeRenderer);
        Player playMe = players.get(entity);
        playMe.setAnimation(animation);

        int framesToPlayPerSecond = 15*60; // default is 15 in trixt0r, 60fps is assumed -> 15*60
        playMe.speed = Math.round(framesToPlayPerSecond * deltaTime);

        playMe.update();
        drawer.draw(playMe);
    }

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

    public void setDrawer(ScalingDrawer drawer) {
        this.drawer = drawer;
    }

    public FileHandle getHandle() {
        return handle;
    }
}
