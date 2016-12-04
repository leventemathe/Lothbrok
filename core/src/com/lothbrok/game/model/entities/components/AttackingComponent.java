package com.lothbrok.game.model.entities.components;

import com.lothbrok.game.model.entities.Entity;

public class AttackingComponent extends AbstractComponent {

    private static final String TAG = AttackingComponent.class.getSimpleName();

    public AttackingComponent(Entity entity) {
        super(entity);
    }

    public void startAttacking() {
        if(entity.actionState.equals(Entity.ActionState.STANDING)) {
            entity.actionState = Entity.ActionState.ATTACKING;
            //Gdx.app.debug(TAG, "started attacking");
        }
    }

    public void stopAttacking() {
        if(entity.actionState.equals(Entity.ActionState.ATTACKING)) {
            entity.actionState = Entity.ActionState.STANDING;
            //Gdx.app.debug(TAG, "stopped attacking");
        }
    }
}
