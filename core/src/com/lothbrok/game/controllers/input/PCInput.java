package com.lothbrok.game.controllers.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.lothbrok.game.controllers.Controller;

public class PCInput implements InputProcessor {

    private Controller controller;

    public PCInput(Controller controller) {
        this.controller = controller;
    }

    @Override
    public boolean keyDown(int keycode) {
        //TODO mappable keyBools
        if(keycode == Input.Keys.H) {
            controller.attack(true);
        }
        if(keycode == Input.Keys.A) {
            controller.moveLeft(true);
        }
        if(keycode == Input.Keys.D) {
            controller.moveRight(true);
        }
        if(keycode == Input.Keys.W) {
            controller.jump(true);
        }

        if(keycode == Input.Keys.UP) {
            controller.cameraMoveUp(true);
        }
        if(keycode == Input.Keys.DOWN) {
            controller.cameraMoveDown(true);
        }
        if(keycode == Input.Keys.LEFT) {
            controller.cameraMoveLeft(true);
        }
        if(keycode == Input.Keys.RIGHT) {
            controller.cameraMoveRight(true);
        }
        if(keycode == Input.Keys.X) {
            controller.cameraZoomIn(true);
        }
        if(keycode == Input.Keys.C) {
            controller.cameraZoomOut(true);
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.A) {
            controller.moveLeft(false);
        }
        if(keycode == Input.Keys.D) {
            controller.moveRight(false);
        }
        if(keycode == Input.Keys.W) {
            controller.jump(false);
        }

        if(keycode == Input.Keys.UP) {
            controller.cameraMoveUp(false);
        }
        if(keycode == Input.Keys.DOWN) {
            controller.cameraMoveDown(false);
        }
        if(keycode == Input.Keys.LEFT) {
            controller.cameraMoveLeft(false);
        }
        if(keycode == Input.Keys.RIGHT) {
            controller.cameraMoveRight(false);
        }
        if(keycode == Input.Keys.X) {
            controller.cameraZoomIn(false);
        }
        if(keycode == Input.Keys.C) {
            controller.cameraZoomOut(false);
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
