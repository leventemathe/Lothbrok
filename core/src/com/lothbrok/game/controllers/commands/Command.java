package com.lothbrok.game.controllers.commands;

public interface Command<EntityType> {

    void execute(EntityType entity, float deltaTime);
}
