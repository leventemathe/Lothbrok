package com.lothbrok.game.assets.animation.spriter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import com.brashmonkey.spriter.Animation;
import com.brashmonkey.spriter.Box;
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
    private LibGdxDrawer spriteDrawer;

    private float x = 0.0f;
    private float y = 0.0f;
    private float scale = 1.0f;

    private Entity currentEntity;
    private Player playOnce;
    private Player playAlways;
    private PlayerTweener playerTweener;

    //TODO lots of iterators are created for maps -> could cause GC stutter
    private Map<Entity, Map<String, Player>> cachedPlayers;
    private PlayerFinishedListener cachedPlayerFinishedListener;
    private PlayerTweenerFinishedListener cachedPlayerTweenerFinishedListener;

    public SpriterAnimation() {
        this.cachedPlayers = new HashMap<>();
        this.cachedPlayerFinishedListener = new PlayerFinishedListener();
        this.cachedPlayerTweenerFinishedListener = new PlayerTweenerFinishedListener();
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
        if(playerTweener != null) {
            playerTweener.setPosition(x, y);
        }
    }

    public void setScale(float scale) {
        this.scale = scale;

        for(Map.Entry<Entity, Map<String, Player>> entityEntry : cachedPlayers.entrySet()) {
            for(Map.Entry<String, Player> playerEntry : entityEntry.getValue().entrySet()) {
                Player player = playerEntry.getValue();
                player.setScale(scale);
            }
        }
        if(playerTweener != null) {
            playerTweener.setScale(scale);
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
        return setMe;
    }

    public void setPlayAlways(String animation) {
        playAlways = cacheAndSetPlayer(animation);
    }

    public void setPlayOnce(String animation) {
        playOnce = cacheAndSetPlayer(animation);
        playOnce.addListener(cachedPlayerFinishedListener);
    }

    public void setPlayerTweener(String doThis, String whileDoingThis, String baseBone) {
        Player doPlayer = cacheAndSetPlayer(doThis);
        Player whilePlayer = cacheAndSetPlayer(whileDoingThis);

        if(playerTweener == null) {
            playerTweener = new PlayerTweener(currentEntity);
            playerTweener.setScale(scale);
            playerTweener.setPosition(x, y);
        }
        playerTweener.setPlayers(doPlayer, whilePlayer);

        playerTweener.setBaseAnimation(whileDoingThis);
        playerTweener.baseBoneName = baseBone;
        playerTweener.setWeight(0f);

        doPlayer.addListener(cachedPlayerTweenerFinishedListener);
    }

    public void flip() {
        for(Map.Entry<Entity, Map<String, Player>> entityEntry : cachedPlayers.entrySet()) {
            for(Map.Entry<String, Player> playerEntry : entityEntry.getValue().entrySet()) {
                Player player = playerEntry.getValue();
                if(player.flippedX() == 1) {
                    player.flipX();
                }
            }
        }
        if(playerTweener != null) {
            if(playerTweener.flippedX() == 1) {
                playerTweener.flipX();
            };
        }
    }

    public void unflip() {
        for(Map.Entry<Entity, Map<String, Player>> entityEntry : cachedPlayers.entrySet()) {
            for(Map.Entry<String, Player> playerEntry : entityEntry.getValue().entrySet()) {
                Player player = playerEntry.getValue();
                if(player.flippedX() == -1) {
                    player.flipX();
                }
            }
        }
        if(playerTweener != null) {
            if(playerTweener.flippedX() == -1) {
                playerTweener.flipX();
            }
        }
    }

    //Update & render
    public void update(float deltaTime) {
        //TODO default is 15 in trixt0r, 60fps is assumed as default -> 15*60
        int framesToPlayPerSecond = 15 * 60;
        //TODO >4000 fps esetén ez 1 és nem mozog az animáció, ez baj? úgyis limitálni kéne az fps-t...
        int speed = Math.round(framesToPlayPerSecond * deltaTime);

        if(playerTweener != null) {
            playerTweener.getFirstPlayer().speed = speed;
            playerTweener.getSecondPlayer().speed = speed;
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
//            Box bodyBox = playAlways.getBox(playAlways.getObject(PLAYER_ANIMATION_SPRITE_BODY));
//            Box legBox = playAlways.getBox(playAlways.getObject(PLAYER_ANIMATION_SPRITE_LEG));
//            spriteDrawer.drawBox(bodyBox);
//            spriteDrawer.drawBox(legBox);
        }
    }

    public Rectangle getBoundingBox(String object) {
        Rectangle rect = null;
        if(playerTweener != null) {
            rect = createBoundingRect(playerTweener, object);
        } else if (playOnce != null) {
            rect = createBoundingRect(playOnce, object);
        } else if(playAlways != null) {
            rect = createBoundingRect(playAlways, object);
        }
        return rect;
    }

    // Getting a Box or Spriter Rectangle from a Player references and recalculates the same box and rect
    // Have to get a math Rectangle before recalculation
    private Rectangle createBoundingRect(Player player, String object) {
        Box box = player.getBox(player.getObject(object));
        com.brashmonkey.spriter.Rectangle spriterRect = box.getBoundingRect();
        return new Rectangle(spriterRect.left,
                spriterRect.bottom,
                spriterRect.right - spriterRect.left,
                spriterRect.top -spriterRect.bottom);
    }

    @Override
    public void dispose() {
        spriteLoader.dispose();
    }

    private class PlayerFinishedListener implements Player.PlayerListener {

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

    private class PlayerTweenerFinishedListener implements Player.PlayerListener {

        @Override
        public void animationFinished(Animation animation) {
            playerTweener = null;
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
