package com.lothbrok.game.controllers.commands.movingentity;

import com.lothbrok.game.controllers.commands.Command;
import com.lothbrok.game.model.entities.MovingEntity;

public class Jump implements Command<MovingEntity> {

    private static final String TAG = Jump.class.getSimpleName();

    @Override
    public void execute(MovingEntity entity, float deltaTime) {
        entity.jump(deltaTime);
        //Gdx.app.debug(TAG, "command sent");
    }
}
