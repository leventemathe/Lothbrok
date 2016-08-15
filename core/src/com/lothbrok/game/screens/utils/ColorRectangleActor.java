package com.lothbrok.game.screens.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

public class ColorRectangleActor extends Actor implements Disposable {

    private Color color;
    private ShapeRenderer renderer;
    private float x, y, w, h;

    public ColorRectangleActor(Color color, float x, float y, float w, float h) {
        super();
        this.color = color;
        this.renderer = new ShapeRenderer();
        renderer.setColor(color);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.rect(x, y, w, h);
        renderer.end();
        batch.begin();
    }

    public void setRect(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
