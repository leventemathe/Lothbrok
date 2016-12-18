package com.lothbrok.game.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ObjectSet;
import com.lothbrok.game.model.entities.Enemy;
import com.lothbrok.game.model.entities.Entity;
import com.lothbrok.game.model.entities.Player;

public class Collision {

    private static final String TAG = Collision.class.getSimpleName();

    private ObjectSet<Enemy> activeEnemies;

    public Collision() {
        this.activeEnemies = new ObjectSet<>();
    }

    public void update(Player player) {
        if(player == null) {
            return;
        }
        ObjectSet.ObjectSetIterator<Enemy> it = activeEnemies.iterator();
        while(it.hasNext()) {
            Enemy enemy = it.next();
            if(enemy.actionState == Entity.ActionState.ATTACKING &&
                    intersect(player.getBodyBox(), enemy.getWeaponBox()) &&
                    enemy.hit(player)) {

                player.getHit();
            }
            if(player.actionState == Entity.ActionState.ATTACKING &&
                    intersect(enemy.getBodyBox(), player.getWeaponBox()) &&
                    player.hit(enemy)) {

                enemy.getHit();
            }
        }
    }

    private boolean intersect(Rectangle body, Rectangle weapon) {
        return weapon.overlaps(body);
    }

    public void addActiveEnemey(Enemy enemy) {
        activeEnemies.add(enemy);
    }

    public void removeActiveEnemy(Enemy enemy) {
        activeEnemies.remove(enemy);
    }
}
