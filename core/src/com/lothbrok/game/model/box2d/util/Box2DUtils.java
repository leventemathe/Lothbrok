package com.lothbrok.game.model.box2d.util;

import com.badlogic.gdx.math.Vector2;

public class Box2DUtils {

    public static final short BIT_GROUND = 2;
    public static final short BIT_PLAYER = 4;

    private static float pixelsPerUnit = 540f;//xl

    private Box2DUtils() {}

    public static float toWorld(float unit) {
        return unit / pixelsPerUnit;
    }

    public static Vector2 toWorld(Vector2 units) {
        return new Vector2(toWorld(units.x), toWorld(units.y));
    }

    public static float toPixels(float unit) {
        return unit * pixelsPerUnit;
    }

    public static Vector2 toPixels(Vector2 units) {
        return new Vector2(toPixels(units.x), toPixels(units.y));
    }
}
