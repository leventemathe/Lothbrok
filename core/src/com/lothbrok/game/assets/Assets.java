package com.lothbrok.game.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.AtlasTmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.StringBuilder;
import com.lothbrok.game.assets.entities.audio.MusicAssets;
import com.lothbrok.game.assets.entities.audio.SoundAssets;
import com.lothbrok.game.assets.entities.fonts.Font;
import com.lothbrok.game.assets.entities.fonts.FontLoader;
import com.lothbrok.game.assets.resolvers.LFileHandleResolver;
import com.lothbrok.game.assets.resolvers.MFileHandleResolver;
import com.lothbrok.game.assets.resolvers.SFileHandleResolver;
import com.lothbrok.game.assets.resolvers.XLFileHandleResolver;
import com.lothbrok.game.assets.spriter.SpriterAnimationAssets;
import com.lothbrok.game.assets.spriter.SpriterAnimationAssetsLoader;
import com.lothbrok.game.constants.AssetsConstants;
import com.lothbrok.game.constants.UIConstants;
import com.lothbrok.game.screens.loadingscreens.EssentialsLoadingScreen;

public class Assets implements Disposable {

    public static final String TAG = Assets.class.getSimpleName();

    private AssetManager assetManager;

    private TextureRegion coin;
    private MusicAssets musicAssets;
    private SoundAssets soundAssets;

    //TODO error handling

    public void init() {
        FileHandleResolver internalFileHandleResolver = new InternalFileHandleResolver();
        FileHandleResolver sizeFileHandleResolver = buildSizeFileHandleResolver();

        if(assetManager != null) {
            assetManager.dispose();
        }
        assetManager = new AssetManager(internalFileHandleResolver, false);

        assetManager.setLoader(SpriterAnimationAssets.class, new SpriterAnimationAssetsLoader(sizeFileHandleResolver));
        assetManager.setLoader(TiledMap.class, new AtlasTmxMapLoader(sizeFileHandleResolver));
        assetManager.setLoader(TextureAtlas.class, new TextureAtlasLoader(sizeFileHandleResolver));
        assetManager.setLoader(Texture.class, new TextureLoader(sizeFileHandleResolver));
        assetManager.setLoader(Skin.class, new SkinLoader(sizeFileHandleResolver));
        assetManager.setLoader(BitmapFont.class, new BitmapFontLoader(internalFileHandleResolver));
        assetManager.setLoader(Font.class, new FontLoader(internalFileHandleResolver));
        assetManager.setLoader(Music.class, new MusicLoader(internalFileHandleResolver));
        assetManager.setLoader(Sound.class, new SoundLoader(internalFileHandleResolver));
    }

    private FileHandleResolver buildSizeFileHandleResolver() {
        if(EssentialsLoadingScreen.size == EssentialsLoadingScreen.Size.XL) {
            return new XLFileHandleResolver();
        } else if(EssentialsLoadingScreen.size == EssentialsLoadingScreen.Size.L) {
            return new LFileHandleResolver();
        } else if(EssentialsLoadingScreen.size == EssentialsLoadingScreen.Size.M) {
            return new MFileHandleResolver();
        } else {
            return new SFileHandleResolver();
        }
    }

    public void loadEssentials() {
        loadRalewayLightFont();
        loadLoadingAnimationAssets();
    }

    public void loadAll() {
        loadPRVikingFont();
        loadMainMenuSkin();
        loadPlayerAnimationAssets();
        loadEnemyAnimationAssets();
        loadMap(1);
        loadMobileControlsSkin();
        loadUI();
        loadMusicAssets();
        loadSoundAssets();
    }

    public boolean isDoneLoading() {
        return assetManager.update();
    }

    public float getProgess() {
        return assetManager.getProgress();
    }



    public void loadRalewayLightFont() {
        assetManager.load(AssetsConstants.RALEWAY_LIGHT_FONT_PATH, Font.class);
    }

