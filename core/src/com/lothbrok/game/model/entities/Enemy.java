package com.lothbrok.game.model.entities;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.lothbrok.game.model.entities.components.AttackingComponent;
import com.lothbrok.game.model.entities.components.BoundingBoxComponent;
import com.lothbrok.game.model.entities.components.MovementComponent;
import com.lothbrok.game.model.entities.components.TiledCollisionComponent;

public class Enemy extends Entity {

    private static final String TAG = Enemy.class.getSimpleName();
    private MovementComponent movementComponent;
    private BoundingBoxComponent boundingBoxComponent;
    private TiledCollisionComponent tiledCollisionComponent;
    private AttackingComponent attackingComponent;

    private float origin;
    private float distanceFromOrigin = 0f;
    public final float RADIUS = 3f;
    public final float ATTACK_RADIUS = 0.5f;

    public Enemy(Vector2 position, Map map) {
        super(position);
        this.origin = position.x;
        setupComponents(map);
    }

    private void setupComponents(Map map) {
        movementComponent = new MovementComponent(this, 1.01f, 2f, 1f);
        boundingBoxComponent = new BoundingBoxComponent(this);
        tiledCollisionComponent = new TiledCollisionComponent(this, (TiledMap)map, boundingBoxComponent);
        attackingComponent = new AttackingComponent(this);
    }

    public void move(float deltaTime) {
        if(direction == Direction.LEFT) {
            if(tiledCollisionComponent.doesLeftPlatformExist() && !tiledCollisionComponent.isLeftColliding()) {
                movementComponent.moveLeft(deltaTime);
            } else {
                direction = Direction.RIGHT;
                movementComponent.resetAcceleration();
            }
        } else if(direction == Direction.RIGHT) {
            if(tiledCollisionComponent.doesRightPlatformExist() && !tiledCollisionComponent.isRightColliding()) {
                movementComponent.moveRight(deltaTime);
            } else {
                direction = Direction.LEFT;
                movementComponent.resetAcceleration();
            }
        }

        distanceFromOrigin = position.x - origin;
        if (distanceFromOrigin >= RADIUS) {
            direction = Direction.LEFT;
            movementComponent.resetAcceleration();
        } else if (distanceFromOrigin <= -RADIUS) {
            direction = Direction.RIGHT;
            movementComponent.resetAcceleration();
        }
        //Gdx.app.debug(TAG, "speed: " + movementComponent.getSpeed());
    }

    public void moveTo(Vector2 position, float deltaTime) {
        float direction = position.x - this.position.x;
        if(direction < 0f) {
            if(tiledCollisionComponent.doesLeftPlatformExist() && !tiledCollisionComponent.isLeftColliding()) {
                movementComponent.moveTo(position, deltaTime);
            }
        } else if(direction > 0f) {
            if(tiledCollisionComponent.doesRightPlatformExist() && !tiledCollisionComponent.isRightColliding()) {
                movementComponent.moveTo(position, deltaTime);
            }
        }
        //Gdx.app.debug(TAG, "speed: " + movementComponent.getSpeed());
    }

    public void updateBoundingBox(Rectangle body, Rectangle foot) {
        boundingBoxComponent.setBoundingBox(body);
        tiledCollisionComponent.setFootSensor(foot);
    }
}
