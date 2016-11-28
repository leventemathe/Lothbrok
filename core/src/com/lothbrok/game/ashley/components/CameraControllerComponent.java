package com.lothbrok.game.ashley.components;

import com.badlogic.ashley.core.Component;

import java.util.Arrays;

public class CameraControllerComponent implements Component{

    public static final int MOVE_LEFT = 0;
    public static final int MOVE_RIGHT = 1;
    public static final int MOVE_DOWN = 2;
    public static final int MOVE_UP = 3;
    public static final int ZOOM_IN = 4;
    public static final int ZOOM_OUT = 5;

    public static final int SIZE = 6;

    public boolean[] commands;

    public CameraControllerComponent() {
        commands = new boolean[SIZE];
        Arrays.fill(commands, false);
    }
}
