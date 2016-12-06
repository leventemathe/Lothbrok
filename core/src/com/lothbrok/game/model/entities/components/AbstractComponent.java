package com.lothbrok.game.model.entities.components;

import com.lothbrok.game.model.entities.Entity;

public abstract class AbstractComponent {

    protected Entity entity;
    protected boolean enabled = true;

    public void disable() {
        enabled = false;
    }

    public AbstractComponent(Entity entity) {
        this.entity = entity;
    }
}
