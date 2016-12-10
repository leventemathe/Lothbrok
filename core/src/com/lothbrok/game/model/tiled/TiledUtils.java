package com.lothbrok.game.model.tiled;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.lothbrok.game.constants.Resolution;

public class TiledUtils {

    private static float scale = Resolution.instance.getWorldScale();

    private TiledUtils() {}

    public static float toWorld(float unit) {
        return unit * scale;
    }

    public static Vector2 toWorld(Vector2 units) {
        return new Vector2(toWorld(units.x), toWorld(units.y));
    }

    public static Rectangle toWorld(Rectangle rectangle) {
        Rectangle result = new Rectangle();
        result.x = rectangle.x * scale;
        result.y = rectangle.y * scale;
        result.width = rectangle.width * scale;
        result.height = rectangle.height * scale;
        return result;
    }

    public static float toPixels(float unit) {
        return unit / scale;
    }

    public static Vector2 toPixels(Vector2 units) {
        return new Vector2(toPixels(units.x), toPixels(units.y));
    }
}
