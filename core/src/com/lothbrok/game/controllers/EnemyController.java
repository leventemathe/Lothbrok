package com.lothbrok.game.controllers;

import com.badlogic.gdx.utils.Array;
import com.lothbrok.game.model.entities.Enemy;
import com.lothbrok.game.model.entities.Entity;
import com.lothbrok.game.model.entities.Player;

public class EnemyController {

    public void control(float deltaTime, Array<Enemy> enemies, Player player) {
        for(int i = 0; i < enemies.size; i++) {
            controlEnemy(enemies.get(i), deltaTime, player);
        }
    }

    private void controlEnemy(Enemy enemy, float deltaTime, Player player) {
        if(player == null) {
            enemy.move(deltaTime);
            return;
        }
        Entity.Direction playersSide = player.getPositionX() < enemy.getPositionX() ? Entity.Direction.LEFT : Entity.Direction.RIGHT;
        float distance = Math.abs(enemy.getPosition().x - player.getPosition().x);
        boolean isOnSameLevel = Math.round(player.getPosition().y) == Math.round(enemy.getPosition().y);
        if(distance < enemy.ATTACK_RADIUS && isOnSameLevel && player.lifeState != Entity.LifeState.DEAD) {
            enemy.startAttacking();
            if(enemy.direction == Entity.Direction.LEFT && playersSide == Entity.Direction.RIGHT) {
                enemy.direction = Entity.Direction.RIGHT;
            } else if (enemy.direction == Entity.Direction.RIGHT && playersSide == Entity.Direction.LEFT) {
                enemy.direction = Entity.Direction.LEFT;
            }
        } else if(distance < enemy.RADIUS && isOnSameLevel && player.lifeState != Entity.LifeState.DEAD) {
            enemy.moveTo(player.getPosition(), deltaTime);
        } else {
            enemy.move(deltaTime);
        }

    }
}
