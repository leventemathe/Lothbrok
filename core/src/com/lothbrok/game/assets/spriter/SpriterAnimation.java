package com.lothbrok.game.assets.spriter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.brashmonkey.spriter.Animation;
import com.brashmonkey.spriter.Box;
import com.brashmonkey.spriter.Entity;
import com.brashmonkey.spriter.Mainline;
import com.brashmonkey.spriter.Player;
import com.brashmonkey.spriter.PlayerTweener;
import com.brashmonkey.spriter.Timeline;

import java.util.HashMap;
import java.util.Map;

public class SpriterAnimation {

    private static final String TAG = SpriterAnimation.class.getSimpleName();

    private SpriterAnimationAssets assets;
    private LibGdxDrawer spriteDrawer;

    private float x = 0.0f;
    private float y = 0.0f;
    private float scale = 1.0f;

    private enum Direction {
        LEFT,
        RIGHT
    }

    private Direction direction = Direction.RIGHT;

    private Entity currentEntity;
    private Player playOnce;
    private Player playAlways;
    private PlayerTweener playerTweener;

    private boolean enabled = true;

    private Map<Entity, Map<String, Player>> cachedPlayers;
    private PlayerTweener cachedPlayerTweener;
    private PlayerFinishedListener cachedPlayerFinishedListener;
    private PlayerTweenerFinishedListener cachedPlayerTweenerFinishedListener;

    private Player.PlayerListener controllerListener;

