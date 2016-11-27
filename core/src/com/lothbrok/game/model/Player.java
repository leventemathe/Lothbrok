package com.lothbrok.game.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.lothbrok.game.model.box2d.util.Box2DShapeFactory;

public class Player {

    private Body playerBody;

    public Player(World world, float x, float y) {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(x, y);
        playerBody = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();

        // player
        Rectangle rect = new Rectangle(0f, 0f, 1f, 1f);
        PolygonShape shape = Box2DShapeFactory.getRectangle(rect, false);
        fdef.shape = shape;
//        fdef.filter.categoryBits = com.lothbrok.game.model.box2d.util.Box2DUtils.BIT_PLAYER;
//        fdef.filter.maskBits = com.lothbrok.game.model.box2d.util.Box2DUtils.BIT_GROUND;
        playerBody.createFixture(fdef).setUserData("player");

        // foot sensor
        shape.setAsBox(0.4f, 0.1f, new Vector2(0.5f, 0f), 0);
        fdef.shape = shape;
//        fdef.filter.categoryBits = com.lothbrok.game.model.box2d.util.Box2DUtils.BIT_PLAYER;
//        fdef.filter.maskBits = com.lothbrok.game.model.box2d.util.Box2DUtils.BIT_GROUND;
        fdef.isSensor = true;
        playerBody.createFixture(fdef).setUserData("foot");

        shape.dispose();
    }
}
