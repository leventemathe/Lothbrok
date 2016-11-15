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
import com.brashmonkey.spriter.PlayerTweener;
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
    private PlayerTweener playerTweener;

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

    // Tween animation with playOnce to play always
    // Can't generalize: make specific methods
    public void setPlayAlwaysWithTweener(String animation, float weight) {
        if(weight > 1f || weight < 0f) {
            Gdx.app.error(TAG, "Bad params: tween weight");
            return;
        }
        if(playOnce == null) {
            playAlways = cacheAndSetPlayer(animation);
            //Gdx.app.debug(TAG, "Play once is null: can't tween");
            return;
        }

        Player playMe = cacheAndSetPlayer(animation);
        PlayerTweener tweener = new PlayerTweener(playOnce, playMe);
        tweener.setWeight(weight);
        playAlways = tweener;
    }

    public void setPlayAlwaysWithTweener(String animation) {
        setPlayAlwaysWithTweener(animation, 0.5f);
    }

    public void setPlayOnce(String animation) {
        playOnce = cacheAndSetPlayer(animation);
        playOnce.addListener(cachedFinishListener);
    }

    // Tween animation with playAlways to play once
    // Can't generalize: make specific methods
    public void setPlayOnceWithTweener(String animation, float weight) {
        if(weight > 1f || weight < 0f) {
            Gdx.app.error(TAG, "Bad params: tween weight");
            return;
        }
        if(playAlways == null) {
            Gdx.app.error(TAG, "Play always is null: can't tween");
            return;
        }

        Player playMe = cacheAndSetPlayer(animation);
        PlayerTweener tweener = new PlayerTweener(playAlways, playMe);
        tweener.setWeight(weight);
        playOnce = tweener;
        playOnce.addListener(cachedFinishListener);
    }

    public void setPlayOnceWithTweener(String animation) {
        setPlayOnceWithTweener(animation, 0.5f);
    }

    public void setPlayerTweener(String animation1, String animation2, String baseBone) {
        Player player1 = cacheAndSetPlayer(animation1);
        Player player2 = cacheAndSetPlayer(animation2);

        playerTweener = new PlayerTweener(currentEntity);

        playerTweener.setScale(scale);
        playerTweener.setPlayers(player1, player2);
        playerTweener.setPosition(x, y);
        spriteDrawer.setScale(playerTweener, scale);

        playerTweener.setBaseAnimation(animation1);
        playerTweener.getSecondPlayer().setAnimation(animation1);
        playerTweener.getFirstPlayer().setAnimation(animation2);
        playerTweener.baseBoneName = "swordarmbone";
        playerTweener.setWeight(0f);

        //playerTweener.getSecondPlayer().addListener(cachedFinishListener);
    }

    public void update(float deltaTime) {
        //TODO default is 15 in trixt0r, 60fps is assumed as default -> 15*60
        int framesToPlayPerSecond = 15 * 60;
        //TODO >4000 fps esetén ez 1 és nem mozog az animáció, ez baj? úgyis limitálni kéne az fps-t...
        int speed = Math.round(framesToPlayPerSecond * deltaTime);

        if(playerTweener != null) {
            playerTweener.speed = speed;
            playerTweener.update();
        } else if(playOnce != null) {
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

        if(playerTweener != null) {
            spriteDrawer.draw(playerTweener);
        } else if(playOnce != null) {
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
            //playerTweener = null;
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
