package com.lothbrok.game.model.box2d;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.StaticBody;

public class Box2DCollisionFromTiled {

    private Box2DCollisionFromTiled() {
    }

    public static List<Body> build(Map map, World world) {
        MapObjects objects = map.getLayers().get("collision").getObjects();

        List<Body> bodies = new ArrayList<>();

        for(MapObject object : objects) {
            Shape shape;

            if (object instanceof RectangleMapObject) {
                shape = com.lothbrok.game.model.box2d.util.Box2DShapeFactory.getRectangle(((RectangleMapObject)object).getRectangle(), true);
            } else if(object instanceof PolylineMapObject) {
                shape = com.lothbrok.game.model.box2d.util.Box2DShapeFactory.getPolyline(((PolylineMapObject)object).getPolyline(), true);
            } else {
                continue;
            }

            BodyDef bodyDef = new BodyDef();
            bodyDef.type = StaticBody;
            Body body = world.createBody(bodyDef);
            body.createFixture(shape, 0f);

            bodies.add(body);

            shape.dispose();
        }
        return bodies;
    }
}
