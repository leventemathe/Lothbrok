package com.lothbrok.game.screens.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

public class ColorRectangleActor extends Actor implements Disposable {

    private ShapeRenderer renderer;

    private Color color;
    private Rectangle rectangle;

    public ColorRectangleActor(ShapeRenderer renderer, Color color, Rectangle rectangle) {
        super();
        this.renderer = renderer;
        this.color = color;
        this.rectangle = new Rectangle();
        this.rectangle.x = rectangle.x;
        this.rectangle.y = rectangle.y;
        this.rectangle.width = rectangle.width;
        this.rectangle.height = rectangle.height;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        renderer.setColor(color);
        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        renderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        batch.begin();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
