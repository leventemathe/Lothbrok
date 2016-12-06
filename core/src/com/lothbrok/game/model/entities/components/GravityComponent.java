package com.lothbrok.game.model.entities.components;

import com.lothbrok.game.model.entities.Entity;

public class GravityComponent extends AbstractComponent {

    private float weight;
    private float gravity;
    private float maxWeight;
    private float baseWeight;

    public GravityComponent(Entity entity, float gravity, float maxWeight, float baseWeight) {
        super(entity);
        this.gravity = gravity;
        this.maxWeight = maxWeight;
        this.baseWeight = baseWeight;
        this.weight = baseWeight;
    }

    public void applyGravity(float deltaTime) {
        if(!enabled) {
            return;
        }
        if(entity.actionState == Entity.ActionState.FALLING && weight < maxWeight) {
            weight *= gravity;
        } else if(entity.actionState != Entity.ActionState.FALLING) {
            weight = baseWeight;
        }
        if(entity.actionState == Entity.ActionState.FALLING) {
            float backupY = entity.position.y;
            entity.prevPosition.y = backupY;
            entity.position.y -= weight * deltaTime;
        }
    }
}
