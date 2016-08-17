package com.lothbrok.game.controllers;

import com.lothbrok.game.controllers.commands.Command;
import com.lothbrok.game.model.entities.MovingEntity;

import java.util.ArrayList;
import java.util.List;

public class MovingEntityController {

    protected MovingEntity entity;
    protected List<Command> commands;

    public MovingEntityController(MovingEntity entity) {
        this.entity = entity;
        commands = new ArrayList<>();
    }

    public void controlEntity(MovingEntity entity) {
        this.entity = entity;
    }

    public void addCommand(Command command) {
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
