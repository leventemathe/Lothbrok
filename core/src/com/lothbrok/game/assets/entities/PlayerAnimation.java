package com.lothbrok.game.assets.entities;

import com.badlogic.gdx.math.Rectangle;
import com.lothbrok.game.assets.spriter.SpriterAnimation;
import com.lothbrok.game.assets.utils.AssetsConstants;

public class PlayerAnimation extends EntityAnimation {

    public PlayerAnimation(SpriterAnimation animation) {
        super(animation, AssetsConstants.PLAYER_ANIMATION_ENTITY_PLAYER, AssetsConstants.PLAYER_ANIMATION_IDLE);
    }

    @Override
    public Rectangle getBodyBoundingBox() {
        return getBodyBoundingBox(AssetsConstants.PLAYER_ANIMATION_SPRITE_BODY,
                AssetsConstants.PLAYER_ANIMATION_SPRITE_LEFT_LEG,
                AssetsConstants.PLAYER_ANIMATION_BOTTOM_DELTA,
                AssetsConstants.PLAYER_ANIMATION_TOP_DELTA);
    }

    @Override
    public Rectangle getWeaponBoundingBox() {
        return getWeaponBoundingBox(AssetsConstants.PLAYER_ANIMATION_SPRITE_WEAPON);
    }

    @Override
    public Rectangle getFootSensor() {
        return getFootSensor(AssetsConstants.PLAYER_ANIMATION_SPRITE_LEFT_LEG,
                AssetsConstants.PLAYER_ANIMATION_SPRITE_RIGHT_LEG,
                AssetsConstants.PLAYER_ANIMATION_BOTTOM_DELTA);
    }

    public void attackWhileMoving() {
        animation.setPlayerTweener(AssetsConstants.PLAYER_ANIMATION_ATTACKING, AssetsConstants.PLAYER_ANIMATION_WALKING, AssetsConstants
                .PLAYER_ANIMATION_BONE_ATTACK);
    }
}
