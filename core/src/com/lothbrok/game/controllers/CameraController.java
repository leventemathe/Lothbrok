package com.lothbrok.game.controllers;

import com.lothbrok.game.renderers.ExtendedCamera;
import com.lothbrok.game.screens.GameScreen;

import java.util.EnumMap;
import java.util.Map;

public class CameraController {

    private enum Commands {
        MOVELEFT,
        MOVERIGHT,
        MOVEUP,
        MOVEDOWN,
        ZOOM_IN,
        ZOOM_OUT,
        DEBUG_MODE;

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
        commands.put(CameraController.Commands.DEBUG_MODE, false);
    }

    public void control(float deltaTime, ExtendedCamera camera) {
        if(GameScreen.debugCamera) {
            if (commands.get(CameraController.Commands.MOVEDOWN)) {
                camera.moveDown(deltaTime);
            }
            if (commands.get(CameraController.Commands.MOVEUP)) {
                camera.moveUp(deltaTime);
            }
            if (commands.get(CameraController.Commands.MOVELEFT)) {
                camera.moveLeft(deltaTime);
            }
            if (commands.get(CameraController.Commands.MOVERIGHT)) {
                camera.moveRight(deltaTime);
            }
            if (commands.get(CameraController.Commands.ZOOM_IN)) {
                camera.zoomIn(deltaTime);
            }
            if (commands.get(CameraController.Commands.ZOOM_OUT)) {
                camera.zoomOut(deltaTime);
            }
        }
        if (commands.get(CameraController.Commands.DEBUG_MODE)) {
            GameScreen.debugCamera = !GameScreen.debugCamera;
            commands.put(CameraController.Commands.DEBUG_MODE, false);
        }
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

    public void debugModeOn(boolean b) {
        commands.put(CameraController.Commands.DEBUG_MODE, b);
    }
}
