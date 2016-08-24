package com.lothbrok.game.assets.animation.spriter;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.Loader;
import com.brashmonkey.spriter.Player;

import java.util.HashMap;
import java.util.Map;

public abstract class ScalingDrawer<R> extends Drawer<R> {

    protected SpriteBatch spriteBatch;
    protected ShapeRenderer shapeRenderer;

    protected Map<Player, Float> scales;

    public ScalingDrawer(Loader loader) {
        super(loader);
        this.scales = new HashMap<>();
    }

    public void setSpriteBatch(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }

    public void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }

    public void setScale(Player player, float scale) {
        if(scales.containsKey(player)) {
            scales.put(player, scale);
        } else {
            scales.put(player, 1f);
        }
        scale(player, scale);
    }

    protected abstract void scale(Player player, float scale);
}
