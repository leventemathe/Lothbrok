package com.lothbrok.game.controllers.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class PCInput implements InputProcessor {

    private InputToControllerProcessor inputToControllerProcessor;

    public PCInput(InputToControllerProcessor inputToControllerProcessor) {
        this.inputToControllerProcessor = inputToControllerProcessor;
    }

    @Override
    public boolean keyDown(int keycode) {
        //TODO mappable keyBools
        if(keycode == Input.Keys.H) {
            inputToControllerProcessor.attack(true);
        }
        if(keycode == Input.Keys.A) {
            inputToControllerProcessor.moveLeft(true);
        }
        if(keycode == Input.Keys.D) {
            inputToControllerProcessor.moveRight(true);
        }
        if(keycode == Input.Keys.W) {
            inputToControllerProcessor.jump(true);
        }

        if(keycode == Input.Keys.UP) {
            inputToControllerProcessor.cameraMoveUp(true);
        }
        if(keycode == Input.Keys.DOWN) {
            inputToControllerProcessor.cameraMoveDown(true);
        }
        if(keycode == Input.Keys.LEFT) {
            inputToControllerProcessor.cameraMoveLeft(true);
        }
        if(keycode == Input.Keys.RIGHT) {
            inputToControllerProcessor.cameraMoveRight(true);
        }
        if(keycode == Input.Keys.X) {
            inputToControllerProcessor.cameraZoomIn(true);
        }
        if(keycode == Input.Keys.C) {
            inputToControllerProcessor.cameraZoomOut(true);
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.A) {
            inputToControllerProcessor.moveLeft(false);
        }
        if(keycode == Input.Keys.D) {
            inputToControllerProcessor.moveRight(false);
        }
        if(keycode == Input.Keys.W) {
            inputToControllerProcessor.jump(false);
        }

        if(keycode == Input.Keys.UP) {
            inputToControllerProcessor.cameraMoveUp(false);
        }
        if(keycode == Input.Keys.DOWN) {
            inputToControllerProcessor.cameraMoveDown(false);
        }
        if(keycode == Input.Keys.LEFT) {
            inputToControllerProcessor.cameraMoveLeft(false);
        }
        if(keycode == Input.Keys.RIGHT) {
            inputToControllerProcessor.cameraMoveRight(false);
        }
        if(keycode == Input.Keys.X) {
            inputToControllerProcessor.cameraZoomIn(false);
        }
        if(keycode == Input.Keys.C) {
            inputToControllerProcessor.cameraZoomOut(false);
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
