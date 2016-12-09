package com.lothbrok.game.model;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.lothbrok.game.assets.entities.animation.EnemyAnimation;
import com.lothbrok.game.assets.entities.animation.PlayerAnimation;
import com.lothbrok.game.model.box2d.Box2DCollisionFromTiled;
import com.lothbrok.game.model.entities.Enemy;
import com.lothbrok.game.model.entities.Entity;
import com.lothbrok.game.model.entities.Player;
import com.lothbrok.game.model.entities.Treasure;
import com.lothbrok.game.model.tiled.ParallaxBackground;
import com.lothbrok.game.model.tiled.TiledUtils;
import com.lothbrok.game.renderers.GameRenderer;

import java.util.Iterator;


public class GameModel {

    private static final String TAG = GameModel.class.getSimpleName();

    private World world;

    private TiledMap map;
    private Array<Treasure> treasures;
    private ParallaxBackground parallaxBackground;

    private Player player;
    private Array<Enemy> enemies;
    private Collision collision;

    public GameModel(TiledMap map) {
        setupWorld(map);
        setupPlayer(map);
        setupEnemies();
        collision = new Collision();
    }

    private void setupWorld(TiledMap map) {
        this.world = new World(new Vector2(0f, -9.8f), true);
        this.map = map;
        Box2DCollisionFromTiled.build(map, world);
        this.parallaxBackground = new com.lothbrok.game.model.tiled.ParallaxBackground(map);
        treasures = new Array<>();
    }

    private void setupPlayer(TiledMap map) {
        Vector2 pos = new Vector2();
        pos.x = TiledUtils.toWorld((float)map.getLayers().get("player_spawn").getObjects().get(0).getProperties().get("x"));
        pos.y = TiledUtils.toWorld((float)map.getLayers().get("player_spawn").getObjects().get(0).getProperties().get("y"));
        this.player = new Player(pos, map);
    }

    private void setupEnemies() {
        enemies = new Array<>();
        MapObjects mapObjects = map.getLayers().get("enemies_spawn").getObjects();
        for(int i = 0; i < mapObjects.getCount(); i++) {
            Vector2 pos = new Vector2();
            pos.x = TiledUtils.toWorld((float)mapObjects.get(i).getProperties().get("x"));
            pos.y = TiledUtils.toWorld((float)mapObjects.get(i).getProperties().get("y"));
            Enemy enemy = new Enemy(pos, map);
            enemies.add(enemy);
        }
    }

    //TODO get the renderer out of here
    public void update(float deltaTime, GameRenderer gameRenderer) {
        //TODO do fix timestep
        world.step(deltaTime, 6, 2);
        updateParallax(deltaTime);
        updatePlayer(deltaTime, gameRenderer.getPlayerAnimation());
        updateEnemies(deltaTime, gameRenderer.getEnemyAnimations());
        collision.update(player);
    }

    private void updateParallax(float deltaTime) {
        if(player != null && player.isActuallyMoving()) {
            if (player.direction == Entity.Direction.RIGHT) {
                parallaxBackground.update(player.getSpeed(), deltaTime);
            } else if (player.direction == Entity.Direction.LEFT) {
                parallaxBackground.update(-player.getSpeed(), deltaTime);
            }
        }
    }

    private void updatePlayer(float deltaTime, PlayerAnimation anim) {
        if(player == null || player.lifeState == Entity.LifeState.DEAD) {
            player = null;
            return;
        }
        if(player.lifeState != Entity.LifeState.DYING) {
            Rectangle body = anim.getBodyBoundingBox();
            Rectangle foot = anim.getFootSensor();
            Rectangle weapon = anim.getWeaponBoundingBox();
            player.updateBoundingBoxes(body, foot, weapon);
        }
        player.update(deltaTime);
    }

    private void updateEnemies(float deltaTime, ObjectMap<Enemy, EnemyAnimation> anims) {
        Iterator<Enemy> it = enemies.iterator();
        while(it.hasNext()) {
            Enemy enemy = it.next();
            if(enemy.lifeState == Entity.LifeState.DEAD) {
                it.remove();
                collision.removeActiveEnemy(enemy);
                continue;
            }
            if(enemy.lifeState != Entity.LifeState.DYING) {
                Rectangle body = anims.get(enemy).getBodyBoundingBox();
                Rectangle foot = anims.get(enemy).getFootSensor();
                Rectangle weapon = anims.get(enemy).getWeaponBoundingBox();
                enemy.updateBoundingBox(body, foot, weapon);

                if(enemy.isActive()) {
                    collision.addActiveEnemey(enemy);
                } else {
                    collision.removeActiveEnemy(enemy);
                }
            }
            enemy.update(deltaTime);
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

    public Array<Enemy> getEnemies() {
        return enemies;
    }
}
