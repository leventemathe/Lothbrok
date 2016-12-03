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

    public Entity(Vector2 position) {
        setupBasics(position);
    }

    private void setupBasics(Vector2 position) {
        this.position = position;
        this.prevPosition = new Vector2();
        this.prevPosition.x = position.x;
        this.prevPosition.y = position.y;
        this.actionState = Entity.ActionState.STANDING;
        this.movementState = Entity.MovementState.STANDING;
    }
}
