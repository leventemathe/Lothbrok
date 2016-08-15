package com.lothbrok.game.screens.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.assets.animation.SpriterAnimation;
import com.lothbrok.game.assets.entities.PlayerAssets;
import com.lothbrok.game.assets.utils.AssetsConstants;

public class GameRenderer implements Disposable, InputProcessor {

    private OrthographicCamera camera;
    private Viewport viewport;

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private OrthogonalTiledMapRenderer mapRenderer;

    //TODO move to logic?
    private TiledMap map;
    private PlayerAssets player;

    public GameRenderer() {
        Gdx.input.setInputProcessor(this);

        setupViewPort();
        setupEntities();
        setupRendering();
    }

    private void setupViewPort() {
        camera = new OrthographicCamera();
        //TODO custom viewport (or maybe extended), now it sets it to the size of the desktop launcher/android screen size
        float aspect = (float) Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight();
        float height = 4f;
        float width = height * aspect;
        viewport = new ExtendViewport(width, height, camera);
    }

    //TODO move to logic?
    private void setupEntities() {
        map = Assets.instance.getMap(1);
        player = Assets.instance.getPlayerAssets();
        //TODO get scale from m l xl etc
        player.getAnimation().scale(1/270f); //l
    }

    private void setupRendering() {
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        //TODO get scale from m l xl etc
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1/540f); //xl
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
        mapRenderer.dispose();
    }

    public void render(float deltaTime) {
        viewport.apply();
        renderSky();
        renderMap();
        renderAnimation(deltaTime);
    }

    private void renderSky() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        //TODO get sky color from somewhere (also change in menu?)
        shapeRenderer.setColor(0f, 0f, 0.5f, 1f);
        shapeRenderer.rect(camera.position.x - camera.viewportWidth/2,
                           camera.position.y - camera.viewportHeight/2,
                           camera.viewportWidth,
                           camera.viewportHeight);
        shapeRenderer.end();
    }

    private void renderMap() {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    private void renderAnimation(float deltaTime) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        SpriterAnimation animation = (SpriterAnimation)player.getAnimation();
        animation.update(deltaTime);
        animation.draw(spriteBatch, shapeRenderer);

        spriteBatch.end();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public boolean keyDown(int keycode) {
        //camera
        if(keycode == Input.Keys.LEFT) {
            camera.translate(-0.2f, 0f);
        }
        if(keycode == Input.Keys.RIGHT) {
            camera.translate(0.2f, 0f);
        }
        if(keycode == Input.Keys.UP) {
            camera.translate(0f, 0.2f);
        }
        if(keycode == Input.Keys.DOWN) {
            camera.translate(0f, -0.2f);
        }

        //player
        if(keycode == Input.Keys.W) {
            player.getAnimation().translatePosition(0f, 0.2f);
        }
        if(keycode == Input.Keys.S) {
            player.getAnimation().translatePosition(0f, -0.2f);
        }
        if(keycode == Input.Keys.A) {
            player.getAnimation().translatePosition(-0.2f, 0f);
        }
        if(keycode == Input.Keys.D) {
            player.getAnimation().translatePosition(0.2f, 0f);
        }
        if(keycode == Input.Keys.SPACE) {
            player.getAnimation().playOnce(AssetsConstants.PLAYER_ANIMATION_ENTITY, AssetsConstants.PLAYER_ANIMATION_ATTACKING);
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
