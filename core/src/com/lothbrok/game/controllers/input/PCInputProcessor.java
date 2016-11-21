package com.lothbrok.game.controllers.input;

import com.badlogic.gdx.Input;
import com.lothbrok.game.controllers.Controller;
import com.lothbrok.game.controllers.commands.Command;
import com.lothbrok.game.controllers.commands.camera.MoveDown;
import com.lothbrok.game.controllers.commands.camera.MoveUp;
import com.lothbrok.game.controllers.commands.camera.ZoomIn;
import com.lothbrok.game.controllers.commands.camera.ZoomOut;
import com.lothbrok.game.controllers.commands.movingentity.Jump;
import com.lothbrok.game.controllers.commands.movingentity.MoveLeft;
import com.lothbrok.game.controllers.commands.movingentity.MoveRight;
import com.lothbrok.game.controllers.commands.movingentity.StartAttacking;
import com.lothbrok.game.model.entities.MovingEntity;
import com.lothbrok.game.renderers.ExtendedCamera;

public class PCInputProcessor extends AbstractInputProcessor {

    public PCInputProcessor(Controller<MovingEntity, Command<MovingEntity>> playerController,
                            Controller<ExtendedCamera, Command<ExtendedCamera>> cameraController) {
        super(playerController, cameraController);
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

        if(debugMode) {
            if(keycode == Input.Keys.UP) {
                keyBools.put(Keys.CAMERA_MOVEUP, true);
            }
            if(keycode == Input.Keys.DOWN) {
                keyBools.put(Keys.CAMERA_MOVEDOWN, true);
            }
            if(keycode == Input.Keys.LEFT) {
                keyBools.put(Keys.CAMERA_MOVELEFT, true);
            }
            if(keycode == Input.Keys.RIGHT) {
                keyBools.put(Keys.CAMERA_MOVERIGHT, true);
            }
            if(keycode == Input.Keys.X) {
                keyBools.put(Keys.CAMERA_ZOOM_IN, true);
            }
            if(keycode == Input.Keys.C) {
                keyBools.put(Keys.CAMERA_ZOOM_OUT, true);
            }
        }

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

        if(debugMode) {
            if(keycode == Input.Keys.UP) {
                keyBools.put(Keys.CAMERA_MOVEUP, false);
            }
            if(keycode == Input.Keys.DOWN) {
                keyBools.put(Keys.CAMERA_MOVEDOWN, false);
            }
            if(keycode == Input.Keys.LEFT) {
                keyBools.put(Keys.CAMERA_MOVELEFT, false);
            }
            if(keycode == Input.Keys.RIGHT) {
                keyBools.put(Keys.CAMERA_MOVERIGHT, false);
            }
            if(keycode == Input.Keys.X) {
                keyBools.put(Keys.CAMERA_ZOOM_IN, false);
            }
            if(keycode == Input.Keys.C) {
                keyBools.put(Keys.CAMERA_ZOOM_OUT, false);
            }
        }

        return false;
    }
}
