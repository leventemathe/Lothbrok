package com.lothbrok.game.model.tiled;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.lothbrok.game.constants.Resolution;
import com.lothbrok.game.constants.TiledConstants;

public class ParallaxBackground {

    private static final String TAG = ParallaxBackground.class.getSimpleName();
    private MapLayer[] maplayers;

    public ParallaxBackground(Map map) {
        maplayers = new MapLayer[3];
        maplayers[0]= map.getLayers().get(TiledConstants.LAYER_BACKGROUND1);
        maplayers[1]= map.getLayers().get(TiledConstants.LAYER_BACKGROUND2);
        maplayers[2]= map.getLayers().get(TiledConstants.LAYER_BACKGROUND3);

        resetMap();
    }

    private void resetMap() {
        for(int i = 0; i < maplayers.length; i++) {
            for (int j = 0; j < maplayers[i].getObjects().getCount(); j++) {
                MapObject mapObject = maplayers[i].getObjects().get(j);
                if (mapObject instanceof TextureMapObject) {
                    TextureMapObject obj = (TextureMapObject) mapObject;
                    obj.setX((j-1f)*((float)obj.getProperties().get("width")));
                }
            }
        }
    }

    public void update(float speed, float deltaTime) {
        for(int i = 0; i < maplayers.length; i++) {
            for (int j = 0; j < maplayers[i].getObjects().getCount(); j++) {
                MapObject mapObject = maplayers[i].getObjects().get(j);
                if (mapObject instanceof TextureMapObject) {
                    TextureMapObject obj = (TextureMapObject) mapObject;
                    float div = maplayers.length*2f + 3f - i*2f;
                    obj.setX(obj.getX() + speed / div * deltaTime * 1f/Resolution.instance.getWorldScale());
                }
            }
        }
    }
}
