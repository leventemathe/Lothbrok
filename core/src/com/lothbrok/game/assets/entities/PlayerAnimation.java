package com.lothbrok.game.assets.entities;

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

    public SpriterAnimation getAnimation() {
        return animation;
    }

    @Override
    public void dispose() {
        animation.dispose();
    }
}
