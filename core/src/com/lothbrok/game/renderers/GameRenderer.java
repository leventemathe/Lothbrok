package com.lothbrok.game.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.assets.animation.spriter.SpriterAnimation;
import com.lothbrok.game.assets.entities.PlayerAnimation;
import com.lothbrok.game.assets.utils.AssetsConstants;
import com.lothbrok.game.model.GameModel;
import com.lothbrok.game.model.entities.MovingEntity;

public class GameRenderer implements Disposable {

    private GameModel gameModel;

    private ExtendedCamera extendedCamera;
    private Viewport viewport;

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private MyOrthogonalTiledMapRenderer mapRenderer;
    private PlayerAnimation playerAnimation;

    public GameRenderer(GameModel gameModel, SpriteBatch batch, ShapeRenderer shapeRenderer) {
        this.gameModel = gameModel;

        setupViewPort();
        setupAnimation();
        setupRendering(batch, shapeRenderer);
    }

    private void setupViewPort() {
        extendedCamera = new ExtendedCamera(new OrthographicCamera(), gameModel.getMap());
        //TODO custom viewport (or maybe extended), now it sets it to the size of the desktop launcher/android screen size
        float aspect = (float) Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight();
        float height = 4;
        float width = height * aspect;
        viewport = new ExtendViewport(width, height, extendedCamera.getCamera());
    }

    //TODO move to logic?
    private void setupAnimation() {
        playerAnimation = new PlayerAnimation(new SpriterAnimation(Assets.instance.getPlayerAnimationAssets()));
        //TODO get scale from m l xl etc
        playerAnimation.getAnimation().setScale(1f/540f); //xl
    }

    private void setupRendering(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        this.spriteBatch = batch;
        this.shapeRenderer = shapeRenderer;
        //TODO get scale from m l xl etc
        mapRenderer = new MyOrthogonalTiledMapRenderer(gameModel.getMap(), 1f/540f, spriteBatch, shapeRenderer); //xl
    }

    public void render(float deltaTime) {
        viewport.apply();
        renderSky();
        renderMap();
        renderAnimation(deltaTime);
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
        spriteBatch.setProjectionMatrix(extendedCamera.getCamera().combined);
        spriteBatch.begin();

        SpriterAnimation animation = playerAnimation.getAnimation();
        MovingEntity.ActionState actionState = gameModel.getPlayer().getActionState();
        MovingEntity.MovingState movingState = gameModel.getPlayer().getMovingState();

        animation.unflip();
        //TODO move all animationchanging to playerAnimation from animation
        if(actionState == MovingEntity.ActionState.ATTACKING) {
            if(movingState == MovingEntity.MovingState.STANDING) {
                animation.setPlayOnce(AssetsConstants.PLAYER_ANIMATION_ATTACKING);
            }
            else if(movingState == MovingEntity.MovingState.LEFT){
                animation.flip();
                playerAnimation.attackWhileMoving();
            } else {
                playerAnimation.attackWhileMoving();
            }
        } else if(actionState == MovingEntity.ActionState.FALLING) {
            animation.setPlayAlways(AssetsConstants.PLAYER_ANIMATION_FALLING);
            if(movingState == MovingEntity.MovingState.LEFT) {
                animation.flip();
            }
        } else if(actionState == MovingEntity.ActionState.JUMPING) {
            animation.setPlayAlways(AssetsConstants.PLAYER_ANIMATION_JUMPING);
            if(movingState == MovingEntity.MovingState.LEFT) {
                animation.flip();
            }
        } else if(movingState == MovingEntity.MovingState.RIGHT) {
            animation.setPlayAlways(AssetsConstants.PLAYER_ANIMATION_WALKING);
        } else if (movingState == MovingEntity.MovingState.LEFT) {
            animation.setPlayAlways(AssetsConstants.PLAYER_ANIMATION_WALKING);
            animation.flip();
        } else {
            animation.setPlayAlways(AssetsConstants.PLAYER_ANIMATION_IDLE);
        }

        animation.setPosition(gameModel.getPlayer().getPosition().x, gameModel.getPlayer().getPosition().y);
        animation.update(deltaTime);
        animation.render(spriteBatch, shapeRenderer);

        spriteBatch.end();
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
}
