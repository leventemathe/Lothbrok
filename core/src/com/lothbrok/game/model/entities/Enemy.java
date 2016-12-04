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

    private MovementComponent movementComponent;
    private BoundingBoxComponent boundingBoxComponent;
    private TiledCollisionComponent tiledCollisionComponent;
    private AttackingComponent attackingComponent;

    private float travelled = 0f;
    private final float MAX_TRAVELLED = 3f;

    public Enemy(Vector2 position, Map map) {
        super(position);
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
            }
        } else if(direction == Direction.RIGHT) {
            if(tiledCollisionComponent.doesRightPlatformExist() && !tiledCollisionComponent.isRightColliding()) {
                movementComponent.moveRight(deltaTime);
            } else {
                direction = Direction.LEFT;
            }
        }

        float movement = position.x - prevPosition.x;
        if(movement != 0f) {
            travelled += movement;
            if (travelled >= MAX_TRAVELLED) {
                position.x -= movement;
                direction = Direction.LEFT;
            } else if (travelled <= -MAX_TRAVELLED) {
                position.x += movement;
                direction = Direction.RIGHT;
            }
        }
    }

    public void updateBoundingBox(Rectangle body, Rectangle foot) {
        boundingBoxComponent.setBoundingBox(body);
        tiledCollisionComponent.setFootSensor(foot);
    }
}
