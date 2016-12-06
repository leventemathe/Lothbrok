package com.lothbrok.game.model.entities;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.lothbrok.game.model.entities.components.AttackingComponent;
import com.lothbrok.game.model.entities.components.BodyBoxComponent;
import com.lothbrok.game.model.entities.components.MovementComponent;
import com.lothbrok.game.model.entities.components.TiledCollisionComponent;
import com.lothbrok.game.model.entities.components.WeaponBoxComponent;

public class Enemy extends Entity {

    private static final String TAG = Enemy.class.getSimpleName();
    private MovementComponent movementComponent;
    private BodyBoxComponent bodyBoxComponent;
    private WeaponBoxComponent weaponBoxComponent;
    private TiledCollisionComponent tiledCollisionComponent;
    private AttackingComponent<Player> attackingComponent;

    private float origin;
    private float distanceFromOrigin = 0f;
    public final float RADIUS = 3f;
    public final float ATTACK_RADIUS = 1f;

    private final float ATTACK_TIME = 1f;
    private float attackTimer = ATTACK_TIME;

    private boolean isActive = false;

    public Enemy(Vector2 position, Map map) {
        super(position);
        this.origin = position.x;
        setupComponents(map);
    }

    private void setupComponents(Map map) {
        movementComponent = new MovementComponent(this, 1.01f, 2f, 1f);
        bodyBoxComponent = new BodyBoxComponent(this);
        weaponBoxComponent = new WeaponBoxComponent(this);
        tiledCollisionComponent = new TiledCollisionComponent(this, (TiledMap)map, bodyBoxComponent);
        attackingComponent = new AttackingComponent<>(this);
    }

    public void update(float deltaTime) {
        attackTimer += deltaTime;
        movementState = MovementState.STANDING;
    }

    public void move(float deltaTime) {
        isActive = false;
        if(direction == Direction.LEFT) {
            if(tiledCollisionComponent.doesLeftPlatformExist() && !tiledCollisionComponent.isLeftColliding()) {
                movementComponent.moveLeft(deltaTime);
            } else {
                direction = Direction.RIGHT;
                movementComponent.resetAcceleration();
            }
        } else if(direction == Direction.RIGHT) {
            if(tiledCollisionComponent.doesRightPlatformExist() && !tiledCollisionComponent.isRightColliding()) {
                movementComponent.moveRight(deltaTime);
            } else {
                direction = Direction.LEFT;
                movementComponent.resetAcceleration();
            }
        }

        distanceFromOrigin = position.x - origin;
        if (distanceFromOrigin >= RADIUS) {
            direction = Direction.LEFT;
            movementComponent.resetAcceleration();
        } else if (distanceFromOrigin <= -RADIUS) {
            direction = Direction.RIGHT;
            movementComponent.resetAcceleration();
        }
        //Gdx.app.debug(TAG, "speed: " + movementComponent.getSpeed());
    }

    public void moveTo(Vector2 position, float deltaTime) {
        isActive = true;
        float direction = position.x - this.position.x;
        if(direction < 0f) {
            if(tiledCollisionComponent.doesLeftPlatformExist() && !tiledCollisionComponent.isLeftColliding()) {
                movementComponent.moveTo(position, deltaTime);
            }
        } else if(direction > 0f) {
            if(tiledCollisionComponent.doesRightPlatformExist() && !tiledCollisionComponent.isRightColliding()) {
                movementComponent.moveTo(position, deltaTime);
            }
        }
        //Gdx.app.debug(TAG, "speed: " + movementComponent.getSpeed());
    }

    public void startAttacking() {
        isActive = true;
        if(attackTimer >= ATTACK_TIME) {
            attackingComponent.startAttacking();
            attackTimer = 0f;
        }
    }

    public boolean hit(Player player) {
        return attackingComponent.addOpponentHit(player);
    }

    public void getHit() {
        lifeState = LifeState.DEAD;
    }

    public void updateBoundingBox(Rectangle body, Rectangle foot, Rectangle weapon) {
        bodyBoxComponent.setBodyBox(body);
        tiledCollisionComponent.setFootSensor(foot);
        weaponBoxComponent.setWeaponBox(weapon);
    }

    public AttackingComponent getAttackingComponent() {
        return attackingComponent;
    }

    public Rectangle getWeaponBox() {
        return weaponBoxComponent.getWeaponBox();
    }

    public Rectangle getBodyBox() {
        return bodyBoxComponent.getBodyBox();
    }

    public boolean isActive() {
        return isActive;
    }
}
