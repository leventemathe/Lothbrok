package com.lothbrok.game.model.entities.components;

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
        float backupX = entity.position.x;
        entity.prevPosition.x = backupX;
        entity.position.x -= speed * deltaTime;
        entity.movementState = Entity.MovementState.MOVING;
        entity.direction = Entity.Direction.LEFT;
    }

    public void moveRight(float deltaTime) {
        accelerate();
        float backupX = entity.position.x;
        entity.prevPosition.x = backupX;
        entity.position.x += speed * deltaTime;
        entity.movementState = Entity.MovementState.MOVING;
        entity.direction = Entity.Direction.RIGHT;
    }

    private void accelerate() {
        if(entity.movementState == Entity.MovementState.MIDMOVING && speed < maxSpeed) {
            speed *= acceleration;
        } else if(entity.movementState != Entity.MovementState.MIDMOVING) {
            speed = baseSpeed;
        }
    }

    public float getSpeed() {
        return speed;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public float getBaseSpeed() {
        return baseSpeed;
    }
}
