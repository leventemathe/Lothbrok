package com.lothbrok.game.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
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
import com.lothbrok.game.assets.entities.animation.EnemyAnimation;
import com.lothbrok.game.assets.entities.animation.PlayerAnimation;
import com.lothbrok.game.assets.spriter.SpriterAnimation;
import com.lothbrok.game.constants.AnimationConstants;
import com.lothbrok.game.constants.Colors;
import com.lothbrok.game.constants.Resolution;
import com.lothbrok.game.model.GameModel;
import com.lothbrok.game.model.entities.Enemy;
import com.lothbrok.game.model.entities.Entity;
import com.lothbrok.game.model.entities.Player;
import com.lothbrok.game.model.entities.Treasure;

public class GameRenderer implements Disposable {

    private Assets assets;

    private ExtendedCamera extendedCamera;
    private Viewport viewport;

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private MyOrthogonalTiledMapRenderer mapRenderer;

    private Player player;
    private PlayerAnimation playerAnimation;

    private ObjectMap<Enemy, EnemyAnimation> enemyAnimations;

    private ObjectMap<Treasure, TextureRegion> treasureTextures;

    public GameRenderer(GameModel gameModel, SpriteBatch batch, ShapeRenderer shapeRenderer, Assets assets) {
        this.assets = assets;

        this.player = gameModel.getPlayer();

        setupViewPort(gameModel);
        setupAnimation(gameModel);
        setupRendering(batch, shapeRenderer, gameModel);
        treasureTextures = new ObjectMap<>();
    }

    private void setupViewPort(GameModel gameModel) {
        extendedCamera = new ExtendedCamera(new OrthographicCamera(), gameModel.getMap());
        float aspect = (float)Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight();
        float height = 4;
        float width = height * aspect;
        viewport = new ExtendViewport(width, height, extendedCamera.getCamera());
    }

    private void setupAnimation(GameModel gameModel) {
        playerAnimation = new PlayerAnimation(new SpriterAnimation(assets.getPlayerAnimationAssets()));
        playerAnimation.getAnimation().setScale(Resolution.instance.getWorldScale());

        Array<Enemy> enemies = gameModel.getEnemies();
        enemyAnimations = new ObjectMap<>();
        for(int i = 0; i < enemies.size; i++) {
            EnemyAnimation animation = new EnemyAnimation(new SpriterAnimation(assets.getEnemyAnimationAssets()));
            animation.getAnimation().setScale(Resolution.instance.getWorldScale());
            enemyAnimations.put(enemies.get(i), animation);
        }
    }

    private void setupRendering(SpriteBatch batch, ShapeRenderer shapeRenderer, GameModel gameModel) {
        this.spriteBatch = batch;
        this.shapeRenderer = shapeRenderer;
        mapRenderer = new MyOrthogonalTiledMapRenderer(gameModel.getMap(), Resolution.instance.getWorldScale(), spriteBatch, shapeRenderer);
    }

    public void addLostTreasure(Treasure treasure) {
        Sprite sprite = new Sprite(assets.getCoin());
        treasureTextures.put(treasure, sprite);
    }

    public void removeLostTreasure(Treasure treasure) {
        treasureTextures.remove(treasure);
    }

    public void render(float deltaTime) {
        viewport.apply();
        renderSky();
        renderMap();

        spriteBatch.setProjectionMatrix(extendedCamera.getCamera().combined);
        spriteBatch.begin();
        renderLostTreasure();
        renderAnimation(deltaTime);
        spriteBatch.end();
        renderPlayerRectangles();
    }

    public void renderWithoutUpdate(float deltaTime) {
        viewport.apply();
        renderSky();
        renderMap();

        spriteBatch.setProjectionMatrix(extendedCamera.getCamera().combined);
        spriteBatch.begin();
        renderLostTreasure();
        renderAnimationWithoutUpdate(deltaTime);
        spriteBatch.end();
        renderPlayerRectangles();
    }

