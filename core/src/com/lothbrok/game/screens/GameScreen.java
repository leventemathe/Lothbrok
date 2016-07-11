package com.lothbrok.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.assets.Constants;
import com.lothbrok.game.assets.entities.Player;

public class GameScreen extends AbstractScreen {
    public GameScreen (Game game) {
        super(game);
    }

    @Override
    public void show() {
        Assets.instance.loadPlayer();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        SpriteBatch batch =  Assets.instance.getSpriteBatch();
        Player player = Assets.instance.getPlayer();

        batch.begin();
        player.getAnimation().playEntity(Constants.PLAYER_WALKING_ANIMATION);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //TODO
    }
}
