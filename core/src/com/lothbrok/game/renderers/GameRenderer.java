package com.lothbrok.game.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.assets.entities.EnemyAnimation;
import com.lothbrok.game.assets.entities.PlayerAnimation;
import com.lothbrok.game.assets.spriter.SpriterAnimation;
import com.lothbrok.game.assets.utils.AssetsConstants;
import com.lothbrok.game.model.GameModel;
import com.lothbrok.game.model.entities.Enemy;
import com.lothbrok.game.model.entities.Entity;
import com.lothbrok.game.model.entities.Treasure;

public class GameRenderer implements Disposable {

    private GameModel gameModel;

    private ExtendedCamera extendedCamera;
    private Viewport viewport;

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private MyOrthogonalTiledMapRenderer mapRenderer;
    private PlayerAnimation playerAnimation;
    private ObjectMap<Enemy, EnemyAnimation> enemyAnimations;
    private ObjectMap<Treasure, TextureRegion> treasureTextures;

    int counter = 0;

    public GameRenderer(GameModel gameModel, SpriteBatch batch, ShapeRenderer shapeRenderer) {
        this.gameModel = gameModel;

        setupViewPort();
        setupAnimation();
        setupRendering(batch, shapeRenderer);
        treasureTextures = new ObjectMap<>();
    }

    private void setupViewPort() {
        extendedCamera = new ExtendedCamera(new OrthographicCamera(), gameModel.getMap());
        //TODO custom viewport (or maybe extended), now it sets it to the size of the desktop launcher/android screen size
        float aspect = (float) Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight();
        float height = 4;
        float width = height * aspect;
        viewport = new ExtendViewport(width, height, extendedCamera.getCamera());
    }

    private void setupAnimation() {
        playerAnimation = new PlayerAnimation(new SpriterAnimation(Assets.instance.getPlayerAnimationAssets()));
        //TODO get scale from m l xl etc
        playerAnimation.getAnimation().setScale(1f/540f); //xl

        Array<Enemy> enemies = gameModel.getEnemies();
        enemyAnimations = new ObjectMap<>();
        for(int i = 0; i < enemies.size; i++) {
            EnemyAnimation animation = new EnemyAnimation(new SpriterAnimation(Assets.instance.getEnemyAnimationAssets()));
            animation.getAnimation().setScale(1f/540f); //xl
            enemyAnimations.put(enemies.get(i), animation);
        }
    }

    private void setupRendering(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        this.spriteBatch = batch;
        this.shapeRenderer = shapeRenderer;
        //TODO get scale from m l xl etc
        mapRenderer = new MyOrthogonalTiledMapRenderer(gameModel.getMap(), 1f/540f, spriteBatch, shapeRenderer); //xl
    }

    public void addLostTreasure(Treasure treasure) {
        Sprite sprite = new Sprite(Assets.instance.getCoin());
        treasureTextures.put(treasure, sprite);
    }

    public void removeLostTreasure(Treasure treasure) {
        treasureTextures.remove(treasure);
    }

    public void render(float deltaTime) {
        viewport.apply();
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderSky();
        renderMap();

        spriteBatch.setProjectionMatrix(extendedCamera.getCamera().combined);
        spriteBatch.begin();
        renderLostTreasure();
        renderAnimation(deltaTime);
        spriteBatch.end();
//
//        byte[] pixels = ScreenUtils.getFrameBufferPixels(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), true);
//        Pixmap pixmap = new Pixmap(Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), Pixmap.Format.RGBA8888);
//        BufferUtils.copy(pixels, 0, pixmap.getPixels(), pixels.length);
//        PixmapIO.writePNG(Gdx.files.external("screenshots/mypixmap" + Integer.toString(counter) + ".png"), pixmap);
//        pixmap.dispose();
//        counter++;
    }

    private void renderSky() {
        Camera camera = this.extendedCamera.getCamera();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        //TODO get sky color from somewhere (also change in menu?)
        shapeRenderer.setColor(0f, 0f, 0.5f, 1f);
        shapeRenderer.rect(camera.position.x - camera.viewportWidth/2,
                           camera.position.y - camera.viewportHeight/2,
                           camera.viewportWidth,
                           camera.viewportHeight);
        shapeRenderer.end();
    }

    private void renderMap() {
        mapRenderer.setView((OrthographicCamera) extendedCamera.getCamera());
        mapRenderer.render();
    }

    private void renderAnimation(float deltaTime) {
        renderEnemyAnimations(deltaTime);
        renderPlayerAnimation(deltaTime);
    }

