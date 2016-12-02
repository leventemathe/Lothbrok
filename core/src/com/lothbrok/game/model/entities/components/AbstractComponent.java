package com.lothbrok.game.model.entities.components;

import com.lothbrok.game.model.entities.Entity;

public abstract class AbstractComponent {

    protected Entity entity;

    public AbstractComponent(Entity entity) {
        this.entity = entity;
    }
}
