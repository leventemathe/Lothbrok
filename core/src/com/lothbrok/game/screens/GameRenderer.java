package com.lothbrok.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.assets.AssetsConstants;
import com.lothbrok.game.assets.animation.SpriterAnimation;
import com.lothbrok.game.assets.entities.PlayerAssets;

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

        camera = new OrthographicCamera();
        //viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        //TODO custom viewport, now it sets it to the size of the desktop launcher/android screen size
        float aspect = (float)Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight();
        float height = 8f;
        float width = height * aspect;
        viewport = new FitViewport(width, height, camera);
        viewport.apply();
        camera.update();

        map = Assets.instance.getMap(1);
        player = Assets.instance.getPlayerAssets();
        //TODO get scale from m l xl etc
        player.getAnimation().scale(1/270f); //l

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
        camera.update();

        //background color
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        //TODO get sky color from somewhere (also change in menu?)
        shapeRenderer.setColor(0f, 0f, 0.5f, 1f);
        shapeRenderer.rect(camera.position.x - camera.viewportWidth/2,
                           camera.position.y - camera.viewportHeight/2,
                           camera.viewportWidth,
                           camera.viewportHeight);
        shapeRenderer.end();


        //map
        mapRenderer.setView(camera);
        mapRenderer.render();

        //animation
        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(camera.combined);

        SpriterAnimation animation = (SpriterAnimation)player.getAnimation();
        animation.play(deltaTime, spriteBatch, shapeRenderer, AssetsConstants.PLAYER_ANIMATION_ENTITY, AssetsConstants
                .PLAYER_ANIMATION_ATTACKING);

        renderFpsCounter(spriteBatch);

        spriteBatch.end();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public boolean keyDown(int keycode) {
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

    //TODO move this from here to a common place
    private void renderFpsCounter (SpriteBatch batch) {
        float x = 0;
        float y = 0;
        int fps = Gdx.graphics.getFramesPerSecond();
        BitmapFont fpsFont = Assets.instance.getMainMenuAssets().getFont48();
        if (fps >= 45) {
            // 45 or more FPS show up in green
            fpsFont.setColor(0, 1, 0, 1);
        } else if (fps >= 30) {
            // 30 or more FPS show up in yellow
            fpsFont.setColor(1, 1, 0, 1);
        } else {
            // less than 30 FPS show up in red
            fpsFont.setColor(1, 0, 0, 1);
        }
        fpsFont.draw(batch, "FPS: " + fps, x, y);
        fpsFont.setColor(1, 1, 1, 1); // white
    }
}
