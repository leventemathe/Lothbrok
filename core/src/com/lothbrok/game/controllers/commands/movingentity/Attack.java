package com.lothbrok.game.controllers.commands.movingentity;

import com.lothbrok.game.controllers.commands.Command;
import com.lothbrok.game.model.entities.MovingEntity;

public class Attack implements Command<MovingEntity> {

    private static final String TAG = Attack.class.getSimpleName();

    @Override
    public void execute(MovingEntity entity, float deltaTime) {
        entity.attack(deltaTime);
        //Gdx.app.debug(TAG, "command sent");
    }
}
