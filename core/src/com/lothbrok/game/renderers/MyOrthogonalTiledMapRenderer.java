package com.lothbrok.game.renderers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.lothbrok.game.constants.Colors;
import com.lothbrok.game.constants.TiledConstants;

public class MyOrthogonalTiledMapRenderer extends OrthogonalTiledMapRenderer {


    private static final String TAG = MyOrthogonalTiledMapRenderer.class.getSimpleName();

    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public MyOrthogonalTiledMapRenderer(TiledMap map, float unitScale, Batch batch, ShapeRenderer shapeRenderer) {
        super(map, unitScale, batch);
        this.shapeRenderer = shapeRenderer;
    }

    @Override
    public void setView(OrthographicCamera camera) {
        super.setView(camera);
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

    @Override
    //TODO optimize, so only visible is drawn
    public void renderObject(MapObject object) {
        if (object instanceof TextureMapObject) {
            TextureMapObject textureObject = (TextureMapObject) object;
            float x = textureObject.getX() * unitScale;
            float y = textureObject.getY() * unitScale;
            float originX = textureObject.getOriginX() * unitScale;
            float originY = textureObject.getOriginY() * unitScale;
            float width = textureObject.getTextureRegion().getRegionWidth() * unitScale;
            float height = textureObject.getTextureRegion().getRegionHeight() * unitScale;
            float scaleX = textureObject.getScaleX();
            float scaleY = textureObject.getScaleY();
            //Gdx.app.debug(TAG, "object: " + x + ", " + y + ", " + width + ", " + height);
            batch.draw(
                    textureObject.getTextureRegion(),
                    x,
                    y,
                    originX,
                    originY,
                    width,
                    height,
                    scaleX,
                    scaleY,
                    -textureObject.getRotation()
            );
        } else if (object instanceof RectangleMapObject) {
            RectangleMapObject rectObject = (RectangleMapObject) object;

            Object typeProperty = rectObject.getProperties().get(TiledConstants.TYPE);
            if (typeProperty == null) {
                return;
            }

            String type = typeProperty.toString();
            Rectangle rectangle = rectObject.getRectangle();
            batch.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            if (type.equals(TiledConstants.TYPE_HILL)) {
                shapeRenderer.setColor(Colors.HILL_GREEN);
                shapeRenderer.rect(rectangle.x * unitScale, rectangle.y * unitScale,
                        rectangle.width * unitScale, rectangle.height * unitScale);
            } else if (type.equals(TiledConstants.TYPE_MOUNTAIN)) {
                shapeRenderer.setColor(Colors.MOUNTAIN_BLUE);
                shapeRenderer.rect(rectangle.x * unitScale, rectangle.y * unitScale,
                        rectangle.width * unitScale, rectangle.height * unitScale);
            } else if (type.equals(TiledConstants.TYPE_CLOUD)) {
                shapeRenderer.setColor(Colors.CLOUD_BLUE);
                shapeRenderer.rect(rectangle.x * unitScale, rectangle.y * unitScale,
                        rectangle.width * unitScale, rectangle.height * unitScale);
            }
            shapeRenderer.end();
            batch.begin();
        }
    }
}
