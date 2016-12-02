package com.lothbrok.game.model.entities;

import com.badlogic.gdx.math.Vector2;

public abstract class Entity {

    public enum ActionState {
        STANDING,
        FALLING,
        JUMPING,
        MIDJUMP,
        ATTACKING
    }

    public enum MovementState {
        STANDING,
        LEFT,
        RIGHT,
        MIDMOVING
    }

    public ActionState actionState;
    public MovementState movementState;

    public Vector2 position;
    public Vector2 prevPosition;
}
