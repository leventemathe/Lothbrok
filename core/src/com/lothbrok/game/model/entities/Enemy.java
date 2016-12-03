package com.lothbrok.game.model.entities;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.math.Vector2;
import com.lothbrok.game.model.entities.components.AttackingComponent;
import com.lothbrok.game.model.entities.components.MovementComponent;

public class Enemy extends Entity {

    private MovementComponent movementComponent;
    private AttackingComponent attackingComponent;

    private float travelled = 0f;
    private final float MAX_TRAVELLED = 3f;

    public Enemy(Vector2 position, Map map) {
        super(position);
        setupComponents(map);
    }

    private void setupComponents(Map map) {
        movementComponent = new MovementComponent(this, 1.01f, 2f, 1f);
        attackingComponent = new AttackingComponent(this);
    }

    public void move(float deltaTime) {
        if(direction == Direction.LEFT) {
            movementComponent.moveLeft(deltaTime);
        } else if(direction == Direction.RIGHT) {
            movementComponent.moveRight(deltaTime);
        }

        float movement = position.x - prevPosition.x;
        travelled += movement;
        if(travelled >= MAX_TRAVELLED) {
            position.x -= movement;
            direction = Direction.LEFT;
        } else if(travelled <= -MAX_TRAVELLED) {
            position.x += movement;
            direction = Direction.RIGHT;
        }
    }
}
