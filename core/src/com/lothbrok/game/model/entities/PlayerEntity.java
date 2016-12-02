package com.lothbrok.game.model.entities;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.lothbrok.game.model.entities.components.AttackingComponent;
import com.lothbrok.game.model.entities.components.GravityComponent;
import com.lothbrok.game.model.entities.components.JumpingComponent;
import com.lothbrok.game.model.entities.components.MovementComponent;
import com.lothbrok.game.model.entities.components.TiledCollisionComponent;

public class PlayerEntity extends Entity {

    private GravityComponent gravityComponent;
    private MovementComponent movementComponent;
    private JumpingComponent jumpingComponent;
    private TiledCollisionComponent tiledCollisionComponent;
    private AttackingComponent attackingComponent;

    public PlayerEntity(Vector2 position, Map map) {
        setupBasics(position);
        setupComponents(map);
    }

    private void setupBasics(Vector2 position) {
        this.position = position;
        this.prevPosition = new Vector2();
        this.prevPosition.x = position.x;
        this.prevPosition.y = position.y;
        this.actionState = Entity.ActionState.STANDING;
        this.movementState = Entity.MovementState.STANDING;
    }

    private void setupComponents(Map map) {
        gravityComponent = new GravityComponent(this, 1.008f, 3f, 0.8f);
        movementComponent = new MovementComponent(this, 1.01f, 2f, 1f);
        jumpingComponent = new JumpingComponent(this, 1.6f, 0.992f, 1.4f, 2.4f);
        tiledCollisionComponent = new TiledCollisionComponent(this, (TiledMapTileLayer)map.getLayers().get("tiles"));
        attackingComponent = new AttackingComponent(this);
    }
}
