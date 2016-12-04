package com.lothbrok.game.model.entities.components;

import com.badlogic.gdx.math.Rectangle;
import com.lothbrok.game.model.entities.Entity;

public class BodyBoxComponent extends AbstractComponent {

    private Rectangle bodyBox;

    public BodyBoxComponent(Entity entity) {
        super(entity);
    }

    public Rectangle getBodyBox() {
        return bodyBox;
    }

    public void setBodyBox(Rectangle bodyBox) {
        this.bodyBox = bodyBox;
    }
}
