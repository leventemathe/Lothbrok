package com.lothbrok.game.model;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.lothbrok.game.model.entities.Player;

public class GameModel {

    private Player player;
    private TiledMap map;
    private World world;

    public GameModel(Player player, TiledMap map) {
        this.player = player;
        this.map = map;
        this.world = new World(new Vector2(0f, -9.8f), true);
    }

    public void update(float deltaTime) {
        player.update(deltaTime);
        world.step(deltaTime, 6, 2);
    }

    public Player getPlayer() {
        return player;
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }
}
