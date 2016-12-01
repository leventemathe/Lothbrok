package com.lothbrok.game.model;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.lothbrok.game.model.box2d.Box2DCollisionFromTiled;
import com.lothbrok.game.model.box2d.util.Box2DUtils;
import com.lothbrok.game.model.entities.MovingEntity;
import com.lothbrok.game.model.entities.Player;

import java.util.List;


public class GameModel {

    private static final String TAG = GameModel.class.getSimpleName();

    private World world;

    private TiledMap map;
    private List<Body> box2DmapCollisons;
    private ParallaxBackground parallaxBackground;

    private Player player;

    public GameModel(TiledMap map) {
        this.world = new World(new Vector2(0f, -0.1f), true);
        this.world.setContactListener(new MyContactListener());
        this.map = map;
        this.box2DmapCollisons = Box2DCollisionFromTiled.build(map, world);

        float playerX = Box2DUtils.toWorld((float)map.getLayers().get("spawn").getObjects().get("playerSpawn").getProperties().get("x"));
        float playerY = Box2DUtils.toWorld((float)map.getLayers().get("spawn").getObjects().get("playerSpawn").getProperties().get("y"));
        this.player = new Player(new Vector2(playerX, playerY), map);

        this.parallaxBackground = new ParallaxBackground(map);
    }

    public void update(float deltaTime) {
        //TODO do the accumulator method
        world.step(1f/60f, 6, 2);
        if(player.isActuallyMoving()) {
            if (player.getMovingState().equals(MovingEntity.MovingState.RIGHT)) {
                parallaxBackground.update(player.getSpeed(), deltaTime);
            } else if (player.getMovingState().equals(MovingEntity.MovingState.LEFT)) {
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
}
