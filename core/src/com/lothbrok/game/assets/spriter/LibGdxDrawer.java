package com.lothbrok.game.assets.spriter;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.Loader;
import com.brashmonkey.spriter.Timeline;

//TODO this was written by trixt0r, but I wrote ScalingDrawer, and I changed some of it, should I comment it here?
public class LibGdxDrawer extends Drawer<Sprite> {

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;

	public LibGdxDrawer(Loader<Sprite> loader){
		super(loader);
	}

	@Override
	public void setColor(float r, float g, float b, float a) {
		shapeRenderer.setColor(r, g, b, a);
	}

	@Override
	public void rectangle(float x, float y, float width, float height) {
        spriteBatch.end();
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.rect(x, y, width, height);
		shapeRenderer.end();
        spriteBatch.begin();
	}

	@Override
	public void line(float x1, float y1, float x2, float y2) {
        spriteBatch.end();
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.line(x1, y1, x2, y2);
		shapeRenderer.end();
        spriteBatch.begin();
	}

	@Override
	public void circle(float x, float y, float radius) {
        spriteBatch.end();
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.circle(x, y, radius);
		shapeRenderer.end();
        spriteBatch.begin();
	}

	@Override
	public void draw(Timeline.Key.Object object) {
		Sprite sprite = loader.get(object.ref);

		float scaleX = object.scale.x;
		float scaleY = object.scale.y;
		sprite.setScale(scaleX, scaleY);

		float newPivotX = (sprite.getWidth() * object.pivot.x);
		float newX = object.position.x - newPivotX;
		float newPivotY = (sprite.getHeight() * object.pivot.y);
		float newY = object.position.y - newPivotY;

		sprite.setX(newX);
		sprite.setY(newY);

		sprite.setOrigin(newPivotX, newPivotY);
		sprite.setRotation(object.angle);

		sprite.draw(spriteBatch);;
	}

    public void setSpriteBatch(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }

    public void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }
}
