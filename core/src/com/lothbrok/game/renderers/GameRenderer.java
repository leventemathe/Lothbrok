package com.lothbrok.game.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.assets.animation.spriter.SpriterAnimation;
import com.lothbrok.game.assets.entities.PlayerAnimation;
import com.lothbrok.game.assets.utils.AssetsConstants;
import com.lothbrok.game.controllers.Controller;
import com.lothbrok.game.controllers.commands.Command;
import com.lothbrok.game.controllers.commands.movingentity.StopAttacking;
import com.lothbrok.game.model.GameModel;
import com.lothbrok.game.model.entities.MovingEntity;

public class GameRenderer implements Disposable {

    private GameModel gameModel;

    private ExtendedCamera extendedCamera;
    private Viewport viewport;

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private OrthogonalTiledMapRenderer mapRenderer;

    //TODO move to logic?
    private TiledMap tiledMap;
    private PlayerAnimation playerAnimation;

    //TODO don't pass controller
    public GameRenderer(GameModel gameModel, Controller<MovingEntity, Command<MovingEntity>> controller) {
        this.gameModel = gameModel;

        setupViewPort();
        setupEntities(controller);
        setupRendering();
    }

    private void setupViewPort() {
        extendedCamera = new ExtendedCamera(new OrthographicCamera());
        //TODO custom viewport (or maybe extended), now it sets it to the size of the desktop launcher/android screen size
        float aspect = (float) Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight();
        float height = 4;
        float width = height * aspect;
        viewport = new ExtendViewport(width, height, extendedCamera.getCamera());
    }

    //TODO move to logic?
    private void setupEntities(Controller<MovingEntity, Command<MovingEntity>> controller) {
        tiledMap = Assets.instance.getMap(1);
        playerAnimation = Assets.instance.getPlayerAnimation();
        //TODO definitly move this to screen
        playerAnimation.getAnimation().setController(controller);
        playerAnimation.getAnimation().addCommand(AssetsConstants.PLAYER_ANIMATION_ATTACKING,
                new StopAttacking());
        //TODO get scale from m l xl etc
        playerAnimation.getAnimation().setScale(1/270f); //l
    }

    private void setupRendering() {
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        //TODO get scale from m l xl etc
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/540f); //xl
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

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
        mapRenderer.dispose();
    }

    public ExtendedCamera getExtendedCamera() {
        return extendedCamera;
    }
}
