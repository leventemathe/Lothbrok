package com.lothbrok.game.renderers;

import com.badlogic.gdx.Gdx;
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
import com.lothbrok.game.model.GameModel;
import com.lothbrok.game.model.entities.AbstractMovingEntity;

public class GameRenderer implements Disposable {

    private GameModel gameModel;

    private OrthographicCamera camera;
    private Viewport viewport;

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private OrthogonalTiledMapRenderer mapRenderer;

    //TODO move to logic?
    private TiledMap tiledMap;
    private PlayerAnimation playerAnimation;

    public GameRenderer(GameModel gameModel) {
        this.gameModel = gameModel;

        setupViewPort();
        setupEntities();
        setupRendering();
    }

    private void setupViewPort() {
        camera = new OrthographicCamera();
        //TODO custom viewport (or maybe extended), now it sets it to the size of the desktop launcher/android screen size
        float aspect = (float) Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight();
        float height = 4;
        float width = height * aspect;
        viewport = new ExtendViewport(width, height, camera);
    }

    //TODO move to logic?
    private void setupEntities() {
        tiledMap = Assets.instance.getMap(1);
        playerAnimation = Assets.instance.getPlayerAnimation();
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
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    private void renderAnimation(float deltaTime) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        SpriterAnimation animation = playerAnimation.getAnimation();
        AbstractMovingEntity.ActionState actionState = gameModel.getPlayer().getActionState();
        AbstractMovingEntity.MovingState movingState = gameModel.getPlayer().getMovingState();

        if(actionState == AbstractMovingEntity.ActionState.JUMPING) {
            animation.setPlayOnce(AssetsConstants.PLAYER_ANIMATION_JUMPING);
        }

        if(movingState == AbstractMovingEntity.MovingState.WALKINGRIGHT) {
            animation.setPlayAlways(AssetsConstants.PLAYER_ANIMATION_WALKING);
        } else if (movingState == AbstractMovingEntity.MovingState.WALKINGLEFT) {
            animation.setPlayAlways(AssetsConstants.PLAYER_ANIMATION_WALKING);
            //TODO flip?
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
}
