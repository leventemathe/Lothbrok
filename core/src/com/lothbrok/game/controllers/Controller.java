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

    private Map<Keys, Boolean> keyBools;

    public Controller(ExtendedCamera camera, Player player) {
        this.camera = camera;
        this.player = player;

        this.keyBools = new EnumMap<>(Keys.class);
        keyBools.put(Keys.MOVELEFT, false);
        keyBools.put(Keys.MOVERIGHT, false);
        keyBools.put(Keys.ATTACK, false);
        keyBools.put(Keys.JUMP, false);
        keyBools.put(Keys.CAMERA_MOVELEFT, false);
        keyBools.put(Keys.CAMERA_MOVERIGHT, false);
        keyBools.put(Keys.CAMERA_MOVEUP, false);
        keyBools.put(Keys.CAMERA_MOVEDOWN, false);
        keyBools.put(Keys.CAMERA_ZOOM_IN, false);
        keyBools.put(Keys.CAMERA_ZOOM_OUT, false);
        keyBools.put(Keys.CAMERA_DEBUG_MODE, false);
    }

    public void control(float deltaTime) {
        // player
        if(keyBools.get(Keys.MOVELEFT)) {
            player.moveLeft(deltaTime);
        }
        if(keyBools.get(Keys.MOVERIGHT)) {
            player.moveRight(deltaTime);
        }
        if(keyBools.get(Keys.ATTACK)) {
            player.startAttacking(deltaTime);
        }
        if(keyBools.get(Keys.JUMP)) {
            player.jump(deltaTime);
        }

        // camera
        if(keyBools.get(Keys.CAMERA_MOVEDOWN)) {
            camera.moveDown(deltaTime);
        }
        if(keyBools.get(Keys.CAMERA_MOVEUP)) {
            camera.moveUp(deltaTime);
        }
        if(keyBools.get(Keys.CAMERA_MOVELEFT)) {
            camera.moveLeft(deltaTime);
        }
        if(keyBools.get(Keys.CAMERA_MOVERIGHT)) {
            camera.moveRight(deltaTime);
        }
        if(keyBools.get(Keys.CAMERA_ZOOM_IN)) {
            camera.zoomIn(deltaTime);
        }
        if(keyBools.get(Keys.CAMERA_ZOOM_OUT)) {
            camera.zoomOut(deltaTime);
        }
        if(keyBools.get(Keys.CAMERA_DEBUG_MODE)) {
            GameScreen.debugCamera = !GameScreen.debugCamera;
            keyBools.put(Keys.CAMERA_DEBUG_MODE, false);
        }
    }

    public void attack(boolean b) {
        keyBools.put(Keys.ATTACK, b);
    }

    public void jump(boolean b) {
        keyBools.put(Keys.JUMP, b);
    }

    public void moveLeft(boolean b) {
        keyBools.put(Keys.MOVELEFT, b);
    }

    public void moveRight(boolean b) {
        keyBools.put(Keys.MOVERIGHT, b);
    }

    public void cameraMoveDown(boolean b) {
        keyBools.put(Keys.CAMERA_MOVEDOWN, b);
    }

    public void cameraMoveUp(boolean b) {
        keyBools.put(Keys.CAMERA_MOVEUP, b);
    }

    public void cameraMoveLeft(boolean b) {
        keyBools.put(Keys.CAMERA_MOVELEFT, b);
    }

    public void cameraMoveRight(boolean b) {
        keyBools.put(Keys.CAMERA_MOVERIGHT, b);
    }

    public void cameraZoomIn(boolean b) {
        keyBools.put(Keys.CAMERA_ZOOM_IN, b);
    }

    public void cameraZoomOut(boolean b) {
        keyBools.put(Keys.CAMERA_ZOOM_OUT, b);
    }

    public void cameraDebugModeOn(boolean b) {
        keyBools.put(Keys.CAMERA_DEBUG_MODE, b);
    }
}
