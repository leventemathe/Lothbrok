package com.lothbrok.game.controllers;

import com.lothbrok.game.controllers.commands.Command;

import java.util.ArrayList;
import java.util.List;

public class AbstractController<EntityType, CommandType extends Command> {

    protected EntityType entity;
    protected List<CommandType> commands;

    public AbstractController(EntityType entity) {
        this.entity = entity;
        commands = new ArrayList<>();
    }

    public void controlEntity(EntityType entity) {
        this.entity = entity;
    }

    public void addCommand(CommandType command) {
        commands.add(command);
    }

    public void executeCommands(float deltaTime) {
        //No iterator, because of Garbage Collection
        for(int i = 0; i < commands.size(); i++) {
            commands.get(i).execute(entity, deltaTime);
        }
        commands.clear();
    }
}
