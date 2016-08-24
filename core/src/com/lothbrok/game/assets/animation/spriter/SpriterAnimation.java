package com.lothbrok.game.assets.animation.spriter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.brashmonkey.spriter.Animation;
import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.Entity;
import com.brashmonkey.spriter.Loader;
import com.brashmonkey.spriter.Mainline;
import com.brashmonkey.spriter.Player;
import com.brashmonkey.spriter.SCMLReader;

import java.util.HashMap;
import java.util.Map;

//TODO add error handling
public class SpriterAnimation implements Disposable {

    private static final String TAG = SpriterAnimation.class.getSimpleName();

    private FileHandle fileHandle;

    private SCMLReader scmlReader;
    private Data scmlData;

    private Loader<Sprite> spriteLoader;
    private ScalingDrawer spriteDrawer;

    private float x = 0.0f;
    private float y = 0.0f;
    private float scale = 1.0f;

    private Entity currentEntity;
    private Player playOnce;
    private Player playAlways;

    //TODO lots of iterators are created for maps -> could cause GC stutter
    private Map<Entity, Map<String, Player>> cachedPlayers;
    private FinishListener cachedFinishListener;

    public SpriterAnimation() {
        this.cachedPlayers = new HashMap<>();
        this.cachedFinishListener = new FinishListener();
    }

    //Loaders
    public void loadScml(String path) {
        this.fileHandle = Gdx.files.internal(path);
        this.scmlReader = new SCMLReader(fileHandle.read());
        this.scmlData = scmlReader.getData();
    }

    public void loadImages() {
        this.spriteLoader = new LibGdxLoader(this.scmlData);
        this.spriteLoader.load(fileHandle.file());
        this.spriteDrawer = new LibGdxDrawer(this.spriteLoader);
    }

    //Metrics
    public void setX(float x) {
        this.x = x;

        for(Map.Entry<Entity, Map<String, Player>> entityEntry : cachedPlayers.entrySet()) {
            for(Map.Entry<String, Player> playerEntry : entityEntry.getValue().entrySet()) {
                Player player = playerEntry.getValue();
                player.setPosition(x, player.getY());
            }
        }
    }

    public void setY(float y) {
        this.y = y;

        for(Map.Entry<Entity, Map<String, Player>> entityEntry : cachedPlayers.entrySet()) {
            for(Map.Entry<String, Player> playerEntry : entityEntry.getValue().entrySet()) {
                Player player = playerEntry.getValue();
                player.setPosition(player.getX(), y);
            }
        }
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;

        for(Map.Entry<Entity, Map<String, Player>> entityEntry : cachedPlayers.entrySet()) {
            for(Map.Entry<String, Player> playerEntry : entityEntry.getValue().entrySet()) {
                Player player = playerEntry.getValue();
                player.setPosition(x, y);
            }
        }
    }

    public void setScale(float scale) {
        this.scale = scale;

        for(Map.Entry<Entity, Map<String, Player>> entityEntry : cachedPlayers.entrySet()) {
            for(Map.Entry<String, Player> playerEntry : entityEntry.getValue().entrySet()) {
                Player player = playerEntry.getValue();
                player.setScale(scale);
                spriteDrawer.setScale(player, scale);
            }
        }
    }

    //Entity
    public void setCurrentEntity(String entity) {
        this.currentEntity = scmlData.getEntity(entity);
    }

    //Players
    private Player cacheAndSetPlayer(String animation) {
        Player setMe = null;
        Map<String, Player> players = cachedPlayers.get(currentEntity);
        if(players != null) {
            Player player = players.get(animation);
            if(player != null) {
                setMe = player;
            } else {
                setMe = createPlayer(animation);
                cachedPlayers.get(currentEntity).put(animation, setMe);
            }
        } else {
            setMe = createPlayer(animation);
            HashMap<String, Player> map = new HashMap<>();
            map.put(animation, setMe);
            cachedPlayers.put(currentEntity, map);
        }
        return setMe;
    }

    private Player createPlayer(String animation) {
        Player setMe = new Player(currentEntity);
        setMe.setAnimation(animation);
        setMe.setPosition(this.x, this.y);
        setMe.setScale(this.scale);
        spriteDrawer.setScale(setMe, scale);

        return setMe;
    }

    public void setPlayAlways(String animation) {
        playAlways = cacheAndSetPlayer(animation);
    }

    public void setPlayAlwaysWithTweener(String animation, float weight) {
        //TODO if weight > 1 || weight < 0 -> error
    }

    public void setPlayOnce(String animation) {
        playOnce = cacheAndSetPlayer(animation);
        playOnce.addListener(cachedFinishListener);
    }

    public void setPlayOnceWithTweener(String animation, float weight) {
        //TODO if weight > 1 || weight < 0 -> error
    }

    public void update(float deltaTime) {
        //TODO default is 15 in trixt0r, 60fps is assumed as default -> 15*60
        int framesToPlayPerSecond = 15 * 60;
        //TODO >4000 fps esetén ez 1 és nem mozog az animáció, ez baj? úgyis limitálni kéne az fps-t...
        int speed = Math.round(framesToPlayPerSecond * deltaTime);

        if(playOnce != null) {
            playOnce.speed = speed;
            playOnce.update();
        } else if(playAlways != null) {
            playAlways.speed = speed;
            playAlways.update();
        }
    }

    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        spriteDrawer.setSpriteBatch(spriteBatch);
        spriteDrawer.setShapeRenderer(shapeRenderer);

        if(playOnce != null) {
            spriteDrawer.draw(playOnce);
        } else if(playAlways != null) {
            spriteDrawer.draw(playAlways);
        }
    }

    @Override
    public void dispose() {
        spriteLoader.dispose();
    }

    private class FinishListener implements Player.PlayerListener {

        @Override
        public void animationFinished(Animation animation) {
            playOnce = null;
        }

        @Override
        public void animationChanged(Animation oldAnim, Animation newAnim) {

        }

        @Override
        public void preProcess(Player player) {

        }

        @Override
        public void postProcess(Player player) {

        }

        @Override
        public void mainlineKeyChanged(Mainline.Key prevKey, Mainline.Key newKey) {

        }
    }
}
