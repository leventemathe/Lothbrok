package com.lothbrok.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.DynamicBody;

public class Treasure {

    private Body body;
    private float timer = 0f;
    private final float LIFE_SPAN = 5f;

    public Treasure(Vector2 position, World world, Shape shape) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = DynamicBody;
        bodyDef.position.x = position.x;
        bodyDef.position.y = position.y;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1;
        fixtureDef.restitution = 0.4f;
        body.createFixture(fixtureDef);
    }

    public void update(float deltaTime) {
        timer += deltaTime;
    }

    public boolean isTimeUp() {
        return timer >= LIFE_SPAN;
    }

    public Body getBody() {
        return body;
    }
}
