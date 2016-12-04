package com.lothbrok.game.model.entities.components;

import com.lothbrok.game.model.entities.Entity;

public class TreasureComponent extends AbstractComponent {

    private int treasure;

    public TreasureComponent(Entity entity, int treasure) {
        super(entity);
        this.treasure = treasure;
    }

    public void loseTreasure(int amount) {
        treasure -= amount;
        if(treasure < 0) {
            treasure = 0;
        }
    }

    public void addTreasure(int amount) {
        treasure += amount;
    }

    public int getTreasure() {
        return treasure;
    }
}
