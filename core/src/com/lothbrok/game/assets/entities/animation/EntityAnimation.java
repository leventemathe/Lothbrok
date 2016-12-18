package com.lothbrok.game.assets.entities.animation;

import com.badlogic.gdx.math.Rectangle;
import com.brashmonkey.spriter.Animation;
import com.brashmonkey.spriter.Mainline;
import com.brashmonkey.spriter.Player;
import com.lothbrok.game.assets.spriter.SpriterAnimation;
import com.lothbrok.game.model.entities.components.AttackingComponent;

//TODO this should consist of components too
public abstract class EntityAnimation {

    protected SpriterAnimation animation;
    protected AttackComponentListener attackComponentListener;

    public EntityAnimation(SpriterAnimation animation, String animationEntity, String idleAnimation) {
        this.animation = animation;
        this.animation.setCurrentEntity(animationEntity);
        this.animation.setPlayAlways(idleAnimation);
    }

    public abstract Rectangle getBodyBoundingBox();
    public abstract Rectangle getWeaponBoundingBox();
    public abstract Rectangle getFootSensor();
    public abstract Rectangle getHeadSensor();

    protected Rectangle getBodyBoundingBox(String body, String leg, float bottomDelta, float topDelta) {
        Rectangle bodyRect = animation.getBoundingBox(body);
        Rectangle legRect = animation.getBoundingBox(leg);
        if(bodyRect != null && legRect != null) {
            return new Rectangle(bodyRect.x,
                    legRect.y + bottomDelta,
                    bodyRect.width,
                    bodyRect.height + legRect.height - topDelta);
        }
        return null;
    }

    protected Rectangle getFootSensor(String leftLeg, String rightLeg, float bottomDelta) {
        Rectangle leftLegRect = animation.getBoundingBox(leftLeg);
        Rectangle rightLegRect = animation.getBoundingBox(rightLeg);
        Rectangle bodyRect =getBodyBoundingBox();
        float facingRightWidth = Math.min(bodyRect.width - 0.05f, rightLegRect.x + rightLegRect.width - leftLegRect.x);
        float facingLeftX = Math.max(bodyRect.x + 0.05f, rightLegRect.x);
        if(leftLegRect != null && rightLegRect != null) {
            if(animation.facingRight()) {
                return new Rectangle(leftLegRect.x,
                        leftLegRect.y,
                        facingRightWidth,
                        rightLegRect.height + bottomDelta);
            }
            return new Rectangle(facingLeftX,
                    rightLegRect.y,
                    leftLegRect.x + leftLegRect.width - rightLegRect.x,
                    rightLegRect.height + bottomDelta);
        }
        return null;
    }

    protected Rectangle getHeadSensor(String head, float delta) {
        Rectangle headRect = animation.getBoundingBox(head);
        if(headRect != null)  {
            return new Rectangle(headRect.x + delta/2f,
                    headRect.y,
                    headRect.width - delta,
                    headRect.height + delta/2f);
        }
        return null;
    }

    protected Rectangle getWeaponBoundingBox(String weapon) {
        Rectangle rect = animation.getBoundingBox(weapon);
        if(rect != null) {
            return rect;
        }
        return null;
    }

    public SpriterAnimation getAnimation() {
        return animation;
    }

    public void setStopAttackingListener(AttackingComponent component) {
        if(attackComponentListener == null) {
            attackComponentListener = new AttackComponentListener(component);
            animation.setControllerListener(attackComponentListener);
        }
    }

    protected class AttackComponentListener implements Player.PlayerListener {

        private AttackingComponent attackingComponent;

        public AttackComponentListener(AttackingComponent attackingComponent) {
            this.attackingComponent = attackingComponent;
        }

        @Override
        public void animationFinished(Animation animation) {
            attackingComponent.stopAttacking();
        }

        @Override
        public void animationChanged(Animation oldAnim, Animation newAnim) {

        }

        @Override
        public void preProcess(Player player) {

        }

        @Override
        public void postProcess(Player player) {

        }

        @Override
        public void mainlineKeyChanged(Mainline.Key prevKey, Mainline.Key newKey) {

        }
    }
}
