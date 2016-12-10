package com.lothbrok.game.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.constants.Resolution;

public class DebugRenderer {

    private Assets assets;

    private Camera camera;
    private Viewport viewport;

    private SpriteBatch spriteBatch;

    private BitmapFont font;

    public DebugRenderer(SpriteBatch batch, Assets assets) {
        this.assets = assets;
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(Resolution.instance.getMenuWidth(), Resolution.instance.getMenuHeight(), camera);
        this.spriteBatch = batch;
        font = assets.getRalewayLightFont().getLargeFont();
    }

    public void render(float deltaTime) {
        viewport.apply();
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        renderFpsCounter(spriteBatch);
        spriteBatch.end();
    }

    private void renderFpsCounter (SpriteBatch batch) {
        float x = -viewport.getWorldWidth()/2f + 2*Resolution.instance.getPaddingMedium();
        float y = -viewport.getWorldHeight()/2f + 3*Resolution.instance.getPaddingMedium();
        int fps = Gdx.graphics.getFramesPerSecond();
        font.draw(batch, "FPS: " + fps, x, y);
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
