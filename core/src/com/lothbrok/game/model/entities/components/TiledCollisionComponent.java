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
        int x1 = (int)Math.floor(footSensor.x);
        int x2 = (int)Math.floor(footSensor.x + footSensor.width);
        int y = (int)Math.floor(footSensor.y);

        TiledMapTileLayer.Cell leftCell = map.getCell(x1, y);
        TiledMapTileLayer.Cell rightCell = map.getCell(x2, y);

        return isColliding(leftCell, rightCell);
    }

    public boolean isTopColliding() {
        int x1 = (int)Math.floor(boundingBox.x);
        int x2 = (int)Math.floor(boundingBox.x + boundingBox.width);
        int y = (int)Math.ceil(boundingBox.y);

        TiledMapTileLayer.Cell leftCell = map.getCell(x1, y);
        TiledMapTileLayer.Cell rightCell = map.getCell(x2, y);

        return isColliding(leftCell, rightCell);
    }

    public boolean isRightColliding() {
        int x = (int)Math.floor(boundingBox.x + boundingBox.width);
        int y1 = (int)Math.floor(boundingBox.y);
        int y2 = (int)Math.floor(boundingBox.y + boundingBox.height);

        if(boundingBox.x + boundingBox.width > mapBorder.width) {
            return true;
        }

        TiledMapTileLayer.Cell bottomCell = map.getCell(x, y1);
        TiledMapTileLayer.Cell topCell = map.getCell(x, y2);

        return isColliding(bottomCell, topCell);
    }

    public boolean isLeftColliding() {
        int x = (int)Math.floor(boundingBox.x);
        int y1 = (int)Math.floor(boundingBox.y);
        int y2 = (int)Math.floor(boundingBox.y + boundingBox.height);

        if(boundingBox.x < mapBorder.x) {
            return true;
        }

        TiledMapTileLayer.Cell bottomCell = map.getCell(x, y1);
        TiledMapTileLayer.Cell topCell = map.getCell(x, y2);

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

    public boolean doesLeftPlatformExist() {
        int x = (int)Math.floor(boundingBox.x);
        int y = (int)Math.floor(boundingBox.y - 1f);

        return doesPlatformExist(x, y);
    }

    public boolean doesRightPlatformExist() {
        int x = (int)Math.ceil(boundingBox.x + boundingBox.width - 1f);
        int y = (int)Math.floor(boundingBox.y - 1f);

        return doesPlatformExist(x, y);
    }

    private boolean doesPlatformExist(int x, int y) {
        TiledMapTileLayer.Cell cell = map.getCell(x, y);
        if(cell == null) {
            return false;
        }

        TiledMapTile tile = cell.getTile();
        if(tile == null) {
            return false;
        }

        Object blocked = tile.getProperties().get("blocked");
        if(blocked == null || blocked.equals(false)) {
            return false;
        }
        return true;
    }

    public boolean hasFallenOutOfMap() {
        return boundingBox.y + boundingBox.height < mapBorder.y;
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
