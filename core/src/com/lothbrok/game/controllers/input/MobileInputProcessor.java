package com.lothbrok.game.controllers.input;

import com.lothbrok.game.controllers.Controller;
import com.lothbrok.game.controllers.commands.Command;
import com.lothbrok.game.model.entities.MovingEntity;
import com.lothbrok.game.renderers.ExtendedCamera;

public class MobileInputProcessor extends AbstractInputProcessor {

    public MobileInputProcessor(Controller<MovingEntity, Command<MovingEntity>> playerController, Controller<ExtendedCamera, Command<ExtendedCamera>> cameraController) {
        super(playerController, cameraController);
    }
}
