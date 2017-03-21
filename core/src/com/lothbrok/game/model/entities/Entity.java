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

    public enum LifeState {
        WELL,
        DYING,
        DEAD
    }

    public enum MovementState {
        STANDING,
        MOVING,
        MIDMOVING
    }

    public enum Direction {
        LEFT,
        RIGHT
    }

    public ActionState actionState;
    public LifeState lifeState;
    public MovementState movementState;
    public Direction direction;

    private Vector2 position;
    private Vector2 prevPosition;

    public Entity(Vector2 position) {
        setupBasics(position);
    }

    private void setupBasics(Vector2 position) {
        this.position = position;
        this.prevPosition = new Vector2();
        this.prevPosition.x = position.x;
        this.prevPosition.y = position.y;
        this.actionState = ActionState.STANDING;
        this.lifeState = LifeState.WELL;
        this.movementState = MovementState.STANDING;
        this.direction = Direction.RIGHT;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getPositionX() {
        return position.x;
    }

    public float getPositionY() {
        return position.y;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setPositionX(float x) {
        this.position.x = x;
    }

    public void setPositionY(float y) {
        this.position.y = y;
    }

    public Vector2 getPrevPosition() {
        return prevPosition;
    }

    public float getPrevPositionX() {
        return prevPosition.x;
    }

    public float getPrevPositionY() {
        return prevPosition.y;
    }

    public void setPrevPosition(Vector2 prevPosition) {
        this.prevPosition = prevPosition;
    }

    public void setPrevPositionX(float x) {
        this.prevPosition.x = x;
    }

    public void setPrevPositionY(float y) {
        this.prevPosition.y = y;
    }
}