    private void renderSky() {
        Camera camera = this.extendedCamera.getCamera();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Colors.SKY_BLUE);
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
        renderEnemyAnimationsWithUpdate(deltaTime);
        renderPlayerAnimationWithUpdate(deltaTime);
    }

    private void renderAnimationWithoutUpdate(float deltaTime) {
        renderEnemyAnimationsWithoutUpdate(deltaTime);
        renderPlayerAnimationWithoutUpdate(deltaTime);
    }

    private void renderPlayerAnimationWithoutUpdate(float deltaTime) {
        preparePlayerAnimation(deltaTime);
        playerAnimation.getAnimation().render(spriteBatch, shapeRenderer);
    }

    private void renderPlayerAnimationWithUpdate(float deltaTime) {
        preparePlayerAnimation(deltaTime);
        playerAnimation.getAnimation().update(deltaTime);
        playerAnimation.getAnimation().render(spriteBatch, shapeRenderer);
    }

    private void preparePlayerAnimation(float deltaTime) {
        SpriterAnimation animation = playerAnimation.getAnimation();
        Entity.ActionState actionState = player.actionState;
        Entity.MovementState movementState = player.movementState;
        Entity.LifeState lifeState = player.lifeState;
        Entity.Direction direction = player.direction;

        animation.setPosition(player.position.x, player.position.y);

        if(actionState == Entity.ActionState.ATTACKING) {
            if(movementState == Entity.MovementState.STANDING) {
                animation.setPlayOnce(AnimationConstants.PLAYER_ANIMATION_ATTACKING);
            }
            else if(movementState == Entity.MovementState.MOVING){
                playerAnimation.attackWhileMoving();
            }
        } else if(actionState == Entity.ActionState.FALLING) {
            animation.setPlayAlways(AnimationConstants.PLAYER_ANIMATION_FALLING);
        } else if(actionState == Entity.ActionState.JUMPING) {
            animation.setPlayAlways(AnimationConstants.PLAYER_ANIMATION_JUMPING);
        } else if(movementState == Entity.MovementState.MOVING) {
            animation.setPlayAlways(AnimationConstants.PLAYER_ANIMATION_WALKING);
        } else {
            animation.setPlayAlways(AnimationConstants.PLAYER_ANIMATION_IDLE);
        }

        if(lifeState == Entity.LifeState.DYING) {
            animation.setPlayOnce(AnimationConstants.PLAYER_ANIMATION_DEATH);
        } else if(lifeState == Entity.LifeState.DEAD) {
            animation.disable();
        }

        if(direction == Entity.Direction.RIGHT) {
            animation.faceRight();
        } else if(direction == Entity.Direction.LEFT) {
            animation.faceLeft();
        }
    }

    private void renderEnemyAnimationsWithUpdate(float deltaTime) {
        for(ObjectMap.Entry<Enemy, EnemyAnimation> entry : enemyAnimations.entries()) {
            prepareEnemyAnimation(deltaTime, entry);
            renderEnemyAnimationWithUpdate(deltaTime, entry.value);
        }
    }

    private void renderEnemyAnimationsWithoutUpdate(float deltaTime) {
        for(ObjectMap.Entry<Enemy, EnemyAnimation> entry : enemyAnimations.entries()) {
            prepareEnemyAnimation(deltaTime, entry);
            renderEnemyAnimationWithoutUpdate(deltaTime, entry.value);
        }
    }

    private void renderEnemyAnimationWithUpdate(float deltaTime, EnemyAnimation animation) {
        animation.getAnimation().update(deltaTime);
        animation.getAnimation().render(spriteBatch, shapeRenderer);
    }

    private void renderEnemyAnimationWithoutUpdate(float deltaTime, EnemyAnimation animation) {
        animation.getAnimation().render(spriteBatch, shapeRenderer);
    }

    private void prepareEnemyAnimation(float deltaTime, ObjectMap.Entry<Enemy, EnemyAnimation> entry) {
        SpriterAnimation animation = entry.value.getAnimation();
        Entity.ActionState actionState = entry.key.actionState;
        Entity.LifeState lifeState = entry.key.lifeState;
        Entity.MovementState movementState = entry.key.movementState;
        Entity.Direction direction = entry.key.direction;

        if(lifeState == Entity.LifeState.DYING) {
            animation.setPlayOnce(AnimationConstants.ENEMY_ANIMATION_DEATH);
        } else if(lifeState == Entity.LifeState.DEAD) {
            animation.setPlayAlways(AnimationConstants.ENEMY_ANIMATION_DEAD);
        } else {
            if (actionState == Entity.ActionState.ATTACKING) {
                animation.setPlayOnce(AnimationConstants.ENEMY_ANIMATION_ATTACKING);
            } else if (movementState == Entity.MovementState.MOVING) {
                animation.setPlayAlways(AnimationConstants.ENEMY_ANIMATION_WALKING);
            } else {
                animation.setPlayAlways(AnimationConstants.ENEMY_ANIMATION_IDLE);
            }
            if(direction == Entity.Direction.RIGHT) {
                animation.faceRight();
            } else if(direction == Entity.Direction.LEFT) {
                animation.faceLeft();
            }
        }

        animation.setPosition(entry.key.position.x, entry.key.position.y);
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

    public void renderPlayerRectangles() {
        if(player == null) {
            return;
        }
        Rectangle body = player.getBodyBox();
        Rectangle weapon = player.getWeaponBox();
        Rectangle footSensor = player.getFootSensor();
        Rectangle headSensor = player.getHeadSensor();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(body.x, body.y, body.width, body.height);
        shapeRenderer.rect(weapon.x, weapon.y, weapon.width, weapon.height);
        shapeRenderer.rect(footSensor.x, footSensor.y, footSensor.width, footSensor.height);
        shapeRenderer.rect(headSensor.x, headSensor.y, headSensor.width, headSensor.height);
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
