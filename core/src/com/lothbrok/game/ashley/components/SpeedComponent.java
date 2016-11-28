package com.lothbrok.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class SpeedComponent implements Component {

    public Vector2 speed;

    public SpeedComponent(Vector2 speed) {
        this.speed = speed;
    }
}
