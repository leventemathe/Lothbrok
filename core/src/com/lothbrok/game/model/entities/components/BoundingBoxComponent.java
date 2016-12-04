package com.lothbrok.game.model.entities.components;

import com.badlogic.gdx.math.Rectangle;
import com.lothbrok.game.model.entities.Entity;

public class BoundingBoxComponent extends AbstractComponent {

    private Rectangle boundingBox;

    public BoundingBoxComponent(Entity entity) {
        super(entity);
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(Rectangle boundingBox) {
        this.boundingBox = boundingBox;
    }
}
