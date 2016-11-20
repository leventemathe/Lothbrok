package com.lothbrok.game.controllers.commands.camera;

import com.lothbrok.game.controllers.commands.Command;
import com.lothbrok.game.renderers.ExtendedCamera;

public class ZoomOut implements Command<ExtendedCamera> {

    private static final String TAG = MoveLeft.class.getSimpleName();

    @Override
    public void execute(ExtendedCamera entity, float deltaTime) {
        entity.zoomOut(deltaTime);
    }
}

