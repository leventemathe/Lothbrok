package com.lothbrok.game.model;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.lothbrok.game.model.box2d.Box2DCollisionFromTiled;
import com.lothbrok.game.model.box2d.util.TiledUtils;
import com.lothbrok.game.model.entities.Enemy;
import com.lothbrok.game.model.entities.Entity;
import com.lothbrok.game.model.entities.Player;

import java.util.LinkedList;
import java.util.List;


public class GameModel {

    private static final String TAG = GameModel.class.getSimpleName();

    private World world;

    private TiledMap map;
    private ParallaxBackground parallaxBackground;

    private Player player;
    private List<Enemy> enemies;

    public GameModel(TiledMap map) {
        setupWorld(map);
        setupPlayer(map);
        setupEnemies();
    }

    private void setupWorld(TiledMap map) {
        this.world = new World(new Vector2(0f, -0.1f), true);
        this.map = map;
        Box2DCollisionFromTiled.build(map, world);
        this.parallaxBackground = new ParallaxBackground(map);
    }

    private void setupPlayer(TiledMap map) {
        Vector2 pos = new Vector2();
        pos.x = TiledUtils.toWorld((float)map.getLayers().get("player_spawn").getObjects().get(0).getProperties().get("x"));
        pos.y = TiledUtils.toWorld((float)map.getLayers().get("player_spawn").getObjects().get(0).getProperties().get("y"));
        this.player = new Player(pos, map);
    }

    private void setupEnemies() {
        enemies = new LinkedList<>();
        MapObjects mapObjects = map.getLayers().get("enemies_spawn").getObjects();
        for(int i = 0; i < mapObjects.getCount(); i++) {
            Vector2 pos = new Vector2();
            pos.x = TiledUtils.toWorld((float)mapObjects.get(i).getProperties().get("x"));
            pos.y = TiledUtils.toWorld((float)mapObjects.get(i).getProperties().get("y"));
            Enemy enemy = new Enemy(pos, map);
            enemies.add(enemy);
        }
    }

    public void update(float deltaTime) {
        //TODO do the accumulator method
        world.step(1f/60f, 6, 2);
        if(player.isActuallyMoving()) {
            if (player.direction == Entity.Direction.RIGHT) {
                parallaxBackground.update(player.getSpeed(), deltaTime);
            } else if (player.direction == Entity.Direction.LEFT) {
                parallaxBackground.update(-player.getSpeed(), deltaTime);
            }
        }
        player.update(deltaTime);
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}
