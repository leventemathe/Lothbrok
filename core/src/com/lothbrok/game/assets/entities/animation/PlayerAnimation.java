package com.lothbrok.game.assets.entities.animation;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.lothbrok.game.assets.spriter.SpriterAnimation;
import com.lothbrok.game.constants.AnimationConstants;

public class PlayerAnimation extends EntityAnimation {

    private Vector2 treasurePosition;
    private Vector2 treasureDirection;

    public PlayerAnimation(SpriterAnimation animation) {
        super(animation, AnimationConstants.PLAYER_ANIMATION_ENTITY_PLAYER, AnimationConstants.PLAYER_ANIMATION_IDLE);
        treasurePosition = new Vector2();
        treasureDirection = new Vector2();
    }

    @Override
    public Rectangle getBodyBoundingBox() {
        return getBodyBoundingBox(AnimationConstants.PLAYER_ANIMATION_SPRITE_BODY,
                AnimationConstants.PLAYER_ANIMATION_SPRITE_LEFT_LEG,
                AnimationConstants.PLAYER_ANIMATION_BOTTOM_DELTA,
                AnimationConstants.PLAYER_ANIMATION_TOP_DELTA);
    }

    @Override
    public Rectangle getWeaponBoundingBox() {
        return getWeaponBoundingBox(AnimationConstants.PLAYER_ANIMATION_SPRITE_WEAPON);
    }

    @Override
    public Rectangle getFootSensor() {
        return getFootSensor(AnimationConstants.PLAYER_ANIMATION_SPRITE_LEFT_LEG,
                AnimationConstants.PLAYER_ANIMATION_SPRITE_RIGHT_LEG,
                AnimationConstants.PLAYER_ANIMATION_BOTTOM_DELTA);
    }

    @Override
    public Rectangle getHeadSensor() {
        return getHeadSensor(AnimationConstants.PLAYER_ANIMATION_SPRITE_HELMET,
                AnimationConstants.PLAYER_ANIMATION_HEAD_DELTA);
    }

    private Rectangle treasureRect = new Rectangle();

    public Vector2 getTreasurePosition() {
        copyRect(treasureRect, AnimationConstants.PLAYER_ANIMATION_SPRITE_CHEST_TOP);
        treasurePosition.x = treasureRect.x + treasureRect.width/2f;
        treasurePosition.y = treasureRect.y;
        return treasurePosition;
    }

    public void attackWhileMoving() {
        animation.setPlayerTweener(AnimationConstants.PLAYER_ANIMATION_ATTACKING, AnimationConstants.PLAYER_ANIMATION_WALKING, AnimationConstants
                .PLAYER_ANIMATION_BONE_ATTACK);
    }
}
