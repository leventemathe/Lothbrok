package com.lothbrok.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ObjectSet;
import com.lothbrok.game.model.entities.Enemy;
import com.lothbrok.game.model.entities.Entity;
import com.lothbrok.game.model.entities.Player;

public class Collision {

    private static final String TAG = Collision.class.getSimpleName();

    private Player player;
    private ObjectSet<Enemy> activeEnemies;

    public Collision(GameModel gameModel) {
        this.player = gameModel.getPlayer();
        this.activeEnemies = new ObjectSet<>();
    }

    public void update() {
        ObjectSet.ObjectSetIterator<Enemy> it = activeEnemies.iterator();
        while(it.hasNext()) {
            Enemy enemy = it.next();
            if(enemy.actionState == Entity.ActionState.ATTACKING &&
                    intersect(player.getBodyBox(), enemy.getWeaponBox()) &&
                    enemy.hit(player)) {

                Gdx.app.debug(TAG, "player hit");
                player.getHit();
            }
            if(player.actionState == Entity.ActionState.ATTACKING &&
                    intersect(enemy.getBodyBox(), player.getWeaponBox()) &&
                    player.hit(enemy)) {

                Gdx.app.debug(TAG, "enemy hit");
                enemy.getHit();
            }
        }
    }

    private boolean intersect(Rectangle body, Rectangle weapon) {
        return weapon.overlaps(body);
    }

    public void addActiveEnemey(Enemy enemy) {
        if(activeEnemies.add(enemy)) {
            Gdx.app.debug(TAG, "active enemy added");
        }
    }

    public void removeActiveEnemy(Enemy enemy) {
        if(activeEnemies.remove(enemy)) {
            Gdx.app.debug(TAG, "active enemy removed");
        }
    }
}
