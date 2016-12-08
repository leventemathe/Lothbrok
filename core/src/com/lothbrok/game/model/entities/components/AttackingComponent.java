package com.lothbrok.game.model.entities.components;

import com.lothbrok.game.model.entities.ActionListener;
import com.lothbrok.game.model.entities.Entity;

import java.util.HashSet;
import java.util.Set;

public class AttackingComponent<OpponentType> extends AbstractComponent {

    private static final String TAG = AttackingComponent.class.getSimpleName();

    private Set<OpponentType> opponentsHit;
    private ActionListener startAttackingListener;
    private ActionListener stopAttackingListener;

    public AttackingComponent(Entity entity) {
        super(entity);
        opponentsHit = new HashSet<>();
    }

    public void startAttacking() {
        if(entity.actionState.equals(Entity.ActionState.STANDING)) {
            entity.actionState = Entity.ActionState.ATTACKING;
            opponentsHit.clear();
            if(startAttackingListener != null) {
                startAttackingListener.listen();
            }
            //Gdx.app.debug(TAG, "started attacking");
        }
    }

    public void stopAttacking() {
        if(entity.actionState.equals(Entity.ActionState.ATTACKING)) {
            entity.actionState = Entity.ActionState.STANDING;
            //Gdx.app.debug(TAG, "stopped attacking");
            if(stopAttackingListener != null) {
                stopAttackingListener.listen();
            }
        }
    }

    public boolean addOpponentHit(OpponentType opponent) {
        return opponentsHit.add(opponent);
    }

    public void setStartAttackingListener(ActionListener startAttackingListener) {
        this.startAttackingListener = startAttackingListener;
    }

    public void setStopAttackingListener(ActionListener stopAttackingListener) {
        this.stopAttackingListener = stopAttackingListener;
    }
}
