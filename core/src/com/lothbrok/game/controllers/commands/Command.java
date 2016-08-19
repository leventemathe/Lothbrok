package com.lothbrok.game.controllers.commands;

import com.lothbrok.game.model.entities.MovingEntity;

public interface Command {

    void execute(MovingEntity entity, float deltaTime);

    public class Jump implements Command {

        private final String TAG = Jump.class.getSimpleName();

        @Override
        public void execute(MovingEntity entity, float deltaTime) {
            entity.jump(deltaTime);
            //Gdx.app.debug(TAG, "command sent");
        }
    }

    public class MoveLeft implements Command {

        private final String TAG = MoveLeft.class.getSimpleName();

        @Override
        public void execute(MovingEntity entity, float deltaTime) {
            entity.moveLeft(deltaTime);
            //Gdx.app.debug(TAG, "command sent");
        }
    }

    public class MoveRight implements Command {

        private final String TAG = MoveRight.class.getSimpleName();

        @Override
        public void execute(MovingEntity entity, float deltaTime) {
            entity.moveRight(deltaTime);
            //Gdx.app.debug(TAG, "command sent");
        }
    }
}
