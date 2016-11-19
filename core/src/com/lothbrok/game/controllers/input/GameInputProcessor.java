package com.lothbrok.game.controllers.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.lothbrok.game.controllers.Controller;
import com.lothbrok.game.controllers.commands.Command;
import com.lothbrok.game.controllers.commands.movingentity.StartAttacking;
import com.lothbrok.game.controllers.commands.movingentity.Jump;
import com.lothbrok.game.controllers.commands.movingentity.MoveLeft;
import com.lothbrok.game.controllers.commands.movingentity.MoveRight;
import com.lothbrok.game.model.entities.MovingEntity;
import com.lothbrok.game.model.entities.Player;

import java.util.EnumMap;
import java.util.Map;

//TODO cache commands
public class GameInputProcessor implements InputProcessor {

    private Controller<MovingEntity, Command<MovingEntity>> playerController;

    private enum Keys {
        MOVELEFT,
        MOVERIGHT,
        ATTACK,
        JUMP;

        public static int getSize() {
            return values().length;
        }
    }

    private Map<Keys, Boolean> keyBools;

    public GameInputProcessor(Controller<MovingEntity, Command<MovingEntity>> playerController) {
        this.playerController = playerController;
        this.keyBools = new EnumMap<>(Keys.class);
        keyBools.put(Keys.MOVELEFT, false);
        keyBools.put(Keys.MOVERIGHT, false);
        keyBools.put(Keys.ATTACK, false);
        keyBools.put(Keys.JUMP, false);
    }

    public void handleInput() {
        if(keyBools.get(Keys.MOVELEFT)) {
            playerController.addCommand(new MoveLeft());
        }
        if(keyBools.get(Keys.MOVERIGHT)) {
            playerController.addCommand(new MoveRight());
        }
        if(keyBools.get(Keys.ATTACK)) {
            playerController.addCommand(new StartAttacking());
            keyBools.put(Keys.ATTACK, false);
        }
        if(keyBools.get(Keys.JUMP)) {
            playerController.addCommand(new Jump());
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        //TODO mappable keyBools
        if(keycode == Input.Keys.H) {
            keyBools.put(Keys.ATTACK, true);
        }
        if(keycode == Input.Keys.A) {
            keyBools.put(Keys.MOVELEFT, true);
        }
        if(keycode == Input.Keys.D) {
            keyBools.put(Keys.MOVERIGHT, true);
        }
        if(keycode == Input.Keys.W) {
            keyBools.put(Keys.JUMP, true);
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
        if(keycode == Input.Keys.W) {
            keyBools.put(Keys.JUMP, false);
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