    public Font getRalewayLightFont() {
        return assetManager.get(AssetsConstants.RALEWAY_LIGHT_FONT_PATH);
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

    public void loadLoadingAnimationAssets() {
        assetManager.load(AssetsConstants.LOADING_ANIMATION_PATH, SpriterAnimationAssets.class);
    }

    public SpriterAnimationAssets getLoadingAnimationAssets() {
        return assetManager.get(AssetsConstants.LOADING_ANIMATION_PATH);
    }

    public void loadPRVikingFont () {
        assetManager.load(AssetsConstants.PR_VIKING_FONT_PATH, Font.class);
    }

    public Font getPrVikingFont() {
        return assetManager.get(AssetsConstants.PR_VIKING_FONT_PATH);
    }

    public void loadMainMenuSkin() {
        assetManager.load(AssetsConstants.MAIN_MENU_SKIN_PATH, Skin.class, new SkinLoader.SkinParameter(AssetsConstants.MAIN_MENU_ATLAS_PATH));
    }

    public Skin getMainMenuSkin() {
        return assetManager.get(AssetsConstants.MAIN_MENU_SKIN_PATH);
    }

    public String buildMapFilePath(int index) {
        StringBuilder path = new StringBuilder(AssetsConstants.MAP_PREFIX_PATH);
        path.append(String.valueOf(index));
        path.append(AssetsConstants.MAP_POSTFIX_PATH);
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

    public void loadUI() {
        assetManager.load(AssetsConstants.UI_SKIN_PATH, Skin.class, new SkinLoader.SkinParameter(AssetsConstants.UI_ATLAS_PATH));
    }

    public Skin getUI() {
        return assetManager.get(AssetsConstants.UI_SKIN_PATH);
    }

    public TextureRegion getCoin() {
        if(coin == null) {
            coin = getUI().getRegion(UIConstants.UI_COIN);
        }
        return coin;
    }

    public void loadMusicAssets() {
        assetManager.load(AssetsConstants.MUSIC_MAIN_MENU, Music.class);
        assetManager.load(AssetsConstants.MUSIC_GAMEPLAY, Music.class);
        assetManager.load(AssetsConstants.MUSIC_DEATH, Music.class);
        assetManager.load(AssetsConstants.MUSIC_VICTORY, Music.class);
        assetManager.load(AssetsConstants.SOUND_FOOTSTEP, Music.class);
    }

    public MusicAssets getMusicAssets() {
        if(musicAssets == null) {
            musicAssets = new MusicAssets();
            musicAssets.setMainMenu((Music) assetManager.get(AssetsConstants.MUSIC_MAIN_MENU));
            musicAssets.setGamePlay((Music) assetManager.get(AssetsConstants.MUSIC_GAMEPLAY));
            musicAssets.setDeath((Music) assetManager.get(AssetsConstants.MUSIC_DEATH));
            musicAssets.setVictory((Music) assetManager.get(AssetsConstants.MUSIC_VICTORY));
            musicAssets.setFootsteps((Music) assetManager.get(AssetsConstants.SOUND_FOOTSTEP));
        }
        return musicAssets;
    }

    public void loadSoundAssets() {
        assetManager.load(AssetsConstants.SOUND_SLICE, Sound.class);
        assetManager.load(AssetsConstants.SOUND_SWING, Sound.class);
        assetManager.load(AssetsConstants.SOUND_EHH, Sound.class);
    }

    public SoundAssets getSoundAssets() {
        if(soundAssets == null) {
            soundAssets = new SoundAssets();
            soundAssets.setSwing((Sound) assetManager.get(AssetsConstants.SOUND_SWING));
            soundAssets.setSlice((Sound) assetManager.get(AssetsConstants.SOUND_SLICE));
            soundAssets.setEhh((Sound) assetManager.get(AssetsConstants.SOUND_EHH));
        }
        return soundAssets;
    }

    @Override
    public void dispose() {
        Gdx.app.debug(TAG, "dispose");
        assetManager.dispose();
    }
}
