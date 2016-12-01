package com.lothbrok.game.model.entities;

import com.badlogic.gdx.math.Vector2;

public abstract class MovingEntity {

    protected Vector2 position;
    protected Vector2 prevPosition;

    protected float speed;
    protected float acceleration;
    protected float maxSpeed;
    protected float baseSpeed;

    protected float jumpHeight = 0f;
    protected float maxJumpHeight;
    protected float jumpSpeed;
    protected float jumpDeceleration;
    protected float minJumpSpeed;
    protected float baseJumpSpeed;

    protected float weight;
    protected float gravity;
    protected float maxWeight;
    protected float baseWeight;

    public enum ActionState {
        STANDING,
        FALLING,
        JUMPING,
        MIDJUMP,
        ATTACKING
    }

    public enum MovingState {
        STANDING,
        LEFT,
        RIGHT,
        MIDMOVING
    }

    protected ActionState actionState;
    protected MovingState movingState;

    public abstract void update(float deltaTime);

    public Vector2 getPosition() {
        return position;
    }

    public ActionState getActionState() {
        return actionState;
    }

    public MovingState getMovingState() {
        return movingState;
    }

    public abstract void moveLeft(float delta);
    public abstract void moveRight(float delta);
    public abstract void jump(float delta);
    public abstract void startAttacking(float delta);
    public abstract void stopAttacking(float delta);

    public float getSpeed() {
        return speed;
    }

    public boolean isActuallyMoving() {
        return !(position.x == prevPosition.x);
    }
}
