package com.lothbrok.game.assets.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.brashmonkey.spriter.Animation;
import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.Loader;
import com.brashmonkey.spriter.Mainline;
import com.brashmonkey.spriter.Player;
import com.brashmonkey.spriter.Player.PlayerListener;
import com.brashmonkey.spriter.PlayerTweener;
import com.brashmonkey.spriter.SCMLReader;
import com.lothbrok.game.assets.animation.spriter.LibGdxDrawer;
import com.lothbrok.game.assets.animation.spriter.LibGdxLoader;
import com.lothbrok.game.assets.animation.spriter.ScalingDrawer;

import java.util.HashMap;
import java.util.Map;

//TODO refactor this ASAP + tweener
public class SpriterAnimation implements Disposable {

    private static final String TAG = SpriterAnimation.class.getSimpleName();

    //TODO add error handling? if its necessary, maybe assets error handliong is enough
    private FileHandle handle;

    private SCMLReader reader;
    private Data data;

    //Map<EntitiyName, ArrayList<EntityAnimation>
    private Map<String, Player> players;
    private Player playOnce;
    private Player current;
    private PlayerTweener tweener;

    private Loader<Sprite> loader;
    private ScalingDrawer drawer;

    private float x = 0.0f;
    private float y = 0.0f;
    private float scale = 1.0f;

    public SpriterAnimation() {
        this.players = new HashMap<>();
    }

    public void loadScml(String path) {
        this.handle = Gdx.files.internal(path);
        this.reader = new SCMLReader(handle.read());
        this.data = reader.getData();
    }

    public void loadImages() {
        this.loader = new LibGdxLoader(this.data);
        this.loader.load(handle.file());
        this.drawer = new LibGdxDrawer(this.loader);
    }

    public void addEntity(String entity) {
        this.players.put(entity, new Player((data.getEntity(entity))));
    }

    //TODO maybe move this to constructor, like unitscale in tiled
    public void scale(float scale) {
        this.scale = scale;
        for(Map.Entry<String, Player> entry : players.entrySet()) {
            Player player = entry.getValue();
            player.scale(scale);
            drawer.scale(player, scale);
        }
        if(current != null) {
           // current.scale(scale);
           // drawer.scale(current, scale);
        }
        if(playOnce != null) {
            playOnce.scale(scale);
            drawer.scale(playOnce, scale);
        }
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        for(Map.Entry<String, Player> entry : players.entrySet()) {
            Player player = entry.getValue();
            player.setPosition(x, y);
        }
        //if(current != null)
            //current.setPosition(x, y);
        if(playOnce != null)
            playOnce.setPosition(x, y);
    }

    public  void translatePosition(float x, float y) {
        this.x += x;
        this.y += y;
        for(Map.Entry<String, Player> entry : players.entrySet()) {
            Player player = entry.getValue();
            player.translatePosition(x, y);
        }
        //if(current != null)
            //current.translatePosition(x, y);
        if(playOnce != null)
            playOnce.translatePosition(x, y);
    }

    public void setIdle(String entity, String animation) {
        current = players.get(entity);
        //current.setPosition(this.x, this.y);
        //drawer.scale(current, scale);
        current.setAnimation(animation);
    }

    public void playOnce(String entity, String animation) {
        //TODO this is very inefficient (GC) -> cache
        playOnce = new Player(data.getEntity(entity));
        playOnce.setPosition(this.x, this.y);
        playOnce.scale(this.scale);
        drawer.scale(playOnce, scale);
        playOnce.setAnimation(animation);
        playOnce.addListener(new SpriterAnimationListener());

        tweener = new PlayerTweener(current, playOnce);
        tweener.setWeight(0.7f);
    }

    public void update(float deltaTime) {
        int framesToPlayPerSecond = 15 * 60; // default is 15 in trixt0r, 60fps is assumed as default -> 15*60
        //TODO >4000 fps esetén ez 1 és nem mozog az animáció, ez baj? úgyis limitálni kéne az fps-t...
        int speed = Math.round(framesToPlayPerSecond * deltaTime);
        if(tweener != null) {
            //TODO a playerben és playertweenerben is csinálni egy deltaTime-os update-et?
            tweener.getFirstPlayer().speed = speed;
            tweener.getSecondPlayer().speed = speed;
            tweener.update();
        } else if(playOnce != null) {
            playOnce.speed = speed;
            playOnce.update();
        } else if(current != null){
            current.speed = speed;
            current.update();
        }
    }

    public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        drawer.setSpriteBatch(spriteBatch);
        drawer.setShapeRenderer(shapeRenderer);

        if(tweener != null) {
            Player tweenerPlayer = new Player(tweener.getEntity());
            tweenerPlayer.scale(scale);
            tweenerPlayer.setPosition(x, y);
            tweenerPlayer.setAnimation(tweener.getAnimation());
            drawer.draw(tweenerPlayer);
        } else if(playOnce != null) {
            drawer.draw(playOnce);
        } else if(current != null) {
            drawer.draw(current);
        }
    }

    @Override
    public void dispose() {
        loader.dispose();
    }



    private class SpriterAnimationListener implements PlayerListener {

        @Override
        public void animationFinished(Animation animation) {
            playOnce = null;
            tweener = null;
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
