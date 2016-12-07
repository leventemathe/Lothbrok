package com.lothbrok.game.controllers;

import com.lothbrok.game.model.entities.Player;

import java.util.EnumMap;
import java.util.Map;

//TODO common superclass with AI
//TODO or at lease do separate camera and player controller
public class PlayerController {

    private enum Commands {
        MOVELEFT,
        MOVERIGHT,
        ATTACK,
        JUMP;

        public static int getSize() {
            return values().length;
        }
    }

    private Map<Commands, Boolean> commands;

    public PlayerController() {
        this.commands = new EnumMap<>(Commands.class);
        commands.put(Commands.MOVELEFT, false);
        commands.put(Commands.MOVERIGHT, false);
        commands.put(Commands.ATTACK, false);
        commands.put(Commands.JUMP, false);
    }

    public void control(float deltaTime, Player player) {
        if(player == null) {
            return;
        }
        if(commands.get(Commands.MOVELEFT)) {
            player.moveLeft(deltaTime);
        }
        if(commands.get(Commands.MOVERIGHT)) {
            player.moveRight(deltaTime);
        }
        if(commands.get(Commands.ATTACK)) {
            player.startAttacking();
            commands.put(Commands.ATTACK, false);
        }
        if(commands.get(Commands.JUMP)) {
            player.jump(deltaTime);
        }
    }

    public void attack(boolean b) {
        commands.put(Commands.ATTACK, b);
    }

    public void jump(boolean b) {
        commands.put(Commands.JUMP, b);
    }

    public void moveLeft(boolean b) {
        commands.put(Commands.MOVELEFT, b);
    }

    public void moveRight(boolean b) {
        commands.put(Commands.MOVERIGHT, b);
    }
}
