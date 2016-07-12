package com.lothbrok.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.assets.AssetsConstants;
import com.lothbrok.game.assets.entities.PlayerAssets;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameScreen extends AbstractScreen {
    public static final String TAG = GameScreen.class.toString();

    public GameScreen (Game game) {
        super(game);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        SpriteBatch spriteBatch = new SpriteBatch();
        ShapeRenderer shapeRenderer = new ShapeRenderer();

        PlayerAssets player = Assets.instance.getPlayerAssets();

        spriteBatch.begin();
        player.getAnimation().play(spriteBatch, shapeRenderer, AssetsConstants.PLAYER_ANIMATION_ENTITY, AssetsConstants.PLAYER_ANIMATION_WALKING);
        spriteBatch.end();
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
