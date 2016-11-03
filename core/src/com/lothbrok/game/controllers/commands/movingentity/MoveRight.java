package com.lothbrok.game.controllers.commands.movingentity;

import com.lothbrok.game.controllers.commands.Command;
import com.lothbrok.game.model.entities.MovingEntity;

public class MoveRight implements Command<MovingEntity> {

    private static final String TAG = MoveRight.class.getSimpleName();

    @Override
    public void execute(MovingEntity entity, float deltaTime) {
        entity.moveRight(deltaTime);
        //Gdx.app.debug(TAG, "command sent");
    }
}
