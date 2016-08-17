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

    private Map<String, Player> players;
    private Player playMe;
    private Player idle;

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
        if(idle != null) {
           // idle.scale(scale);
           // drawer.scale(idle, scale);
        }
        if(playMe != null) {
            playMe.scale(scale);
            drawer.scale(playMe, scale);
        }
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        for(Map.Entry<String, Player> entry : players.entrySet()) {
            Player player = entry.getValue();
            player.setPosition(x, y);
        }
        //if(idle != null)
            //idle.setPosition(x, y);
        if(playMe != null)
            playMe.setPosition(x, y);
    }

    public  void translatePosition(float x, float y) {
        this.x += x;
        this.y += y;
        for(Map.Entry<String, Player> entry : players.entrySet()) {
            Player player = entry.getValue();
            player.translatePosition(x, y);
        }
        //if(idle != null)
            //idle.translatePosition(x, y);
        if(playMe != null)
            playMe.translatePosition(x, y);
    }

    public void setIdle(String entity, String animation) {
        idle = players.get(entity);
        //idle.setPosition(this.x, this.y);
        //idle.scale(scale);
        drawer.scale(idle, scale);
        idle.setAnimation(animation);
    }

    public void playOnce(String entity, String animation) {
        playMe = new Player(data.getEntity(entity));
        playMe.setPosition(this.x, this.y);
        playMe.scale(this.scale);
        drawer.scale(playMe, scale);
        playMe.setAnimation(animation);
        playMe.addListener(new SpriterAnimationListener());
    }

    public void update(float deltaTime) {
        int framesToPlayPerSecond = 15 * 60; // default is 15 in trixt0r, 60fps is assumed as default -> 15*60
        //TODO >4000 fps esetén ez 1 és nem mozog az animáció, ez baj? úgyis limitálni kéne az fps-t...
        int speed = Math.round(framesToPlayPerSecond * deltaTime);
        if(playMe != null) {
            playMe.speed = speed;
            playMe.update();
        } else if(idle != null){
            idle.speed = speed;
            idle.update();
        }
    }

    public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        drawer.setSpriteBatch(spriteBatch);
        drawer.setShapeRenderer(shapeRenderer);

        if(playMe != null) {
            drawer.draw(playMe);
        } else if(idle != null) {
            drawer.draw(idle);
        }
    }

    @Override
    public void dispose() {
        loader.dispose();
    }



    private class SpriterAnimationListener implements PlayerListener {

        @Override
        public void animationFinished(Animation animation) {
            playMe = null;
            //TODO remove this ASAP and fix scaling, positioning etc -> store values so they can be applied to new players
            //idle.setAnimation("idle");
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
