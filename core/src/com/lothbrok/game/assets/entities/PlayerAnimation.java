package com.lothbrok.game.assets.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import com.lothbrok.game.assets.animation.spriter.SpriterAnimation;
import com.lothbrok.game.assets.utils.AssetsConstants;


public class PlayerAnimation implements Disposable {

    private SpriterAnimation animation;

    public PlayerAnimation(SpriterAnimation animation) {
        init(animation);
    }

    private void init(SpriterAnimation animation) {
        this.animation = animation;
        this.animation.setCurrentEntity(AssetsConstants.PLAYER_ANIMATION_ENTITY_PLAYER);
        this.animation.setPlayAlways(AssetsConstants.PLAYER_ANIMATION_IDLE);
    }

    public void attackWhileMoving() {
        animation.setPlayerTweener(AssetsConstants.PLAYER_ANIMATION_ATTACKING, AssetsConstants.PLAYER_ANIMATION_WALKING, AssetsConstants
                .PLAYER_ANIMATION_BONE_ATTACK);
    }

    public Rectangle getBodyBoudningBox() {
        Rectangle bodyRect = animation.getBoundingBox(AssetsConstants.PLAYER_ANIMATION_SPRITE_BODY);
        Rectangle legRect = animation.getBoundingBox(AssetsConstants.PLAYER_ANIMATION_SPRITE_LEFT_LEG);
        if(bodyRect != null && legRect != null) {
            return new Rectangle(bodyRect.x,
                    legRect.y + AssetsConstants.PLAYER_ANIMATION_BOTTOM_DELTA,
                    bodyRect.width,
                    bodyRect.height + legRect.height - AssetsConstants.PLAYER_ANIMATION_TOP_DELTAT);
        }
        return null;
    }

    public Rectangle getFootSensor() {
        Rectangle leftLegRect = animation.getBoundingBox(AssetsConstants.PLAYER_ANIMATION_SPRITE_LEFT_LEG);
        Rectangle rightLegRect = animation.getBoundingBox(AssetsConstants.PLAYER_ANIMATION_SPRITE_RIGHT_LEG);
        if(leftLegRect != null && rightLegRect != null) {
            if(!animation.isFlipped()) {
                return new Rectangle(leftLegRect.x,
                        leftLegRect.y,
                        rightLegRect.x + rightLegRect.width - leftLegRect.x,
                        AssetsConstants.PLAYER_ANIMATION_BOTTOM_DELTA);
            }
            return new Rectangle(rightLegRect.x,
                    rightLegRect.y,
                    leftLegRect.x + leftLegRect.width - rightLegRect.x,
                    AssetsConstants.PLAYER_ANIMATION_BOTTOM_DELTA);
        }
        return null;
    }

    public SpriterAnimation getAnimation() {
        return animation;
    }

    @Override
    public void dispose() {
        animation.dispose();
    }
}
