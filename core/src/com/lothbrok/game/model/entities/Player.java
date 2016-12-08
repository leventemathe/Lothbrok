package com.lothbrok.game.model.entities;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.lothbrok.game.model.entities.components.AttackingComponent;
import com.lothbrok.game.model.entities.components.BodyBoxComponent;
import com.lothbrok.game.model.entities.components.GravityComponent;
import com.lothbrok.game.model.entities.components.HealthComponent;
import com.lothbrok.game.model.entities.components.JumpingComponent;
import com.lothbrok.game.model.entities.components.MovementComponent;
import com.lothbrok.game.model.entities.components.TiledCollisionComponent;
import com.lothbrok.game.model.entities.components.TreasureComponent;
import com.lothbrok.game.model.entities.components.WeaponBoxComponent;

public class Player extends Entity {

    private static final String TAG = Player.class.getSimpleName();
    private GravityComponent gravityComponent;
    private MovementComponent movementComponent;
    private JumpingComponent jumpingComponent;
    private BodyBoxComponent bodyBoxComponent;
    private WeaponBoxComponent weaponBoxComponent;
    private TiledCollisionComponent tiledCollisionComponent;
    private AttackingComponent<Enemy> attackingComponent;
    private HealthComponent healthComponent;
    private TreasureComponent treasureComponent;

    private float treasureTime = 0f;
    private final float TREASURE_INTERVAL = 0.5f;

    private boolean victory = false;

    // Setup
    public Player(Vector2 position, Map map) {
        super(position);
        setupComponents(map);
    }

    private void setupComponents(Map map) {
        gravityComponent = new GravityComponent(this, 1.008f, 3f, 0.8f);
        movementComponent = new MovementComponent(this, 1.01f, 2f, 1f);
        jumpingComponent = new JumpingComponent(this, 1.6f, 0.992f, 1.4f, 2.4f);
        bodyBoxComponent = new BodyBoxComponent(this);
        weaponBoxComponent = new WeaponBoxComponent(this);
        tiledCollisionComponent = new TiledCollisionComponent(this, (TiledMap)map, bodyBoxComponent);
        attackingComponent = new AttackingComponent<>(this);
        healthComponent = new HealthComponent(this, 3);
        treasureComponent = new TreasureComponent(this, 100);
    }



    // Update
    public void update(float deltaTime) {
        prevPosition.x = position.x;
        prevPosition.y = position.y;
        updateActionState(deltaTime);
        updateMovingState();
        updateHealth();
        updateTreasure(deltaTime);
        updateLifeState(deltaTime);
        updateVictory();
    }

    private void updateActionState(float deltaTime) {
        if(actionState == Entity.ActionState.JUMPING) {
            actionState = Entity.ActionState.MIDJUMP;
        } else if(actionState != Entity.ActionState.ATTACKING) {
            actionState = Entity.ActionState.FALLING;
        }

        gravityComponent.applyGravity(deltaTime);
        if(tiledCollisionComponent.isBottomColliding() && actionState == ActionState.FALLING) {
            actionState = Entity.ActionState.STANDING;
            position.y = prevPosition.y;
        }
    }

    private void updateMovingState() {
        if(movementState == MovementState.MOVING) {
            movementState = Entity.MovementState.MIDMOVING;
        } else {
            movementState = Entity.MovementState.STANDING;
        }
    }

    private void updateHealth() {
        if(tiledCollisionComponent.fallenOutOfMap()) {
            healthComponent.setHealth(0);
        }
    }

    private void updateTreasure(float deltaTime) {
        if(movementState != MovementState.STANDING || actionState != ActionState.STANDING) {
            treasureTime += deltaTime;
        } else {
            treasureTime = 0f;
        }
        if(treasureTime >= TREASURE_INTERVAL) {
            treasureTime = 0f;
            treasureComponent.loseTreasure(1);
        }
    }

    private void updateLifeState(float deltaTime) {
        if(lifeState == LifeState.DYING) {
            lifeState = LifeState.DEAD;
        }
        if(getHealth() <= 0 && lifeState != LifeState.DEAD) {
            lifeState = LifeState.DYING;
        }
        //Gdx.app.debug("life: ", lifeState.toString());
    }

    private void updateVictory() {
        if(!victory) {
            victory = tiledCollisionComponent.victoryReached();
        }
    }



    // Control methods
    public void moveLeft(float deltaTime) {
        movementComponent.moveLeft(deltaTime);
        if(tiledCollisionComponent.isLeftColliding()) {
            position.x = prevPosition.x;
        }
    }

    public void moveRight(float deltaTime) {
        movementComponent.moveRight(deltaTime);
        if(tiledCollisionComponent.isRightColliding()) {
            position.x = prevPosition.x;
        }
    }

    public void jump(float deltaTime) {
        jumpingComponent.jump(deltaTime);
        if(tiledCollisionComponent.isTopColliding()) {
            actionState = ActionState.FALLING;
        }
    }

    public void startAttacking() {
        attackingComponent.startAttacking();
        treasureComponent.loseTreasure(1);
    }

    public void stopAttacking() {
        attackingComponent.stopAttacking();
    }

    public void updateBoundingBoxes(Rectangle body, Rectangle foot, Rectangle weapon) {
        bodyBoxComponent.setBodyBox(body);
        tiledCollisionComponent.setFootSensor(foot);
        weaponBoxComponent.setWeaponBox(weapon);
    }

    public boolean hit(Enemy enemy) {
        return attackingComponent.addOpponentHit(enemy);
    }

    public void getHit() {
        //lifeState = LifeState.HIT;
        healthComponent.loseHealth(1);
    }

    public boolean isActuallyMoving() {
        return position.x != prevPosition.x;
    }

    public boolean isVictoryAchieved() {
        return victory;
    }

    public float getSpeed() {
        return movementComponent.getSpeed();
    }

    public Rectangle getBodyBox() {
        return bodyBoxComponent.getBodyBox();
    }

    public Rectangle getFootSensor() {
        return tiledCollisionComponent.getFootSensor();
    }

    public Rectangle getWeaponBox() {
        return weaponBoxComponent.getWeaponBox();
    }

    public AttackingComponent getAttackingComponent() {
        return attackingComponent;
    }

    public int getHealth() {
        return healthComponent.getHealth();
    }

    public int getTreasure() {
        return treasureComponent.getTreasure();
    }

    public HealthComponent getHealthComponent() {
        return healthComponent;
    }

    public MovementComponent getMovementComponent() {
        return movementComponent;
    }

    public JumpingComponent getJumpingComponent() {
        return jumpingComponent;
    }
}
