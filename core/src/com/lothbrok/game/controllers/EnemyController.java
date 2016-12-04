package com.lothbrok.game.controllers;

import com.lothbrok.game.model.entities.Enemy;
import com.lothbrok.game.model.entities.Player;

import java.util.List;

public class EnemyController {

    private List<Enemy> enemies;
    private Player player;

    public EnemyController(List<Enemy> enemies, Player player) {
        this.enemies = enemies;
        this.player = player;
    }

    public void control(float deltaTime) {
        for(int i = 0; i < enemies.size(); i++) {
            controlEnemy(enemies.get(i), deltaTime);
        }
    }

    private void controlEnemy(Enemy enemy, float deltaTime) {
        float distance = Math.abs(enemy.position.x - player.position.x);
        boolean isOnSameLevel = Math.round(player.position.y) == Math.round(enemy.position.y);
        if(distance < enemy.ATTACK_RADIUS && isOnSameLevel) {
            enemy.startAttacking();
        } else if(distance < enemy.RADIUS && isOnSameLevel) {
            enemy.moveTo(player.position, deltaTime);
        } else {
                enemy.move(deltaTime);
        }

    }
}
