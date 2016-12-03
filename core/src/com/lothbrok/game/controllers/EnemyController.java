package com.lothbrok.game.controllers;

import com.lothbrok.game.model.entities.Enemy;

import java.util.List;

public class EnemyController {

    private List<Enemy> enemies;

    public EnemyController(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public void control(float deltaTime) {
        for(int i = 0; i < enemies.size(); i++) {
            controlEnemy(enemies.get(i), deltaTime);
        }
    }

    private void controlEnemy(Enemy enemy, float deltaTime) {
        //if player not in sight:
        enemy.move(deltaTime);
        //if player in sight:
        //  attack
    }
}
