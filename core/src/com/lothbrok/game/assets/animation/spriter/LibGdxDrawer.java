package com.lothbrok.game.assets.animation.spriter;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.Loader;
import com.brashmonkey.spriter.Timeline.Key.Object;

public class LibGdxDrawer extends Drawer<Sprite> {

	SpriteBatch spriteBatch;
	ShapeRenderer shapeRenderer;

	public LibGdxDrawer(Loader<Sprite> loader){
		super(loader);
	}

	@Override
	public void setColor(float r, float g, float b, float a) {
		shapeRenderer.setColor(r, g, b, a);
	}

	@Override
	public void rectangle(float x, float y, float width, float height) {
		shapeRenderer.rect(x, y, width, height);
	}

	@Override
	public void line(float x1, float y1, float x2, float y2) {
		shapeRenderer.line(x1, y1, x2, y2);
	}

	@Override
	public void circle(float x, float y, float radius) {
		shapeRenderer.circle(x, y, radius);
	}

	@Override
	public void draw(Object object) {
		Sprite sprite = loader.get(object.ref);
		float newPivotX = (sprite.getWidth() * object.pivot.x);
		float newX = object.position.x - newPivotX;
		float newPivotY = (sprite.getHeight() * object.pivot.y);
		float newY = object.position.y - newPivotY;

		sprite.setX(newX);
		sprite.setY(newY);

		sprite.setOrigin(newPivotX, newPivotY);
		sprite.setRotation(object.angle);

		sprite.draw(spriteBatch);
	}

	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}

	public void setSpriteBatch(SpriteBatch spriteBatch) {
		this.spriteBatch = spriteBatch;
	}

	public ShapeRenderer getShapeRenderer() {
		return shapeRenderer;
	}

	public void setShapeRenderer(ShapeRenderer shapeRenderer) {
		this.shapeRenderer = shapeRenderer;
	}
}
