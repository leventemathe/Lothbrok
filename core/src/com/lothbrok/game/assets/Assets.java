package com.lothbrok.game.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.lothbrok.game.assets.entities.Player;

public class Assets implements Disposable {
    public static final Assets instance = new Assets();

    private AssetManager assetManager;

    private Player player;

    private Assets() {}

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.setErrorListener(new AssetsErrorListenerImplementation());
    }

    public void loadPlayer() {
        player = new com.lothbrok.game.assets.entities.Player();
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        player.dispose(); //TODO player could be null
    }

    public com.lothbrok.game.assets.entities.Player getPlayer() {
        return player;
    }
}
