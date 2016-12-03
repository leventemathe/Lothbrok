package com.lothbrok.game.controllers;

import com.lothbrok.game.model.entities.Player;
import com.lothbrok.game.renderers.ExtendedCamera;
import com.lothbrok.game.screens.GameScreen;

import java.util.EnumMap;
import java.util.Map;

//TODO common superclass with AI
//TODO or at lease do separate camera and player controller
public class Controller {

    private ExtendedCamera camera;
    private Player player;

    private enum Commands {
        MOVELEFT,
        MOVERIGHT,
        ATTACK,
        JUMP,
        CAMERA_MOVELEFT,
        CAMERA_MOVERIGHT,
        CAMERA_MOVEUP,
        CAMERA_MOVEDOWN,
        CAMERA_ZOOM_IN,
        CAMERA_ZOOM_OUT,
        CAMERA_DEBUG_MODE;

        public static int getSize() {
            return values().length;
        }
    }

    private Map<Commands, Boolean> commands;

    public Controller(ExtendedCamera camera, Player player) {
        this.camera = camera;
        this.player = player;

        this.commands = new EnumMap<>(Commands.class);
        commands.put(Commands.MOVELEFT, false);
        commands.put(Commands.MOVERIGHT, false);
        commands.put(Commands.ATTACK, false);
        commands.put(Commands.JUMP, false);
        commands.put(Commands.CAMERA_MOVELEFT, false);
        commands.put(Commands.CAMERA_MOVERIGHT, false);
        commands.put(Commands.CAMERA_MOVEUP, false);
        commands.put(Commands.CAMERA_MOVEDOWN, false);
        commands.put(Commands.CAMERA_ZOOM_IN, false);
        commands.put(Commands.CAMERA_ZOOM_OUT, false);
        commands.put(Commands.CAMERA_DEBUG_MODE, false);
    }

    public void control(float deltaTime) {
        // player
        if(commands.get(Commands.MOVELEFT)) {
            player.moveLeft(deltaTime);
        }
        if(commands.get(Commands.MOVERIGHT)) {
            player.moveRight(deltaTime);
        }
        if(commands.get(Commands.ATTACK)) {
            player.startAttacking();
            commands.put(Commands.ATTACK, false);
        }
        if(commands.get(Commands.JUMP)) {
            player.jump(deltaTime);
        }

        // camera
        if(GameScreen.debugCamera) {
            if (commands.get(Commands.CAMERA_MOVEDOWN)) {
                camera.moveDown(deltaTime);
            }
            if (commands.get(Commands.CAMERA_MOVEUP)) {
                camera.moveUp(deltaTime);
            }
            if (commands.get(Commands.CAMERA_MOVELEFT)) {
                camera.moveLeft(deltaTime);
            }
            if (commands.get(Commands.CAMERA_MOVERIGHT)) {
                camera.moveRight(deltaTime);
            }
            if (commands.get(Commands.CAMERA_ZOOM_IN)) {
                camera.zoomIn(deltaTime);
            }
            if (commands.get(Commands.CAMERA_ZOOM_OUT)) {
                camera.zoomOut(deltaTime);
            }
        }
        if (commands.get(Commands.CAMERA_DEBUG_MODE)) {
            GameScreen.debugCamera = !GameScreen.debugCamera;
            commands.put(Commands.CAMERA_DEBUG_MODE, false);
        }
    }

    public void attack(boolean b) {
        commands.put(Commands.ATTACK, b);
    }

    public void jump(boolean b) {
        commands.put(Commands.JUMP, b);
    }

    public void moveLeft(boolean b) {
        commands.put(Commands.MOVELEFT, b);
    }

    public void moveRight(boolean b) {
        commands.put(Commands.MOVERIGHT, b);
    }

    public void cameraMoveDown(boolean b) {
        commands.put(Commands.CAMERA_MOVEDOWN, b);
    }

    public void cameraMoveUp(boolean b) {
        commands.put(Commands.CAMERA_MOVEUP, b);
    }

    public void cameraMoveLeft(boolean b) {
        commands.put(Commands.CAMERA_MOVELEFT, b);
    }

    public void cameraMoveRight(boolean b) {
        commands.put(Commands.CAMERA_MOVERIGHT, b);
    }

    public void cameraZoomIn(boolean b) {
        commands.put(Commands.CAMERA_ZOOM_IN, b);
    }

    public void cameraZoomOut(boolean b) {
        commands.put(Commands.CAMERA_ZOOM_OUT, b);
    }

    public void cameraDebugModeOn(boolean b) {
        commands.put(Commands.CAMERA_DEBUG_MODE, b);
    }
}
