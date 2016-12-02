package com.lothbrok.game.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.maps.tiled.AtlasTmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.StringBuilder;
import com.lothbrok.game.assets.animation.spriter.SpriterAnimationAssets;
import com.lothbrok.game.assets.animation.spriter.SpriterAnimationAssetsLoader;
import com.lothbrok.game.assets.entities.MainMenuAssets;
import com.lothbrok.game.assets.entities.RalewayLightFont;
import com.lothbrok.game.assets.utils.AssetsConstants;
import com.lothbrok.game.assets.utils.AssetsErrorListenerImplementation;

public class Assets implements Disposable {

    public static final String TAG = Assets.class.getSimpleName();

    public static final Assets instance = new Assets();

    //TODO implement scale: 1080p 1x, 4k 2x etc -> changel file paths and viewport sizes accordingly

    private AssetManager assetManager;

    private Assets() {}

    private RalewayLightFont ralewayLightFont;
    private MainMenuAssets mainMenuAssets;

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        //TODO this error handling sucks, implement something better with exception throwing, catching
        assetManager.setErrorListener(new AssetsErrorListenerImplementation());

        assetManager.setLoader(SpriterAnimationAssets.class, new SpriterAnimationAssetsLoader(new InternalFileHandleResolver()));
        assetManager.setLoader(TiledMap.class, new AtlasTmxMapLoader(new InternalFileHandleResolver()));
        assetManager.setLoader(Skin.class, new SkinLoader(new InternalFileHandleResolver()));
        assetManager.setLoader(BitmapFont.class, new BitmapFontLoader(new InternalFileHandleResolver()));
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(new InternalFileHandleResolver()));
    }

    public boolean isDoneLoading() {
        return assetManager.update();
    }

    public float getProgess() {
        return assetManager.getProgress();
    }

    public void loadRalewayLightFont() {
        assetManager.load(com.lothbrok.game.assets.utils.AssetsConstants.RALEWAY_LIGHT_FONT_PATH, FreeTypeFontGenerator.class);
    }

    //TODO move the generation to loading somehow, same for menu
    public RalewayLightFont getRalewayLightFont() {
        if(ralewayLightFont == null) {
            FreeTypeFontGenerator fontGenerator = assetManager.get(AssetsConstants.RALEWAY_LIGHT_FONT_PATH);

            FreeTypeFontParameter parameter = new FreeTypeFontParameter();
            parameter.size = 32;
            BitmapFont font32 = fontGenerator.generateFont(parameter);
            parameter.size = 48;
            BitmapFont font48 = fontGenerator.generateFont(parameter);

            ralewayLightFont = new RalewayLightFont();
            ralewayLightFont.setFont32(font32);
            ralewayLightFont.setFont48(font48);
        }
        return ralewayLightFont;
    }

    public void loadPlayerAnimationAssets() {
        assetManager.load(AssetsConstants.PLAYER_ANIMATION_PATH, SpriterAnimationAssets.class);
    }

    public SpriterAnimationAssets getPlayerAnimationAssets() {
        return assetManager.get(AssetsConstants.PLAYER_ANIMATION_PATH);
    }

    public void loadEnemyAnimationAssets() {
        assetManager.load(AssetsConstants.ENEMY_ANIMATION_PATH, SpriterAnimationAssets.class);
    }

    public SpriterAnimationAssets getEnemyAnimationAssets() {
        return assetManager.get(AssetsConstants.ENEMY_ANIMATION_PATH);
    }
    public void loadMainMenuAssets() {
        assetManager.load(AssetsConstants.MENU_SKIN_PATH, Skin.class, new SkinLoader.SkinParameter(AssetsConstants.MENU_ATLAS_PATH));
        //assetManager.load(AssetsConstants.MENU_FONT_PATH, BitmapFont.class);
        assetManager.load(AssetsConstants.MENU_FONT_PATH, FreeTypeFontGenerator.class);
    }

    public MainMenuAssets getMainMenuAssets() {
        if(mainMenuAssets == null) {
            Skin skin = assetManager.get(AssetsConstants.MENU_SKIN_PATH);

            FreeTypeFontGenerator fontGenerator = assetManager.get(AssetsConstants.MENU_FONT_PATH);

            FreeTypeFontParameter parameter = new FreeTypeFontParameter();
            parameter.size = 48;
            BitmapFont font48 = fontGenerator.generateFont(parameter);

            mainMenuAssets = new MainMenuAssets();
            mainMenuAssets.setSkin(skin);
            mainMenuAssets.setFontGenerator(fontGenerator);
            mainMenuAssets.setFont48(font48);
        }
        //TODO load filtering from settings
        //TODO set filtering for other images too (animation, map)
        Texture.TextureFilter filter = Texture.TextureFilter.MipMapLinearLinear;
        Texture.TextureFilter fontFilter = Texture.TextureFilter.Linear;
        ObjectSet<Texture> textures = mainMenuAssets.getSkin().getAtlas().getTextures();
        Array<TextureRegion> fontTextures = mainMenuAssets.getFont48().getRegions();
        for(Texture texture : textures) {
            texture.setFilter(filter, filter);
        }
        for(TextureRegion texture : fontTextures) {
            texture.getTexture().setFilter(fontFilter, fontFilter);
        }
        return mainMenuAssets;
    }

    public String buildMapFilePath(int index) {
        StringBuilder path = new StringBuilder(AssetsConstants.MAP_PREFIX);
        path.append(String.valueOf(index));
        path.append(com.lothbrok.game.assets.utils.AssetsConstants.MAP_POSTFIX);
        return  path.toString();
    }

    public void loadMap(int index) {
        assetManager.load(buildMapFilePath(index), TiledMap.class);
    }

    public TiledMap getMap(int index) {
        return assetManager.get(buildMapFilePath(index));
    }

    public void loadMobileControlsSkin() {
        assetManager.load(AssetsConstants.MOBILE_CONTROLS_SKIN_PATH, Skin.class, new SkinLoader.SkinParameter(AssetsConstants.MOBILE_CONTROLS_ATLAS_PATH));
    }

    public Skin getMobileControlsSkin() {
        return assetManager.get(AssetsConstants.MOBILE_CONTROLS_SKIN_PATH);
    }

    //TODO add unload methods
    //TODO assets switching in screen transition
    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
