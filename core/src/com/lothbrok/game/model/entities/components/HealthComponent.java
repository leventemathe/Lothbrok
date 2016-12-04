package com.lothbrok.game.model.entities.components;

import com.lothbrok.game.model.entities.Entity;

public class HealthComponent extends AbstractComponent {

    private int health;

    public HealthComponent(Entity entity, int health) {
        super(entity);
        this.health = health;
    }

    public int getHealth() {
        return health;
    }
}
