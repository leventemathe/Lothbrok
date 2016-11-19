package com.lothbrok.game.model.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Player extends MovingEntity {

    private final String TAG = Player.class.getSimpleName();

    public Player() {
        setupBasics();
        setupMoving();
        setupJumping();
        setupFalling();
    }

    private void setupBasics() {
        this.position = new Vector2(0f, 0f);
        this.actionState = ActionState.STANDING;
        this.movingState = MovingState.STANDING;
    }

    private void setupMoving() {
        this.speed = 1f;
        this.acceleration = 1.01f;
        this.maxSpeed = 2f;
        this.baseSpeed = this.speed;
    }

    private void setupJumping() {
        this.maxJumpHeight = 1.6f;

        this.jumpSpeed = 2.4f;
        this.jumpDeceleration = 0.992f;
        this.minJumpSpeed = 1.4f;
        this.baseJumpSpeed = this.jumpSpeed;
    }

    private void setupFalling() {
        this.weight = 0.8f;
        this.gravity = 1.008f;
        this.maxWeight = 3f;
        this.baseWeight = this.weight;
    }

    @Override
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
            }
        } else {
            actionState = ActionState.FALLING;
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

    @Override
    public void moveLeft(float deltaTime) {
        accelerate();
        position.x -= speed * deltaTime;
        movingState = MovingState.LEFT;
    }

    @Override
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

    @Override
    public void jump(float delta) {
        if(actionState.equals(ActionState.STANDING) || actionState.equals(ActionState.JUMPING) || actionState == ActionState.MIDJUMP) {
            if(jumpHeight < maxJumpHeight) {
                decelerateJumping();
                position.y += jumpSpeed * delta;
                jumpHeight += jumpSpeed * delta;
                actionState = ActionState.JUMPING;
            } else {
                jumpHeight = 0f;
                actionState = ActionState.FALLING;
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

    @Override
    public void startAttacking(float deltaTime) {
        if(actionState.equals(ActionState.STANDING)) {
            actionState = ActionState.ATTACKING;
            Gdx.app.debug(TAG, "started attacking");
        }
    }

    @Override
    public void stopAttacking(float delta) {
        if(actionState.equals(ActionState.ATTACKING)) {
            actionState = ActionState.STANDING;
            Gdx.app.debug(TAG, "stopped attacking");
        }
    }
}
