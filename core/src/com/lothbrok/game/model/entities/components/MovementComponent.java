package com.lothbrok.game.model.entities.components;

import com.badlogic.gdx.math.Vector2;
import com.lothbrok.game.model.entities.Entity;

public class MovementComponent extends AbstractComponent {

    private float speed;
    private float acceleration;
    private float maxSpeed;
    private float baseSpeed;

    public MovementComponent(Entity entity, float acceleration, float maxSpeed, float baseSpeed) {
        super(entity);
        this.acceleration = acceleration;
        this.maxSpeed = maxSpeed;
        this.baseSpeed = baseSpeed;
        this.speed = baseSpeed;
    }

    public void moveLeft(float deltaTime) {
        accelerate();
        float backupX = entity.getPositionX();
        entity.setPrevPositionX(backupX);
        entity.setPositionX(entity.getPositionX() - speed * deltaTime);
        entity.movementState = Entity.MovementState.MOVING;
        entity.direction = Entity.Direction.LEFT;
    }

    public void moveRight(float deltaTime) {
        accelerate();
        float backupX = entity.getPositionX();
        entity.setPrevPositionX(backupX);
        entity.setPositionX(entity.getPositionX() + speed * deltaTime);
        entity.movementState = Entity.MovementState.MOVING;
        entity.direction = Entity.Direction.RIGHT;
    }

    public void moveTo(Vector2 position, float deltaTime) {
        accelerate();
        float distanceX = position.x - entity.getPositionX();
        float directionX = distanceX / Math.abs(distanceX);
        entity.setPositionX(entity.getPositionX() + directionX * speed * deltaTime);
        entity.movementState = Entity.MovementState.MOVING;
        if(directionX < 0f) {
            if(entity.direction == Entity.Direction.RIGHT) {
                resetAcceleration();
            }
            entity.direction = Entity.Direction.LEFT;
        } else if(directionX > 0f) {
            if(entity.direction == Entity.Direction.LEFT) {
                resetAcceleration();
            }
            entity.direction = Entity.Direction.RIGHT;
        }
    }

    private void accelerate() {
        if((entity.movementState == Entity.MovementState.MIDMOVING ||
                entity.movementState == Entity.MovementState.MOVING) &&
                speed < maxSpeed) {

            speed *= acceleration;
        } else if(entity.movementState != Entity.MovementState.MIDMOVING) {
            speed = baseSpeed;
        }
    }

    public void resetAcceleration() {
        speed = baseSpeed;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
