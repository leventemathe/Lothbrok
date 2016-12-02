package com.lothbrok.game.controllers;

import com.lothbrok.game.model.entities.Player;
import com.lothbrok.game.renderers.ExtendedCamera;
import com.lothbrok.game.screens.GameScreen;

import java.util.EnumMap;
import java.util.Map;

//TODO cache commands
public class Controller {

    private ExtendedCamera camera;
    private Player player;

    private enum Keys {
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

    private Map<Keys, Boolean> commands;

    public Controller(ExtendedCamera camera, Player player) {
        this.camera = camera;
        this.player = player;

        this.commands = new EnumMap<>(Keys.class);
        commands.put(Keys.MOVELEFT, false);
        commands.put(Keys.MOVERIGHT, false);
        commands.put(Keys.ATTACK, false);
        commands.put(Keys.JUMP, false);
        commands.put(Keys.CAMERA_MOVELEFT, false);
        commands.put(Keys.CAMERA_MOVERIGHT, false);
        commands.put(Keys.CAMERA_MOVEUP, false);
        commands.put(Keys.CAMERA_MOVEDOWN, false);
        commands.put(Keys.CAMERA_ZOOM_IN, false);
        commands.put(Keys.CAMERA_ZOOM_OUT, false);
        commands.put(Keys.CAMERA_DEBUG_MODE, false);
    }

    public void control(float deltaTime) {
        // player
        if(commands.get(Keys.MOVELEFT)) {
            player.moveLeft(deltaTime);
        }
        if(commands.get(Keys.MOVERIGHT)) {
            player.moveRight(deltaTime);
        }
        if(commands.get(Keys.ATTACK)) {
            player.startAttacking();
            commands.put(Keys.ATTACK, false);
        }
        if(commands.get(Keys.JUMP)) {
            player.jump(deltaTime);
        }

        // camera
        if(GameScreen.debugCamera) {
            if (commands.get(Keys.CAMERA_MOVEDOWN)) {
                camera.moveDown(deltaTime);
            }
            if (commands.get(Keys.CAMERA_MOVEUP)) {
                camera.moveUp(deltaTime);
            }
            if (commands.get(Keys.CAMERA_MOVELEFT)) {
                camera.moveLeft(deltaTime);
            }
            if (commands.get(Keys.CAMERA_MOVERIGHT)) {
                camera.moveRight(deltaTime);
            }
            if (commands.get(Keys.CAMERA_ZOOM_IN)) {
                camera.zoomIn(deltaTime);
            }
            if (commands.get(Keys.CAMERA_ZOOM_OUT)) {
                camera.zoomOut(deltaTime);
            }
        }
        if (commands.get(Keys.CAMERA_DEBUG_MODE)) {
            GameScreen.debugCamera = !GameScreen.debugCamera;
            commands.put(Keys.CAMERA_DEBUG_MODE, false);
        }
    }

    public void attack(boolean b) {
        commands.put(Keys.ATTACK, b);
    }

    public void jump(boolean b) {
        commands.put(Keys.JUMP, b);
    }

    public void moveLeft(boolean b) {
        commands.put(Keys.MOVELEFT, b);
    }

    public void moveRight(boolean b) {
        commands.put(Keys.MOVERIGHT, b);
    }

    public void cameraMoveDown(boolean b) {
        commands.put(Keys.CAMERA_MOVEDOWN, b);
    }

    public void cameraMoveUp(boolean b) {
        commands.put(Keys.CAMERA_MOVEUP, b);
    }

    public void cameraMoveLeft(boolean b) {
        commands.put(Keys.CAMERA_MOVELEFT, b);
    }

    public void cameraMoveRight(boolean b) {
        commands.put(Keys.CAMERA_MOVERIGHT, b);
    }

    public void cameraZoomIn(boolean b) {
        commands.put(Keys.CAMERA_ZOOM_IN, b);
    }

    public void cameraZoomOut(boolean b) {
        commands.put(Keys.CAMERA_ZOOM_OUT, b);
    }

    public void cameraDebugModeOn(boolean b) {
        commands.put(Keys.CAMERA_DEBUG_MODE, b);
    }
}
