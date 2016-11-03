package com.lothbrok.game.controllers.commands.movingentity;

import com.lothbrok.game.controllers.commands.Command;
import com.lothbrok.game.model.entities.MovingEntity;

public class MoveLeft implements Command<MovingEntity> {

    private static final String TAG = MoveLeft.class.getSimpleName();

    @Override
    public void execute(MovingEntity entity, float deltaTime) {
        entity.moveLeft(deltaTime);
        //Gdx.app.debug(TAG, "command sent");
    }
}
