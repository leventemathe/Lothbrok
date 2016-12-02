package com.lothbrok.game.model.entities.components;

import com.lothbrok.game.model.entities.Entity;

public class GravityComponent extends AbstractComponent {

    private float weight;
    private float gravity;
    private float maxWeight;
    private float baseWeight;

    public GravityComponent(Entity entity) {
        super(entity);
    }

    private void applyGravity() {
        if(entity.actionState == Entity.ActionState.FALLING && weight < maxWeight) {
            weight *= gravity;
        } else if(entity.actionState != Entity.ActionState.FALLING) {
            weight = baseWeight;
        }
    }
}
