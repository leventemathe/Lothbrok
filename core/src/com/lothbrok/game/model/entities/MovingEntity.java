package com.lothbrok.game.model.entities;

public interface MovingEntity {

    void moveLeft(float deltaTime);
    void moveRight(float deltaTime);
    void jump(float deltaTime);
}
