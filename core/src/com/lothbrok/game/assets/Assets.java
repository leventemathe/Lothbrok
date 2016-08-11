package com.lothbrok.game.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.StringBuilder;
import com.lothbrok.game.assets.animation.SpriterAnimation;
import com.lothbrok.game.assets.animation.SpriterAnimationLoader;
import com.lothbrok.game.assets.entities.MainMenuAssets;
import com.lothbrok.game.assets.entities.PlayerAssets;

public class Assets implements Disposable {

    public static final String TAG = Assets.class.getSimpleName();

    public static final Assets instance = new Assets();

    //TODO implement scale: 1080p 1x, 4k 2x etc -> changel file paths and viewport sizes accordingly

    private AssetManager assetManager;

    private Assets() {}

    private PlayerAssets playerAssets;
    private MainMenuAssets mainMenuAssets;

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        //TODO this error handling sucks, implement something better with exception throwing, catching
        assetManager.setErrorListener(new AssetsErrorListenerImplementation());

        assetManager.setLoader(SpriterAnimation.class, new SpriterAnimationLoader(new InternalFileHandleResolver()));
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assetManager.setLoader(Skin.class, new SkinLoader(new InternalFileHandleResolver()));
        assetManager.setLoader(BitmapFont.class, new BitmapFontLoader(new InternalFileHandleResolver()));
    }

    public boolean isDoneLoading() {
        return assetManager.update();
    }

    public float getProgess() {
        return assetManager.getProgress();
    }

    public void loadPlayerAssets() {
        assetManager.load(AssetsConstants.PLAYER_ANIMATION_PATH, SpriterAnimation.class);
    }

    public PlayerAssets getPlayerAssets() {
        if(playerAssets == null) {
            SpriterAnimation animation = assetManager.get(AssetsConstants.PLAYER_ANIMATION_PATH);
            playerAssets = new PlayerAssets(animation);
        }
        return playerAssets;
    }

    public void loadMainMenuAssets() {
        assetManager.load(AssetsConstants.MENU_SKIN_PATH, Skin.class, new SkinLoader.SkinParameter(AssetsConstants.MENU_ATLAS_PATH));
        assetManager.load(AssetsConstants.MENU_FONT_PATH, BitmapFont.class);
    }

    public MainMenuAssets getMainMenuAssets() {
        if(mainMenuAssets == null) {
            Skin skin = assetManager.get(AssetsConstants.MENU_SKIN_PATH);
            BitmapFont font = assetManager.get(AssetsConstants.MENU_FONT_PATH);
            mainMenuAssets = new MainMenuAssets();
            mainMenuAssets.setSkin(skin);
            mainMenuAssets.setFont(font);
        }
        //TODO load filtering from settings
        //TODO set filtering for other images too (animation, map)
        Texture.TextureFilter filter = Texture.TextureFilter.MipMapLinearLinear;
        ObjectSet<Texture> textures = mainMenuAssets.getSkin().getAtlas().getTextures();
        for(Texture texture : textures) {
            texture.setFilter(filter, filter);
        }
        return mainMenuAssets;
    }

    public String buildMapFilePath(int index) {
        StringBuilder path = new StringBuilder(AssetsConstants.MAP_PREFIX);
        path.append(String.valueOf(index));
        path.append(AssetsConstants.MAP_POSTFIX);
        return  path.toString();
    }

    public void loadMap(int index) {
        assetManager.load(buildMapFilePath(index), TiledMap.class);
    }

    public TiledMap getMap(int index) {
        return assetManager.get(buildMapFilePath(index));
    }

    //TODO add unload methods
    //TODO assets switching in screen transition
    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
