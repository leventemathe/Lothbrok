package com.lothbrok.game.controllers.input;

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

import java.util.EnumMap;
import java.util.Map;

//TODO cache commands
public class InputToControllerProcessor {

    protected Controller<MovingEntity, Command<MovingEntity>> playerController;
    protected Controller<ExtendedCamera, Command<ExtendedCamera>> cameraController;

    //TODO move this somewhere central
    protected boolean debugMode = true;

    protected enum Keys {
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

    protected Map<Keys, Boolean> keyBools;

    public InputToControllerProcessor(Controller<MovingEntity, Command<MovingEntity>> playerController,
                                      Controller<ExtendedCamera, Command<ExtendedCamera>> cameraController) {
        this.playerController = playerController;
        this.cameraController = cameraController;
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

    public void handleInput() {
        if(keyBools.get(Keys.MOVELEFT)) {
            playerController.addCommand(new MoveLeft());
            cameraController.addCommand(new com.lothbrok.game.controllers.commands.camera.MoveLeft());
        }
        if(keyBools.get(Keys.MOVERIGHT)) {
            playerController.addCommand(new MoveRight());
            cameraController.addCommand(new com.lothbrok.game.controllers.commands.camera.MoveRight());
        }
        if(keyBools.get(Keys.ATTACK)) {
            playerController.addCommand(new StartAttacking());
            attack(false);
        }
        if(keyBools.get(Keys.JUMP)) {
            playerController.addCommand(new Jump());
        }

        if(debugMode) {
            if(keyBools.get(Keys.CAMERA_MOVEDOWN)) {
                cameraController.addCommand(new MoveDown());
            }
            if(keyBools.get(Keys.CAMERA_MOVEUP)) {
                cameraController.addCommand(new MoveUp());
            }
            if(keyBools.get(Keys.CAMERA_MOVELEFT)) {
                cameraController.addCommand(new com.lothbrok.game.controllers.commands.camera.MoveLeft());
            }
            if(keyBools.get(Keys.CAMERA_MOVERIGHT)) {
                cameraController.addCommand(new com.lothbrok.game.controllers.commands.camera.MoveRight());
            }
            if(keyBools.get(Keys.CAMERA_ZOOM_IN)) {
                cameraController.addCommand(new ZoomIn());
            }
            if(keyBools.get(Keys.CAMERA_ZOOM_OUT)) {
                cameraController.addCommand(new ZoomOut());
            }
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
