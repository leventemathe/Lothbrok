package com.lothbrok.game.model;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class GameModel {

    private TiledMap map;
    private World world;

    public GameModel(TiledMap map) {
        this.map = map;
        this.world = new World(new Vector2(0f, -9.8f), true);
    }

    public void update(float deltaTime) {
        world.step(deltaTime, 6, 2);
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }
}