    private void renderPlayerAnimation(float deltaTime) {
        SpriterAnimation animation = playerAnimation.getAnimation();
        Entity.ActionState actionState = gameModel.getPlayer().actionState;
        Entity.MovementState movementState = gameModel.getPlayer().movementState;
        Entity.LifeState lifeState = gameModel.getPlayer().lifeState;
        Entity.Direction direction = gameModel.getPlayer().direction;

        //TODO move all animationchanging to playerAnimation from animation
        if(actionState == Entity.ActionState.ATTACKING) {
            if(movementState == Entity.MovementState.STANDING) {
                animation.setPlayOnce(AssetsConstants.PLAYER_ANIMATION_ATTACKING);
            }
            else if(movementState == Entity.MovementState.MOVING){
                playerAnimation.attackWhileMoving();
            }
        } else if(actionState == Entity.ActionState.FALLING) {
            animation.setPlayAlways(AssetsConstants.PLAYER_ANIMATION_FALLING);
        } else if(actionState == Entity.ActionState.JUMPING) {
            animation.setPlayAlways(AssetsConstants.PLAYER_ANIMATION_JUMPING);
        } else if(movementState == Entity.MovementState.MOVING) {
            animation.setPlayAlways(AssetsConstants.PLAYER_ANIMATION_WALKING);
        } else {
            animation.setPlayAlways(AssetsConstants.PLAYER_ANIMATION_IDLE);
        }

        if(lifeState == Entity.LifeState.DYING) {
            animation.setPlayOnce(AssetsConstants.PLAYER_ANIMATION_DEATH);
        } else if(lifeState == Entity.LifeState.DEAD) {
            animation.setPlayAlways(AssetsConstants.PLAYER_ANIMATION_DEAD);
        }

        if(direction == Entity.Direction.RIGHT) {
            animation.faceRight();
        } else if(direction == Entity.Direction.LEFT) {
            animation.faceLeft();
        }

        animation.setPosition(gameModel.getPlayer().position.x, gameModel.getPlayer().position.y);
        animation.update(deltaTime);
        animation.render(spriteBatch, shapeRenderer);
    }

    private void renderEnemyAnimations(float deltaTime) {
        for(ObjectMap.Entry<Enemy, EnemyAnimation> entry : enemyAnimations.entries()) {
            SpriterAnimation animation = entry.value.getAnimation();
            Entity.ActionState actionState = entry.key.actionState;
            Entity.LifeState lifeState = entry.key.lifeState;
            Entity.MovementState movementState = entry.key.movementState;
            Entity.Direction direction = entry.key.direction;

            if(lifeState == Entity.LifeState.DYING) {
                animation.setPlayOnce(AssetsConstants.ENEMY_ANIMATION_DEATH);
            } else if(lifeState == Entity.LifeState.DEAD) {
                animation.setPlayAlways(AssetsConstants.ENEMY_ANIMATION_DEAD);
            } else {
                if (actionState == Entity.ActionState.ATTACKING) {
                    animation.setPlayOnce(AssetsConstants.ENEMY_ANIMATION_ATTACKING);
                } else if (movementState == Entity.MovementState.MOVING) {
                    animation.setPlayAlways(AssetsConstants.ENEMY_ANIMATION_WALKING);
                } else {
                    animation.setPlayAlways(AssetsConstants.ENEMY_ANIMATION_IDLE);
                }
                if(direction == Entity.Direction.RIGHT) {
                    animation.faceRight();
                } else if(direction == Entity.Direction.LEFT) {
                    animation.faceLeft();
                }
            }

            animation.setPosition(entry.key.position.x, entry.key.position.y);
            animation.update(deltaTime);
            animation.render(spriteBatch, shapeRenderer);
        }
    }

    private void renderLostTreasure() {
        for(ObjectMap.Entry<Treasure, TextureRegion> entry : treasureTextures.entries()) {
            TextureRegion texture = entry.value;
            Treasure treasure = entry.key;
            float x = treasure.getX();
            float y = treasure.getY();
            float width = treasure.getWidth();
            float height = treasure.getHeight();
            float originX = width/2f;
            float originY = height/2f;
            float rotation = treasure.getBody().getAngle();
            spriteBatch.draw(texture, x, y, originX, originY, width, height, 1f, 1f, (float)Math.toDegrees(rotation));
        }
    }

    public void renderRectangle(Rectangle rect) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
        shapeRenderer.end();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        mapRenderer.dispose();
    }

    public ExtendedCamera getExtendedCamera() {
        return extendedCamera;
    }

    public PlayerAnimation getPlayerAnimation() {
        return playerAnimation;
    }

    public ObjectMap<Enemy, EnemyAnimation> getEnemyAnimations() {
        return enemyAnimations;
    }
}
