package com.lothbrok.game.model.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public abstract class MovingEntity {

    private static final String TAG = MovingEntity.class.getSimpleName();
    protected Vector2 position;

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

    protected TiledMapTileLayer tiles;

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

    public MovingEntity(TiledMapTileLayer tiles) {
        this.tiles = tiles;
    }

    public Vector2 getPosition() {
        return position;
    }

    public ActionState getActionState() {
        return actionState;
    }

    public MovingState getMovingState() {
        return movingState;
    }

    public void update(float deltaTime) {
        updateActionState(deltaTime);
        updateMovingState();
    }

    private void updateActionState(float deltaTime) {
        //TODO collision detection with ground
        if(actionState == ActionState.JUMPING) {
            actionState = ActionState.MIDJUMP;
        } else if(position.y <= 0f) {
            if(actionState != ActionState.ATTACKING) {
                actionState = ActionState.STANDING;
                jumpHeight = 0f;
            }
        } else {
            actionState = ActionState.FALLING;
            jumpHeight = 0f;
        }

        applyGravity();
        if(actionState == ActionState.FALLING) {
            position.y -= weight * deltaTime;
        }
    }

    private void updateMovingState() {
        if(movingState == MovingState.LEFT || movingState == MovingState.RIGHT) {
            movingState = MovingState.MIDMOVING;
        } else {
            movingState = MovingState.STANDING;
        }
    }

    private void applyGravity() {
        if(actionState == ActionState.FALLING && weight < maxWeight) {
            weight *= gravity;
        } else if(actionState != ActionState.FALLING) {
            weight = baseWeight;
        }
    }

    public void moveLeft(float deltaTime) {
        accelerate();
        position.x -= speed * deltaTime;
        movingState = MovingState.LEFT;
    }

    public void moveRight(float deltaTime) {
        accelerate();
        position.x += speed * deltaTime;
        movingState = MovingState.RIGHT;
    }

    private void accelerate() {
        if(movingState == MovingState.MIDMOVING && speed < maxSpeed) {
            speed *= acceleration;
        } else if(movingState != MovingState.MIDMOVING) {
            speed = baseSpeed;
        }
    }

    public void jump(float delta) {
        if(actionState.equals(ActionState.STANDING) || actionState.equals(ActionState.JUMPING) || actionState == ActionState.MIDJUMP) {
            if(jumpHeight < maxJumpHeight) {
                decelerateJumping();
                position.y += jumpSpeed * delta;
                jumpHeight += jumpSpeed * delta;
                actionState = ActionState.JUMPING;
            }
            //Gdx.app.debug(TAG, "jumping");
        }
    }

    private void decelerateJumping() {
        if(actionState == ActionState.MIDJUMP && jumpSpeed > minJumpSpeed) {
            jumpSpeed *= jumpDeceleration;
        } else if(actionState != ActionState.MIDJUMP) {
            jumpSpeed = baseJumpSpeed;
        }
    }

    public void startAttacking(float deltaTime) {
        if(actionState.equals(ActionState.STANDING)) {
            actionState = ActionState.ATTACKING;
            Gdx.app.debug(TAG, "started attacking");
        }
    }

    public void stopAttacking(float delta) {
        if(actionState.equals(ActionState.ATTACKING)) {
            actionState = ActionState.STANDING;
            Gdx.app.debug(TAG, "stopped attacking");
        }
    }
}
