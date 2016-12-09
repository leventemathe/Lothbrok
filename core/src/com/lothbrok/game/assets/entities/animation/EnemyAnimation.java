package com.lothbrok.game.assets.entities.animation;

import com.badlogic.gdx.math.Rectangle;
import com.lothbrok.game.assets.spriter.SpriterAnimation;
import com.lothbrok.game.constants.AnimationConstants;

public class EnemyAnimation extends EntityAnimation {

    public EnemyAnimation(SpriterAnimation animation) {
        super(animation, AnimationConstants.ENEMY_ANIMATION_ENTITY_ENEMY, AnimationConstants.ENEMY_ANIMATION_WALKING);
    }

    @Override
    public Rectangle getBodyBoundingBox() {
        return getBodyBoundingBox(AnimationConstants.ENEMY_ANIMATION_SPRITE_BODY,
                AnimationConstants.ENEMY_ANIMATION_SPRITE_LEFT_LEG,
                AnimationConstants.ENEMY_ANIMATION_BOTTOM_DELTA,
                AnimationConstants.ENEMY_ANIMATION_TOP_DELTA);
    }

    @Override
    public Rectangle getWeaponBoundingBox() {
        return getWeaponBoundingBox(AnimationConstants.ENEMY_ANIMATION_SPRITE_WEAPON);
    }

    @Override
    public Rectangle getFootSensor() {
        return getFootSensor(AnimationConstants.ENEMY_ANIMATION_SPRITE_LEFT_LEG,
                AnimationConstants.ENEMY_ANIMATION_SPRITE_RIGHT_LEG,
                AnimationConstants.ENEMY_ANIMATION_BOTTOM_DELTA);
    }
}
