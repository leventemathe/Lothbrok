package com.lothbrok.game.model.tiled;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class TiledUtils {

    private static float pixelsPerUnit = 540f;//xl

    private TiledUtils() {}

    public static float toWorld(float unit) {
        return unit / pixelsPerUnit;
    }

    public static Vector2 toWorld(Vector2 units) {
        return new Vector2(toWorld(units.x), toWorld(units.y));
    }

    public static Rectangle toWorld(Rectangle rectangle) {
        Rectangle result = new Rectangle();
        result.x = rectangle.x / pixelsPerUnit;
        result.y = rectangle.y / pixelsPerUnit;
        result.width = rectangle.width / pixelsPerUnit;
        result.height = rectangle.height / pixelsPerUnit;
        return result;
    }

    public static float toPixels(float unit) {
        return unit * pixelsPerUnit;
    }

    public static Vector2 toPixels(Vector2 units) {
        return new Vector2(toPixels(units.x), toPixels(units.y));
    }
}
