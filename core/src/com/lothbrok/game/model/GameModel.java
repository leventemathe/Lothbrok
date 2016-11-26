package com.lothbrok.game.model;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.lothbrok.game.model.entities.Player;

public class GameModel {

    private Player player;
    private TiledMap map;

    public GameModel(Player player, TiledMap map) {
        this.player = player;
        this.map = map;
    }

    public Player getPlayer() {
        return player;
    }

    public TiledMap getMap() {
        return map;
    }
}
