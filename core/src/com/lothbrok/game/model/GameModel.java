package com.lothbrok.game.model;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.lothbrok.game.model.box2d.Box2DCollisionFromTiled;
import com.lothbrok.game.model.entities.Enemy;
import com.lothbrok.game.model.entities.Entity;
import com.lothbrok.game.model.entities.Player;
import com.lothbrok.game.model.entities.Treasure;
import com.lothbrok.game.model.tiled.ParallaxBackground;
import com.lothbrok.game.model.tiled.TiledUtils;

import java.util.LinkedList;
import java.util.List;


public class GameModel {

    private static final String TAG = GameModel.class.getSimpleName();

    private World world;

    private TiledMap map;
    private Array<Treasure> treasures;
    private ParallaxBackground parallaxBackground;

    private Player player;
    private List<Enemy> enemies;
    private Collision collision;

    public GameModel(TiledMap map) {
        setupWorld(map);
        treasures = new Array<>();
        setupPlayer(map);
        setupEnemies();
        collision = new Collision(this);
    }

    private void setupWorld(TiledMap map) {
        this.world = new World(new Vector2(0f, -9.8f), true);
        this.map = map;
        Box2DCollisionFromTiled.build(map, world);
        this.parallaxBackground = new com.lothbrok.game.model.tiled.ParallaxBackground(map);
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
        //TODO do fix timestep
        world.step(deltaTime, 6, 2);
        updateParallax(deltaTime);
        player.update(deltaTime);
        updateEnemies(deltaTime);
        collision.update();
    }

    private void updateParallax(float deltaTime) {
        if(player.isActuallyMoving()) {
            if (player.direction == Entity.Direction.RIGHT) {
                parallaxBackground.update(player.getSpeed(), deltaTime);
            } else if (player.direction == Entity.Direction.LEFT) {
                parallaxBackground.update(-player.getSpeed(), deltaTime);
            }
        }
    }

    private void updateEnemies(float deltaTime) {
        for(int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            enemy.update(deltaTime);
            if(enemy.isActive()) {
                collision.addActiveEnemey(enemy);
            } else {
                collision.removeActiveEnemy(enemy);
            }
        }
    }

    public Treasure spawnLostTreasure(Vector2 position) {
        Shape shape = new CircleShape();
        shape.setRadius(0.1f);
        float dirX = player.direction == Entity.Direction.LEFT ? 1f : -1f;
        Treasure treasure = new Treasure(position, world, shape);
        treasures.add(treasure);
        treasure.getBody().applyForceToCenter(dirX*5f, 5f, true);
        shape.dispose();
        return treasure;
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    public Array<Treasure> getTreasures() {
        return treasures;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}
