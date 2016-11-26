package com.lothbrok.game.model.entities;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Player extends MovingEntity {

    private final String TAG = Player.class.getSimpleName();

    public Player(TiledMapTileLayer tiles) {
        super(tiles);
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
}
