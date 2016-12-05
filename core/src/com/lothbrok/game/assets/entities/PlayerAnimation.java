package com.lothbrok.game.assets.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.lothbrok.game.assets.spriter.SpriterAnimation;
import com.lothbrok.game.assets.utils.AssetsConstants;

public class PlayerAnimation extends EntityAnimation {

    private Vector2 treasurePosition;
    private Vector2 treasureDirection;

    public PlayerAnimation(SpriterAnimation animation) {
        super(animation, AssetsConstants.PLAYER_ANIMATION_ENTITY_PLAYER, AssetsConstants.PLAYER_ANIMATION_IDLE);
        treasurePosition = new Vector2();
        treasureDirection = new Vector2();
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

    public Vector2 getTreasurePosition() {
        Rectangle rect = animation.getBoundingBox(AssetsConstants.PLAYER_ANIMATION_SPRITE_CHEST_TOP);
        treasurePosition.x = rect.x + rect.width/2f;
        treasurePosition.y = rect.y;
        return treasurePosition;
    }

    public Vector2 getTreasureDirection() {
        treasureDirection.x = treasurePosition.x;
        treasureDirection.y = treasurePosition.y;
        return treasureDirection.nor();
    }

    public void attackWhileMoving() {
        animation.setPlayerTweener(AssetsConstants.PLAYER_ANIMATION_ATTACKING, AssetsConstants.PLAYER_ANIMATION_WALKING, AssetsConstants
                .PLAYER_ANIMATION_BONE_ATTACK);
    }
}
