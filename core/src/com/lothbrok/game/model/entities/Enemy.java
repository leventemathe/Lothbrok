package com.lothbrok.game.model.entities;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.lothbrok.game.model.entities.components.AttackingComponent;
import com.lothbrok.game.model.entities.components.MovementComponent;
import com.lothbrok.game.model.entities.components.TiledCollisionComponent;

public class Enemy extends Entity {

    private MovementComponent movementComponent;
    private TiledCollisionComponent tiledCollisionComponent;
    private AttackingComponent attackingComponent;

    private float travelled = 0f;
    private final float MAX_TRAVELLED = 3f;

    public Enemy(Vector2 position, Map map) {
        super(position);
        setupComponents(map);
    }

    private void setupComponents(Map map) {
        movementComponent = new MovementComponent(this, 1.01f, 2f, 1f);
        tiledCollisionComponent = new TiledCollisionComponent(this, (TiledMap)map);
        attackingComponent = new AttackingComponent(this);
    }

    public void update(float deltaTime) {

    }
}
