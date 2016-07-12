package com.lothbrok.game.assets.animation;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;

//TODO do i even need this?
public abstract class AbstractAnimation implements Disposable {
    public abstract void addEntity(String entity);
    public abstract void play(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, String entity, String animation);
}
