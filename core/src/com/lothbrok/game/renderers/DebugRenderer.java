package com.lothbrok.game.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lothbrok.game.assets.Assets;

public class DebugRenderer implements Disposable {

    private Camera camera;
    private Viewport viewport;

    private SpriteBatch spriteBatch;

    private BitmapFont font;

    public DebugRenderer() {
        camera = new OrthographicCamera();
        //TODO move size to constants
        viewport = new ExtendViewport(1920, 1080, camera);
        spriteBatch = new SpriteBatch();
        //TODO get size dependent font
        font = Assets.instance.getRalewayLightFont().getFont32();
    }

    public void render(float delta) {
        viewport.apply();
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        renderFpsCounter(spriteBatch);
        spriteBatch.end();
    }

    private void renderFpsCounter (SpriteBatch batch) {
        float x = -viewport.getWorldWidth()/2 + 50;
        float y = -viewport.getWorldHeight()/2 + 50;
        int fps = Gdx.graphics.getFramesPerSecond();
        font.draw(batch, "FPS: " + fps, x, y);
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }
}
