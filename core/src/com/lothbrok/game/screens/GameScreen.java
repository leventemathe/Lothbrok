package com.lothbrok.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.assets.AssetsConstants;
import com.lothbrok.game.assets.entities.PlayerAssets;

public class GameScreen extends AbstractScreen {
    public static final String TAG = GameScreen.class.getSimpleName();

    //render stuff
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;

    private OrthographicCamera camera;

    //map stuff
    TiledMap map;
    OrthogonalTiledMapRenderer mapRenderer;

    @Override
    public void show() {
        //render stuff
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera(8, 8);

        //TODO do some filtering
        //map stuff
        map = Assets.instance.getMap(1);
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1/540f);
        mapRenderer.setView(camera);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //player = animation
        PlayerAssets player = Assets.instance.getPlayerAssets();

        spriteBatch.begin();
        player.getAnimation().play(spriteBatch, shapeRenderer, AssetsConstants.PLAYER_ANIMATION_ENTITY, AssetsConstants.PLAYER_ANIMATION_WALKING);
        spriteBatch.end();

        //map
        mapRenderer.render();
    }

    @Override
    public void resize(int width, int height) {
        //camera.viewportWidth = width;
        //camera.viewportHeight = height;
        //camera.update();
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
        //TODO do proper dispose, e.g. SpriteBatch
    }
}
