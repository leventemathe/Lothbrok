package com.lothbrok.game.model.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player extends MovingEntity {

    private final String TAG = Player.class.getSimpleName();

    private TiledMapTileLayer map;
    private Rectangle mapBorder;


    private Rectangle boundingBox;
    private Rectangle footSensor;
    private final float MAX_BOUNDING_BOX_AGE = 1000f;
    private float boundingBoxAge = MAX_BOUNDING_BOX_AGE;

    public Player(Vector2 position, Map map) {
        this.map = (TiledMapTileLayer)map.getLayers().get("tiles");
        this.mapBorder = new Rectangle(0f, 0f, (int)map.getProperties().get("width"), (int)map.getProperties().get("height"));
        setupBasics(position);
        setupMoving();
        setupJumping();
        setupFalling();
    }

    private void setupBasics(Vector2 position) {
        this.position = position;
        this.prevPosition = new Vector2();
        this.prevPosition.x = position.x;
        this.prevPosition.y = position.y;
        this.actionState = ActionState.STANDING;
        this.movingState = MovingState.STANDING;
        this.boundingBox = new Rectangle();
        this.footSensor = new Rectangle();
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

    @Override
    public void update(float deltaTime) {
        updateActionState(deltaTime);
        updateMovingState();
    }

    private void updateActionState(float deltaTime) {
        //TODO collision detection with ground
        if(actionState == ActionState.JUMPING) {
            actionState = ActionState.MIDJUMP;
        } else if(actionState != ActionState.ATTACKING) {
            actionState = ActionState.FALLING;
            jumpHeight = 0f;
        }

        applyGravity();
        if(actionState == ActionState.FALLING) {
            prevPosition.y = position.y;
            position.y -= weight * deltaTime;
            if(isBottomColliding()) {
                actionState = ActionState.STANDING;
                position.y = prevPosition.y;
            }
        }
    }

    private void updateMovingState() {
        if(movingState == MovingState.LEFT || movingState == MovingState.RIGHT) {
            movingState = MovingState.MIDMOVING;
        } else {
            movingState = MovingState.STANDING;
        }
    }

    private void applyGravity() {
        if(actionState == ActionState.FALLING && weight < maxWeight) {
            weight *= gravity;
        } else if(actionState != ActionState.FALLING) {
            weight = baseWeight;
        }
    }

    @Override
    public void moveLeft(float deltaTime) {
        accelerate();
        prevPosition.x = position.x;
        position.x -= speed * deltaTime;
        if(isLeftColliding()) {
            position.x = prevPosition.x;
        }
        movingState = MovingState.LEFT;
    }

    @Override
    public void moveRight(float deltaTime) {
        accelerate();
        prevPosition.x = position.x;
        position.x += speed * deltaTime;
        if(isRightColliding()) {
            position.x = prevPosition.x;
        }
        movingState = MovingState.RIGHT;
    }

    private void accelerate() {
        if(movingState == MovingState.MIDMOVING && speed < maxSpeed) {
            speed *= acceleration;
        } else if(movingState != MovingState.MIDMOVING) {
            speed = baseSpeed;
        }
    }

    @Override
    public void jump(float delta) {
        if(actionState.equals(ActionState.STANDING) || actionState.equals(ActionState.JUMPING) || actionState == ActionState.MIDJUMP) {
            if(jumpHeight < maxJumpHeight) {
                decelerateJumping();
                prevPosition.y = position.y;
                position.y += jumpSpeed * delta;
                jumpHeight += jumpSpeed * delta;
                if(isTopColliding()) {
                    position.y = prevPosition.y;
                } else {
                    actionState = ActionState.JUMPING;
                }
            }
            //Gdx.app.debug(TAG, "jumping");
        }
    }

    private void decelerateJumping() {
        if(actionState == ActionState.MIDJUMP && jumpSpeed > minJumpSpeed) {
            jumpSpeed *= jumpDeceleration;
        } else if(actionState != ActionState.MIDJUMP) {
            jumpSpeed = baseJumpSpeed;
        }
    }

    @Override
    public void startAttacking() {
        if(actionState.equals(ActionState.STANDING)) {
            actionState = ActionState.ATTACKING;
            Gdx.app.debug(TAG, "started attacking");
        }
    }

    @Override
    public void stopAttacking() {
        if(actionState.equals(ActionState.ATTACKING)) {
            actionState = ActionState.STANDING;
            Gdx.app.debug(TAG, "stopped attacking");
        }
    }

    private boolean isBottomColliding() {
        int playerX1 = (int)Math.floor(footSensor.x);
        int playerX2 = (int)Math.floor(footSensor.x + footSensor.width);
        int playerY = (int)Math.floor(footSensor.y);

        TiledMapTileLayer.Cell leftCell = map.getCell(playerX1, playerY);
        TiledMapTileLayer.Cell rightCell = map.getCell(playerX2, playerY);

        return isColliding(leftCell, rightCell);
    }

    private boolean isTopColliding() {
        int playerX1 = (int)Math.floor(footSensor.x);
        int playerX2 = (int)Math.floor(footSensor.x + footSensor.width);
        int playerY = (int)Math.ceil(footSensor.y);

        TiledMapTileLayer.Cell leftCell = map.getCell(playerX1, playerY);
        TiledMapTileLayer.Cell rightCell = map.getCell(playerX2, playerY);

        return isColliding(leftCell, rightCell);
    }

    private boolean isRightColliding() {
        int playerX = (int)Math.floor(boundingBox.x + boundingBox.width);
        int playerY1 = (int)Math.floor(boundingBox.y);
        int playerY2 = (int)Math.floor(boundingBox.y + boundingBox.height);

        if(boundingBox.x + boundingBox.width > mapBorder.width) {
            return true;
        }

        TiledMapTileLayer.Cell bottomCell = map.getCell(playerX, playerY1);
        TiledMapTileLayer.Cell topCell = map.getCell(playerX, playerY2);

        return isColliding(bottomCell, topCell);
    }

    private boolean isLeftColliding() {
        int playerX = (int)Math.floor(boundingBox.x);
        int playerY1 = (int)Math.floor(boundingBox.y);
        int playerY2 = (int)Math.floor(boundingBox.y + boundingBox.height);

        if(boundingBox.x < mapBorder.x) {
            return true;
        }

        TiledMapTileLayer.Cell bottomCell = map.getCell(playerX, playerY1);
        TiledMapTileLayer.Cell topCell = map.getCell(playerX, playerY2);

        return isColliding(bottomCell, topCell);
    }

    private boolean isColliding(TiledMapTileLayer.Cell cell1, TiledMapTileLayer.Cell cell2) {
        TiledMapTile tile1 = null;
        TiledMapTile tile2 = null;

        if(cell1 != null) {
            tile1 = cell1.getTile();
        }
        if(cell2 != null) {
            tile2 = cell2.getTile();
        }

        if(tile1 != null) {
            Object blocked = tile1.getProperties().get("blocked");
            if(blocked != null && blocked.equals(true)) {
                return true;
            }
        }
        if(tile2 != null) {
            Object blocked = tile2.getProperties().get("blocked");
            if(blocked != null && blocked.equals(true)) {
                return true;
            }
        }
        return false;
    }

    public void updateBoundingBox(Rectangle body, Rectangle foot, float deltaTime) {
        boundingBox = body;
        footSensor = foot;
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public Rectangle getFootSensor() {
        return footSensor;
    }
}
