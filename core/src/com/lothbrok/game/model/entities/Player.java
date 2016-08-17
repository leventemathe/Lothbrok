package com.lothbrok.game.model.entities;

import com.badlogic.gdx.math.Vector2;

public class Player extends AbstractMovingEntity {

    public Player() {
        this.position = new Vector2(0f, 0f);
        this.speed = 1f;
        maxJumpHeight = 0.8f;
        jumpSpeed = 0.4f;
        state = State.STANDING;
    }

    @Override
    public void update(float deltaTime) {
        //TODO change this to falling + collision detection
        state = State.STANDING;
        position.y = 0.0f;
    }

    @Override
    public void moveLeft(float deltaTime) {
        position.x -= speed*deltaTime;
        state = State.WALKINGLEFT;
    }

    @Override
    public void moveRight(float deltaTime) {
        position.x += speed*deltaTime;
        state = State.WALKINGRIGHT;
    }

    @Override
    public void jump(float deltaTime) {
        if((state.equals(State.STANDING) || state.equals(State.JUMPING)) && position.y < maxJumpHeight ) {
            position.y += jumpSpeed*deltaTime;
            state = State.JUMPING;
        }
    }
}
