package com.lothbrok.game.assets.animation.spriter;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.Loader;
import com.brashmonkey.spriter.Player;

public abstract class ScalingDrawer<R> extends Drawer<R> {

    SpriteBatch spriteBatch;
    ShapeRenderer shapeRenderer;

    public ScalingDrawer(Loader loader) {
        super(loader);
    }

    public abstract void scale(Player player, float scale);

    public void setSpriteBatch(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }

    public void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }
}
