package com.lothbrok.game.assets.entities;

import com.badlogic.gdx.utils.Disposable;
import com.lothbrok.game.assets.animation.AbstractAnimation;
import com.lothbrok.game.assets.AssetsConstants;

public class PlayerAssets implements Disposable {
    private AbstractAnimation animation;

    public PlayerAssets(AbstractAnimation animation) {
        init(animation);
    }

    private void init(AbstractAnimation animation) {
        this.animation = animation;
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
