package com.lothbrok.game.model.box2d.util;

import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Box2DShapeFactory {

    private Box2DShapeFactory() {}

    public static PolygonShape getRectangle(Rectangle rectangle, boolean scale) {
        PolygonShape polygon = new PolygonShape();
        Vector2 size;
        float hX, hY;
        if(scale) {
            size = new Vector2(
                    TiledUtils.toWorld(rectangle.x + rectangle.width * 0.5f),
                    TiledUtils.toWorld(rectangle.y + rectangle.height * 0.5f)
            );
            hX = TiledUtils.toWorld(rectangle.width * 0.5f);
            hY = TiledUtils.toWorld(rectangle.height * 0.5f);
        } else {
            size = new Vector2(
                    rectangle.x + rectangle.width * 0.5f,
                    rectangle.y + rectangle.height * 0.5f
            );
            hX = rectangle.width * 0.5f;
            hY = rectangle.height * 0.5f;
        }

        polygon.setAsBox(
                hX,
                hY,
                size,
                0.0f
        );

        return polygon;
    }

    public static ChainShape getPolyline(Polyline polyline, boolean scale) {
        ChainShape chain = new ChainShape();
        float[] vertices = polyline.getTransformedVertices();

        if(scale) {
            Vector2[] worldVertices = new Vector2[vertices.length / 2];

            for (int i = 0; i < vertices.length / 2; ++i) {
                worldVertices[i] = new Vector2();
                worldVertices[i].x = TiledUtils.toWorld(vertices[i * 2]);
                worldVertices[i].y = TiledUtils.toWorld(vertices[i * 2 + 1]);
            }

            chain.createChain(worldVertices);
        } else {
            chain.createChain(vertices);
        }
        return chain;
    }
}
