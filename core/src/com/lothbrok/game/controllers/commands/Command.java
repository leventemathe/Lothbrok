package com.lothbrok.game.controllers.commands;

import com.lothbrok.game.model.entities.MovingEntity;

public interface Command {

    void execute(MovingEntity entity, float deltaTime);

    public class Jump implements Command {

        @Override
        public void execute(MovingEntity entity, float deltaTime) {
            entity.jump(deltaTime);
        }
    }

    public class MoveLeft implements Command {

        @Override
        public void execute(MovingEntity entity, float deltaTime) {
            entity.moveLeft(deltaTime);
        }
    }

    public class MoveRight implements Command {

        @Override
        public void execute(MovingEntity entity, float deltaTime) {
            entity.moveRight(deltaTime);
        }
    }
}