    public SpriterAnimation(SpriterAnimationAssets assets) {
        this.assets = assets;
        this.spriteDrawer = new LibGdxDrawer(assets.getSpriteLoader());

        this.cachedPlayers = new HashMap<>();
        this.cachedPlayerFinishedListener = new PlayerFinishedListener();
        this.cachedPlayerTweenerFinishedListener = new PlayerTweenerFinishedListener();
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
        if(cachedPlayerTweener != null) {
            cachedPlayerTweener.setPosition(x, y);
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
        if(cachedPlayerTweener != null) {
            cachedPlayerTweener.setScale(scale);
        }
    }

    //Entity
    public void setCurrentEntity(String entity) {
        this.currentEntity = assets.getScmlData().getEntity(entity);
    }

    //Players
    private Player cacheAndSetPlayer(String animation) {
        Player setMe;
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
        if(direction == Direction.LEFT) {
            setMe.flipX();
        }
        return setMe;
    }

    private PlayerTweener cacheAndSetPlayerTweener() {
        if(cachedPlayerTweener == null) {
            cachedPlayerTweener = createPlayerTweener();
        }
        return cachedPlayerTweener;
    }

    private PlayerTweener createPlayerTweener() {
        PlayerTweener setMe = new PlayerTweener(currentEntity);
        setMe.setPosition(this.x, this.y);
        setMe.setScale(this.scale);
        if(direction == Direction.LEFT) {
            setMe.flipX();
        }
        return setMe;
    }

    public void setPlayAlways(String animation) {
        playAlways = cacheAndSetPlayer(animation);
    }

    public void setPlayOnce(String animation) {
        if(playOnce != null) {
            //return;
        }
        playOnce = cacheAndSetPlayer(animation);
        playOnce.removeListener(cachedPlayerFinishedListener);
        playOnce.addListener(cachedPlayerFinishedListener);
        if(controllerListener != null) {
            playOnce.removeListener(controllerListener);
            playOnce.addListener(controllerListener);
        }
    }

    public void setPlayerTweener(String doThis, String whileDoingThis, String baseBone) {
        setPlayOnce(doThis);
        Player doPlayer = playOnce;

        setPlayAlways(whileDoingThis);
        Player whilePlayer = playAlways;

        if(playerTweener == null) {
            playerTweener = cacheAndSetPlayerTweener();
        }
        playerTweener.setPlayers(doPlayer, whilePlayer);
        playerTweener.setBaseAnimation(whileDoingThis);
        playerTweener.baseBoneName = baseBone;
        playerTweener.setWeight(0f);

        doPlayer.removeListener(cachedPlayerTweenerFinishedListener);
        doPlayer.addListener(cachedPlayerTweenerFinishedListener);
    }

    public void faceLeft() {
        if(direction == Direction.LEFT) {
            return;
        }
        for(Map.Entry<Entity, Map<String, Player>> entityEntry : cachedPlayers.entrySet()) {
            for(Map.Entry<String, Player> playerEntry : entityEntry.getValue().entrySet()) {
                Player player = playerEntry.getValue();
                if(player.flippedX() == 1) {
                    player.flipX();
                }
            }
        }
        if(cachedPlayerTweener != null) {
            if(cachedPlayerTweener.flippedX() == 1) {
                cachedPlayerTweener.flipX();
            };
        }
        direction = Direction.LEFT;
    }

    public void faceRight() {
        if(direction == Direction.RIGHT) {
            return;
        }
        for(Map.Entry<Entity, Map<String, Player>> entityEntry : cachedPlayers.entrySet()) {
            for(Map.Entry<String, Player> playerEntry : entityEntry.getValue().entrySet()) {
                Player player = playerEntry.getValue();
                if(player.flippedX() == -1) {
                    player.flipX();
                }
            }
        }
        if(cachedPlayerTweener != null) {
            if(cachedPlayerTweener.flippedX() == -1) {
                cachedPlayerTweener.flipX();
            }
        }
        direction = Direction.RIGHT;
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }

    //Update & render
    public void update(float deltaTime) {
        if(!enabled && playOnce == null && playerTweener == null) {
            return;
        }

        int framesToPlayPerSecond = 15 * 60;
        int speed = Math.round(framesToPlayPerSecond * deltaTime);

        if(playerTweener != null) {
            playerTweener.getFirstPlayer().speed = speed;
            playerTweener.getSecondPlayer().speed = speed;
            playerTweener.update();
            if(playerTweener == null) {
                playAlways.speed = speed;
                playAlways.update();
            }
        } else if(playOnce != null) {
            playOnce.speed = speed;
            playOnce.update();
            if(playOnce == null) {
                playAlways.speed = speed;
                playAlways.update();
            }
        } else if(playAlways != null) {
            playAlways.speed = speed;
            playAlways.update();
        }
    }

    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        if(!enabled && playOnce == null && playerTweener == null) {
            return;
        }

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

    public Rectangle getBoundingBox(String object) {
        Rectangle rect = null;
        if(playerTweener != null) {
            rect = createBoundingRect(playerTweener, object);
        } else if (playOnce != null) {
            rect = createBoundingRect(playOnce, object);
        } else if(playAlways != null) {
            rect = createBoundingRect(playAlways, object);
        }
        if(rect == null) {
            cachedBoundingRect.set(0.0f, 0.0f, 0.0f, 0.0f);
            return cachedBoundingRect;
        }
        return rect;
    }

    // Getting a Box or Spriter Rectangle from a Player references and recalculates the same box and rect
    // Have to get a math Rectangle before recalculation
    private Rectangle cachedBoundingRect = new Rectangle();

    private Rectangle createBoundingRect(Player player, String object) {
        if(player == null || object == null) {
            return null;
        }
        Timeline.Key.Object obj;
        try {
            obj = player.getObject(object);
        } catch (NullPointerException e) {
            return null;
        }
        Box box = player.getBox(obj);
        com.brashmonkey.spriter.Rectangle spriterRect = box.getBoundingRect();

        cachedBoundingRect.x = spriterRect.left;
        cachedBoundingRect.y = spriterRect.bottom;
        cachedBoundingRect.width = spriterRect.right - spriterRect.left;
        cachedBoundingRect.height = spriterRect.top -spriterRect.bottom;
        return cachedBoundingRect;
    }

    public Player getPlayOnce() {
        return playOnce;
    }

    public Player getPlayAlways() {
        return playAlways;
    }

    public PlayerTweener getPlayerTweener() {
        return playerTweener;
    }

    public void setControllerListener(Player.PlayerListener listener) {
        controllerListener = listener;
    }

    public boolean facingLeft() {
        return direction == Direction.LEFT;
    }

    public boolean facingRight() {
        return direction == Direction.RIGHT;
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
