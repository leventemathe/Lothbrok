package com.lothbrok.game.model.entities;

import com.badlogic.gdx.math.Vector2;

public abstract class MovingEntity {

    protected Vector2 position;
    protected float speed;
    protected float maxJumpHeight;
    protected float jumpSpeed;

    public enum ActionState {
        STANDING,
        FALLING,
        JUMPING
    }

    public enum MovingState {
        WALKINGLEFT,
        WALKINGRIGHT,
        NONE
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
}
