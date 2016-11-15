package com.lothbrok.game.model.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Player extends MovingEntity {

    private final String TAG = Player.class.getSimpleName();

    public Player() {
        this.position = new Vector2(0f, 0f);
        this.speed = 1f;
        this.maxJumpHeight = 1.6f;
        this.jumpSpeed = 1f;
        this.weight = 1f;
        this.actionState = ActionState.STANDING;
    }

    @Override
    public void update(float deltaTime) {
        //TODO collision detection with ground
        if(actionState == ActionState.JUMPING || position.y <= 0f) {
            actionState = ActionState.STANDING;
        } else {
            actionState = ActionState.FALLING;
        }

        if(actionState == ActionState.FALLING) {
            position.y -= weight * deltaTime;
        }

        movingState = MovingState.NONE;
    }

    @Override
    public void moveLeft(float deltaTime) {
        position.x -= speed * deltaTime;
        movingState = MovingState.LEFT;
    }

    @Override
    public void moveRight(float deltaTime) {
        position.x += speed * deltaTime;
        movingState = MovingState.RIGHT;
    }

    @Override
    public void jump(float delta) {
        if(actionState.equals(ActionState.STANDING) || actionState.equals(ActionState.JUMPING)) {
            actionState = ActionState.JUMPING;
            if(jumpHeight < maxJumpHeight) {
                position.y += jumpSpeed * delta;
                jumpHeight += jumpSpeed * delta;
            } else {
                jumpHeight = 0f;
                actionState = ActionState.FALLING;
            }
            Gdx.app.debug(TAG, "jumping");
        }
    }

    @Override
    public void attack(float deltaTime) {
        if(actionState.equals(ActionState.STANDING)) {
            actionState = ActionState.ATTACKING;
            Gdx.app.debug(TAG, "attacking");
        }
    }
}
