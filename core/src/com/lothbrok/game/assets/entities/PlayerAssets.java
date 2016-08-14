package com.lothbrok.game.assets.entities;

import com.badlogic.gdx.utils.Disposable;
import com.lothbrok.game.assets.AssetsConstants;
import com.lothbrok.game.assets.animation.SpriterAnimation;

public class PlayerAssets implements Disposable {
    private SpriterAnimation animation;

    public PlayerAssets(SpriterAnimation animation) {
        init(animation);
    }

    private void init(SpriterAnimation animation) {
        this.animation = animation;
        this.animation.addEntity(AssetsConstants.PLAYER_ANIMATION_ENTITY);
    }

    @Override
    public void dispose() {
        animation.dispose();
    }

    public SpriterAnimation getAnimation() {
        return animation;
    }
}
