package com.lothbrok.game.controllers;

import com.lothbrok.game.renderers.ExtendedCamera;

import java.util.EnumMap;
import java.util.Map;

//TODO cache commands
public class Controller {

    //TODO move this somewhere central
    private boolean debugMode = true;

    private ExtendedCamera camera;

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
        CAMERA_ZOOM_OUT;

        public static int getSize() {
            return values().length;
        }
    }

    private Map<Keys, Boolean> keyBools;

    public Controller(ExtendedCamera camera) {
        this.camera = camera;

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
    }

    public void control(float deltaTime) {
        if(keyBools.get(Keys.MOVELEFT)) {

        }
        if(keyBools.get(Keys.MOVERIGHT)) {

        }
        if(keyBools.get(Keys.ATTACK)) {

        }
        if(keyBools.get(Keys.JUMP)) {

        }

        if(debugMode) {

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
}
