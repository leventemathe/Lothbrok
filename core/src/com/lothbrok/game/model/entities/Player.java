package com.lothbrok.game.model.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Player extends AbstractMovingEntity {

    private final String TAG = Player.class.getSimpleName();

    public Player() {
        this.position = new Vector2(0f, 0f);
        this.speed = 1f;
        maxJumpHeight = 0.8f;
        jumpSpeed = 0.4f;
        actionState = ActionState.STANDING;
    }

    @Override
    public void update(float deltaTime) {
        //TODO change this to falling + collision detection
        actionState = ActionState.STANDING;
        position.y = 0.0f;
    }

    @Override
    public void moveLeft(float deltaTime) {
        position.x -= speed*deltaTime;
        movingState = MovingState.WALKINGLEFT;
    }

    @Override
    public void moveRight(float deltaTime) {
        position.x += speed*deltaTime;
        movingState = MovingState.WALKINGRIGHT;
    }

    @Override
    public void jump(float deltaTime) {
        if((actionState.equals(ActionState.STANDING) || actionState.equals(ActionState.JUMPING)) && position.y < maxJumpHeight ) {
            position.y += jumpSpeed*deltaTime;
            actionState = ActionState.JUMPING;
            Gdx.app.debug(TAG, "jumping");
        }
    }
}
