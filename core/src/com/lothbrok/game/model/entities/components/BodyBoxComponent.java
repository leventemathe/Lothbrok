package com.lothbrok.game.model.entities.components;

import com.badlogic.gdx.math.Rectangle;
import com.lothbrok.game.model.entities.Entity;

public class BodyBoxComponent extends AbstractComponent {

    private Rectangle bodyBox;

    public BodyBoxComponent(Entity entity) {
        super(entity);
        bodyBox = new Rectangle(entity.position.x, entity.position.y, 0f, 0f);
    }

    public Rectangle getBodyBox() {
        return bodyBox;
    }

    public void setBodyBox(Rectangle bodyBox) {
        this.bodyBox = bodyBox;
    }
}
