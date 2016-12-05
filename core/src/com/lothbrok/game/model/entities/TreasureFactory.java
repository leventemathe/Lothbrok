package com.lothbrok.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.DynamicBody;

public class TreasureFactory {

    private Body body;

    private TreasureFactory() {}

    public static Body createTreasure(Vector2 position, World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = DynamicBody;
        bodyDef.position.x = position.x;
        bodyDef.position.y = position.y;
        Body body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1f;
        fixtureDef.restitution = 0.4f;
        body.createFixture(fixtureDef);

        //shape.dispose();
        return body;
    }
}
