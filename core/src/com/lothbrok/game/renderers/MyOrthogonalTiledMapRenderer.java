package com.lothbrok.game.renderers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

public class MyOrthogonalTiledMapRenderer extends OrthogonalTiledMapRenderer {


    private static final String TAG = MyOrthogonalTiledMapRenderer.class.getSimpleName();

    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public MyOrthogonalTiledMapRenderer(TiledMap map) {
        super(map);
    }

    public MyOrthogonalTiledMapRenderer(TiledMap map, Batch batch) {
        super(map, batch);
    }

    public MyOrthogonalTiledMapRenderer(TiledMap map, float unitScale) {
        super(map, unitScale);
    }

    public MyOrthogonalTiledMapRenderer(TiledMap map, float unitScale, Batch batch) {
        super(map, unitScale, batch);
    }

    @Override
    public void setView(OrthographicCamera camera) {
        super.setView(camera);
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

    @Override
    //TODO optimize, so only visible is drawn
    //TODO move shapeRenderer begin end to outseid the for loop in renderObjectS
    public void renderObject(MapObject object) {
        if (object instanceof RectangleMapObject) {
            RectangleMapObject rectObject = (RectangleMapObject) object;

            final String typeString = "type";
            Object typeProperty = rectObject.getProperties().get(typeString);
            if (typeProperty == null) {
                return;
            }

            String type = typeProperty.toString();
            Rectangle rectangle = rectObject.getRectangle();
            batch.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            //TODO get Color from somewhere
            if (type.equals("closeHill")) {
                shapeRenderer.setColor(new Color(47f/255f, 173f/255f, 33f/255f, 1f));
                shapeRenderer.rect(rectangle.x * unitScale, rectangle.y * unitScale,
                        rectangle.width * unitScale, rectangle.height * unitScale);
            } else if (type.equals("cloud")) {
                shapeRenderer.setColor(new Color(230f/255f, 244f/255f, 250f/255f, 1f));
                shapeRenderer.rect(rectangle.x * unitScale, rectangle.y * unitScale,
                        rectangle.width * unitScale, rectangle.height * unitScale);
            }
            shapeRenderer.end();
            batch.begin();
        } else if (object instanceof TextureMapObject) {
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
                    textureObject.getRotation()
            );
        }
    }
}
