package com.lothbrok.game.assets.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;

import com.lothbrok.game.assets.Animation;
import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.assets.Constants;

public class Player implements Disposable {
    private Animation animation;

    public Player() {
        init();
    }

    private void init() {
        this.animation = new Animation();
        this.animation.load(Constants.PLAYER_ANIMATION);

        this.animation.addEntity(Constants.PLAYER_WALKING_ANIMATION);
    }

    public void playWalkingAnimation(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        animation.playEntity(spriteBatch, shapeRenderer, Constants.PLAYER_WALKING_ANIMATION);
    }

    @Override
    public void dispose() {
        animation.dispose();
    }

    public Animation getAnimation() {
        return animation;
    }
}
