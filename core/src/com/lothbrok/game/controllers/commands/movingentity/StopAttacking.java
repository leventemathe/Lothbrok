package com.lothbrok.game.controllers.commands.movingentity;

import com.lothbrok.game.controllers.commands.Command;
import com.lothbrok.game.model.entities.MovingEntity;

public class StopAttacking  implements Command<MovingEntity> {

    private static final String TAG = StartAttacking.class.getSimpleName();

    @Override
    public void execute(MovingEntity entity, float deltaTime) {
        entity.stopAttacking(deltaTime);
        //Gdx.app.debug(TAG, "command sent");
    }
}
