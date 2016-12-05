package com.lothbrok.game.controllers;

import com.lothbrok.game.model.entities.Enemy;
import com.lothbrok.game.model.entities.Player;

import java.util.List;

public class CollisionController {

    private List<Enemy> enemies;
    private Player player;

    public CollisionController(List<Enemy> enemies, Player player) {
        this.enemies = enemies;
        this.player = player;
    }

    public void control(float deltaTime) {
        // get the neighbors of player
        // check player against neightbors
        // check neighbors agains player
    }
}
