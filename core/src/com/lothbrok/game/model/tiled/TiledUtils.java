package com.lothbrok.game.model.tiled;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.lothbrok.game.constants.Resolution;

public class TiledUtils {

    private TiledUtils() {}

    public static float toWorld(float unit) {
        return unit * Resolution.instance.getWorldScale();
    }

    public static Vector2 toWorld(Vector2 units) {
        return new Vector2(toWorld(units.x), toWorld(units.y));
    }

    public static Rectangle toWorld(Rectangle rectangle) {
        Rectangle result = new Rectangle();
        result.x = rectangle.x * Resolution.instance.getWorldScale();
        result.y = rectangle.y * Resolution.instance.getWorldScale();
        result.width = rectangle.width * Resolution.instance.getWorldScale();
        result.height = rectangle.height * Resolution.instance.getWorldScale();
        return result;
    }

    public static float toPixels(float unit) {
        return unit / Resolution.instance.getWorldScale();
    }

    public static Vector2 toPixels(Vector2 units) {
        return new Vector2(toPixels(units.x), toPixels(units.y));
    }
}
