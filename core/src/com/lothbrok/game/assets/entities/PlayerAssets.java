package com.lothbrok.game.assets.entities;

import com.badlogic.gdx.utils.Disposable;
import com.lothbrok.game.assets.animation.AbstractAnimation;
import com.lothbrok.game.assets.animation.SpriterAnimation;
import com.lothbrok.game.assets.AssetsConstants;

public class PlayerAssets implements Disposable {
    private AbstractAnimation animation;

    public PlayerAssets() {
        init();
    }

    private void init() {
        this.animation = new SpriterAnimation(AssetsConstants.PLAYER_ANIMATION_PATH);
        this.animation.addEntity(AssetsConstants.PLAYER_ANIMATION_ENTITY);
    }

    @Override
    public void dispose() {
        animation.dispose();
    }

    public AbstractAnimation getAnimation() {
        return animation;
    }
}
