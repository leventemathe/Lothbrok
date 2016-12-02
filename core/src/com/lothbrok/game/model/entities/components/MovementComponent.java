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
        entity.prevPosition.x = entity.position.x;
        entity.position.x -= speed * deltaTime;
        entity.movementState = Entity.MovementState.LEFT;
    }

    public void moveRight(float deltaTime) {
        accelerate();
        entity.prevPosition.x = entity.position.x;
        entity.position.x += speed * deltaTime;
        entity.movementState = Entity.MovementState.RIGHT;
    }

    private void accelerate() {
        if(entity.movementState == Entity.MovementState.MIDMOVING && speed < maxSpeed) {
            speed *= acceleration;
        } else if(entity.movementState != Entity.MovementState.MIDMOVING) {
            speed = baseSpeed;
        }
    }
}
