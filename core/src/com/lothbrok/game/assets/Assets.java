package com.lothbrok.game.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable {
    public static final Assets instance = new Assets();

    private AssetManager assetManager;
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;

    private com.lothbrok.game.assets.entities.Player player;

    private Assets() {}

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.setErrorListener(new AssetsErrorListenerImplementation());
        this.spriteBatch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
    }

    public void loadPlayer() {
        player = new com.lothbrok.game.assets.entities.Player(this);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        spriteBatch.dispose();
        shapeRenderer.dispose();
        player.dispose(); //TODO player could be null
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public com.lothbrok.game.assets.entities.Player getPlayer() {
        return player;
    }
}
