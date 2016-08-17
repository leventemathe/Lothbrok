package com.lothbrok.game.controllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.lothbrok.game.controllers.commands.Command;

import java.util.EnumMap;
import java.util.Map;

public class GameInputProcessor implements InputProcessor {

    private MovingEntityController playerController;

    private enum Keys {
        MOVELEFT,
        MOVERIGHT;

        public static int getSize() {
            return values().length;
        }
    }

    private Map<Keys, Boolean> keyBools;

    public GameInputProcessor(MovingEntityController playerController) {
        this.playerController = playerController;
        this.keyBools = new EnumMap<Keys, Boolean>(Keys.class);
        keyBools.put(Keys.MOVELEFT, false);
        keyBools.put(Keys.MOVERIGHT, false);
    }

    public void handleInput() {
        if(keyBools.get(Keys.MOVELEFT)) {
            playerController.addCommand(new Command.MoveLeft());
        }
        if(keyBools.get(Keys.MOVERIGHT)) {
            playerController.addCommand(new Command.MoveRight());
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        //TODO mappable keyBools
        if(keycode == Input.Keys.W) {
            //TODO change jump to keyBools too
            playerController.addCommand(new Command.Jump());
        }
        if(keycode == Input.Keys.A) {
            keyBools.put(Keys.MOVELEFT, true);
        }
        if(keycode == Input.Keys.D) {
            keyBools.put(Keys.MOVERIGHT, true);
        }

        //camera
        /*
        if(keycode == Input.Keys.LEFT) {
            camera.translate(-0.2f, 0f);
        }
        if(keycode == Input.Keys.RIGHT) {
            camera.translate(0.2f, 0f);
        }
        if(keycode == Input.Keys.UP) {
            camera.translate(0f, 0.2f);
        }
        if(keycode == Input.Keys.DOWN) {
            camera.translate(0f, -0.2f);
        }
        */

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.A) {
            keyBools.put(Keys.MOVELEFT, false);
        }
        if(keycode == Input.Keys.D) {
            keyBools.put(Keys.MOVERIGHT, false);
        }
        return false;
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
