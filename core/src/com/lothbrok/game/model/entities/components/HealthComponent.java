package com.lothbrok.game.model.entities.components;

import com.lothbrok.game.model.entities.Entity;

public class HealthComponent extends AbstractComponent {

    private int health;

    public HealthComponent(Entity entity, int health) {
        super(entity);
        this.health = health;
    }

    public void loseHealth(int amount) {
        health -= amount;
        if(health < 0) {
            health = 0;
        }
    }

    public void addHealth(int amount) {
        health += amount;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
