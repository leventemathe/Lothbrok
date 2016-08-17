package com.lothbrok.game.model;

import com.lothbrok.game.model.entities.Player;

public class GameModel {

    private Player player;

    public GameModel(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
