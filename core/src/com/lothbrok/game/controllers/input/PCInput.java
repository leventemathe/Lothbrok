package com.lothbrok.game.controllers.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.lothbrok.game.controllers.CameraController;
import com.lothbrok.game.controllers.PlayerController;

public class PCInput implements InputProcessor {

    private PlayerController playerController;
    private CameraController cameraController;

    public PCInput(PlayerController playerController, CameraController cameraController) {
        this.playerController = playerController;
        this.cameraController = cameraController;
    }

    @Override
    public boolean keyDown(int keycode) {
        //TODO mappable keyBools
        if(keycode == Input.Keys.H) {
            playerController.attack(true);
        }
        if(keycode == Input.Keys.A) {
            playerController.moveLeft(true);
        }
        if(keycode == Input.Keys.D) {
            playerController.moveRight(true);
        }
        if(keycode == Input.Keys.W) {
            playerController.jump(true);
        }

        if(keycode == Input.Keys.UP) {
            cameraController.moveUp(true);
        }
        if(keycode == Input.Keys.DOWN) {
            cameraController.moveDown(true);
        }
        if(keycode == Input.Keys.LEFT) {
            cameraController.moveLeft(true);
        }
        if(keycode == Input.Keys.RIGHT) {
            cameraController.moveRight(true);
        }
        if(keycode == Input.Keys.X) {
            cameraController.zoomIn(true);
        }
        if(keycode == Input.Keys.C) {
            cameraController.zoomOut(true);
        }
        if(keycode == Input.Keys.SPACE) {
            cameraController.debugModeOn(true);
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.A) {
            playerController.moveLeft(false);
        }
        if(keycode == Input.Keys.D) {
            playerController.moveRight(false);
        }
        if(keycode == Input.Keys.W) {
            playerController.jump(false);
        }

        if(keycode == Input.Keys.UP) {
            cameraController.moveUp(false);
        }
        if(keycode == Input.Keys.DOWN) {
            cameraController.moveDown(false);
        }
        if(keycode == Input.Keys.LEFT) {
            cameraController.moveLeft(false);
        }
        if(keycode == Input.Keys.RIGHT) {
            cameraController.moveRight(false);
        }
        if(keycode == Input.Keys.X) {
            cameraController.zoomIn(false);
        }
        if(keycode == Input.Keys.C) {
            cameraController.zoomOut(false);
        }
        if(keycode == Input.Keys.SPACE) {
            cameraController.debugModeOn(false);
        }

        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
