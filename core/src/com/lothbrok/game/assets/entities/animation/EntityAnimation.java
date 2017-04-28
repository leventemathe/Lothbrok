package com.lothbrok.game.assets.entities.animation;

import com.badlogic.gdx.math.Rectangle;
import com.brashmonkey.spriter.Animation;
import com.brashmonkey.spriter.Mainline;
import com.brashmonkey.spriter.Player;
import com.lothbrok.game.assets.spriter.SpriterAnimation;
import com.lothbrok.game.model.entities.components.AttackingComponent;

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

    protected Rectangle bodyRect = new Rectangle();
    protected Rectangle legRect = new Rectangle();
    protected Rectangle bodyBoundingBox = new Rectangle();

    protected Rectangle getBodyBoundingBox(String body, String leg, float bottomDelta, float topDelta) {
        copyRect(bodyRect, body);
        copyRect(legRect, leg);

        if(bodyRect != null && legRect != null) {
            bodyBoundingBox.x = bodyRect.x;
            bodyBoundingBox.y = legRect.y + bottomDelta;
            bodyBoundingBox.width = bodyRect.width;
            bodyBoundingBox.height = bodyRect.height + legRect.height - topDelta;
            return bodyBoundingBox;
        }
        return null;
    }

    protected void copyRect(Rectangle intoRect, String animationPart) {
        intoRect.x = animation.getBoundingBox(animationPart).x;
        intoRect.y = animation.getBoundingBox(animationPart).y;
        intoRect.width = animation.getBoundingBox(animationPart).width;
        intoRect.height = animation.getBoundingBox(animationPart).height;
    }

    protected void copyRect(Rectangle intoRect, Rectangle fromRect) {
        intoRect.x = fromRect.x;
        intoRect.y = fromRect.y;
        intoRect.width = fromRect.width;
        intoRect.height = fromRect.height;
    }

    protected Rectangle leftLegRect = new Rectangle();
    protected Rectangle rightLegRect = new Rectangle();

    protected Rectangle getFootSensor(String leftLeg, String rightLeg, float bottomDelta) {
        copyRect(leftLegRect, leftLeg);
        copyRect(rightLegRect, rightLeg);

        Rectangle bodyRect = getBodyBoundingBox();
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

    protected Rectangle headRect = new Rectangle();

    protected Rectangle getHeadSensor(String head, float delta) {
        copyRect(headRect, head);
        if(headRect != null)  {
            headRect.x = headRect.x + delta/2f;
            headRect.width = headRect.width - delta;
            headRect.height = headRect.height + delta/2f;
            return headRect;
        }
        return null;
    }

    protected Rectangle weaponRect = new Rectangle();

    protected Rectangle getWeaponBoundingBox(String weapon) {
        copyRect(weaponRect, weapon);
        if(weaponRect != null) {
            return weaponRect;
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
