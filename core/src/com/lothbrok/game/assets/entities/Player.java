package com.lothbrok.game.assets.entities;

import com.badlogic.gdx.utils.Disposable;

import com.lothbrok.game.assets.Animation;
import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.assets.Constants;

public class Player extends AbstractEntity implements Disposable {
    private Animation animation;

    public Player(Assets assets) {
        super(assets);
        init();
    }

    private void init() {
        this.animation = new Animation(assets.getSpriteBatch(), assets.getShapeRenderer());
        this.animation.load(Constants.PLAYER_ANIMATION);

        this.animation.addEntity(Constants.PLAYER_WALKING_ANIMATION);
    }

    public void playWalkingAnimation() {
        animation.playEntity(Constants.PLAYER_WALKING_ANIMATION);
    }

    @Override
    public void dispose() {
        animation.dispose();
    }

    public Animation getAnimation() {
        return animation;
    }
}
