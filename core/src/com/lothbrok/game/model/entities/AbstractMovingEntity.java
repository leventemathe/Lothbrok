package com.lothbrok.game.model.entities;

import com.badlogic.gdx.math.Vector2;

public abstract class AbstractMovingEntity implements MovingEntity {

    protected Vector2 position;
    protected float speed;
    protected float maxJumpHeight;
    protected float jumpSpeed;

    public enum State {
        STANDING,
        FALLING,
        JUMPING,
        WALKINGLEFT,
        WALKINGRIGHT
    }

    protected State state;

    public abstract void update(float deltaTime);

    public Vector2 getPosition() {
        return position;
    }

    public State getState() {
        return state;
    }
}
