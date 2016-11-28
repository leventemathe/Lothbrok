package com.lothbrok.game.ashley.components;

import com.badlogic.ashley.core.Component;

import java.util.Arrays;

public class PlayerControllerComponent implements Component {

    public static final int MOVE_LEFT = 0;
    public static final int MOVE_RIGHT = 1;
    public static final int JUMP = 2;
    public static final int ATTACK = 3;

    public static final int SIZE = 4;

    public boolean[] commands;

    public PlayerControllerComponent() {
        commands = new boolean[SIZE];
        Arrays.fill(commands, false);
    }
}
