package com.lothbrok.game.controllers;

import java.util.EnumMap;
import java.util.Map;

public class CameraController {

    private enum Commands {
        MOVELEFT,
        MOVERIGHT,
        MOVEUP,
        MOVEDOWN,
        ZOOM_IN,
        ZOOM_OUT;

        public static int getSize() {
            return values().length;
        }
    }

    private Map<CameraController.Commands, Boolean> commands;

    public CameraController() {
        this.commands = new EnumMap<>(CameraController.Commands.class);

        commands.put(CameraController.Commands.MOVELEFT, false);
        commands.put(CameraController.Commands.MOVERIGHT, false);
        commands.put(CameraController.Commands.MOVEUP, false);
        commands.put(CameraController.Commands.MOVEDOWN, false);
        commands.put(CameraController.Commands.ZOOM_IN, false);
        commands.put(CameraController.Commands.ZOOM_OUT, false);
    }

    public void moveDown(boolean b) {
        commands.put(CameraController.Commands.MOVEDOWN, b);
    }

    public void moveUp(boolean b) {
        commands.put(CameraController.Commands.MOVEUP, b);
    }

    public void moveLeft(boolean b) {
        commands.put(CameraController.Commands.MOVELEFT, b);
    }

    public void moveRight(boolean b) {
        commands.put(CameraController.Commands.MOVERIGHT, b);
    }

    public void zoomIn(boolean b) {
        commands.put(CameraController.Commands.ZOOM_IN, b);
    }

    public void zoomOut(boolean b) {
        commands.put(CameraController.Commands.ZOOM_OUT, b);
    }
}
