package com.lothbrok.game.renderers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MyOrthogonalTiledMapRenderer extends OrthogonalTiledMapRenderer {


    private static final String TAG = MyOrthogonalTiledMapRenderer.class.getSimpleName();

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
    //TODO optimize, so only visible is drawn
    public void renderObject(MapObject object) {
        if (object instanceof TextureMapObject) {
            TextureMapObject textureObject = (TextureMapObject) object;
            float x = textureObject.getX()*unitScale;
            float y = textureObject.getY()*unitScale;
            float originX = textureObject.getOriginX()*unitScale;
            float originY = textureObject.getOriginY()*unitScale;
            float width = textureObject.getTextureRegion().getRegionWidth()*unitScale;
            float height= textureObject.getTextureRegion().getRegionHeight()*unitScale;
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
