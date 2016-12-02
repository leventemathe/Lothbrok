package com.lothbrok.game.model.entities.components;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.lothbrok.game.model.entities.Entity;

public class TiledCollisionComponent extends AbstractComponent {

    private TiledMapTileLayer map;
    private Rectangle mapBorder;

    private Rectangle boundingBox;
    private Rectangle footSensor;

    public TiledCollisionComponent(Entity entity, TiledMap map) {
        super(entity);
        this.map = (TiledMapTileLayer)map.getLayers().get("tiles");
        this.mapBorder = new Rectangle(0f, 0f, (int)map.getProperties().get("width"), (int)map.getProperties().get("height"));
    }

    public boolean isBottomColliding() {
        int playerX1 = (int)Math.floor(footSensor.x);
        int playerX2 = (int)Math.floor(footSensor.x + footSensor.width);
        int playerY = (int)Math.floor(footSensor.y);

        TiledMapTileLayer.Cell leftCell = map.getCell(playerX1, playerY);
        TiledMapTileLayer.Cell rightCell = map.getCell(playerX2, playerY);

        return isColliding(leftCell, rightCell);
    }

    public boolean isTopColliding() {
        int playerX1 = (int)Math.floor(boundingBox.x);
        int playerX2 = (int)Math.floor(boundingBox.x + boundingBox.width);
        int playerY = (int)Math.ceil(boundingBox.y);

        TiledMapTileLayer.Cell leftCell = map.getCell(playerX1, playerY);
        TiledMapTileLayer.Cell rightCell = map.getCell(playerX2, playerY);

        return isColliding(leftCell, rightCell);
    }

    public boolean isRightColliding() {
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

    public boolean isLeftColliding() {
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

    public void updateBoundingBox(Rectangle body, Rectangle foot) {
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
