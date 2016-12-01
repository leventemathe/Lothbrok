package com.lothbrok.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.controllers.Controller;
import com.lothbrok.game.controllers.input.MobileInputInterface;
import com.lothbrok.game.controllers.input.PCInput;
import com.lothbrok.game.model.GameModel;
import com.lothbrok.game.model.entities.Player;
import com.lothbrok.game.renderers.ExtendedCamera;
import com.lothbrok.game.renderers.GameRenderer;

public class GameScreen extends AbstractScreen {

    public static final String TAG = GameScreen.class.getSimpleName();

    //M
    private GameModel gameModel;

    //V
    private GameRenderer gameRenderer;
    //TODO polimorphism instead of if statements
    private MobileInputInterface mobileInputInterface;
    private Box2DDebugRenderer box2DDebugRenderer;

    //C
    private InputProcessor inputProcessor;
    private Controller controller;

    @Override
    public void show() {
        super.show();
        TiledMap map = Assets.instance.getMap(1);
        gameModel = new GameModel(map);

        gameRenderer = new GameRenderer(gameModel, spriteBatch, shapeRenderer);
        box2DDebugRenderer = new Box2DDebugRenderer();
        gameRenderer.getExtendedCamera().snapTo(gameModel.getPlayer().getPosition());

        controller = new Controller(gameRenderer.getExtendedCamera(), gameModel.getPlayer());
        mobileInputInterface = new MobileInputInterface(controller, spriteBatch);
        inputProcessor = new PCInput(controller);
        //inputProcessor = mobileInputInterface.getStage();
        Gdx.input.setInputProcessor(inputProcessor);
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);

        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameRenderer.render(deltaTime);
        gameRenderer.renderRectangle(gameModel.getPlayer().getBoundingBox());
        gameRenderer.renderRectangle(gameModel.getPlayer().getFootSensor());
        mobileInputInterface.render(deltaTime);
        box2DDebugRenderer.render(gameModel.getWorld(), gameRenderer.getExtendedCamera().getCamera().combined);
        super.render(deltaTime);
    }

    public void update(float deltaTime) {
        updatePlayerBoundingBox(deltaTime);
        gameModel.update(deltaTime);
        controller.control(deltaTime);
        updateCamera(deltaTime);
    }

    private void updateCamera(float deltaTime) {
        Player player = gameModel.getPlayer();
        ExtendedCamera camera = gameRenderer.getExtendedCamera();

        Vector2 targetPos = new Vector2();
        float offsetX = 0.7f;

        if(!gameRenderer.getPlayerAnimation().getAnimation().isFlipped()) {
            targetPos.x = player.getPosition().x + offsetX;
        } else {
            targetPos.x = player.getPosition().x - offsetX;
        }
        targetPos.y = player.getPosition().y;

        camera.moveToX(targetPos.x, deltaTime);
        //camera.snapToX(targetPos.x);
        camera.snapToY(targetPos.y);
    }

    private void updatePlayerBoundingBox(float deltaTime) {
        Rectangle body = gameRenderer.getPlayerAnimation().getBodyBoudningBox();
        Rectangle foot = gameRenderer.getPlayerAnimation().getFootSensor();
        gameModel.getPlayer().updateBoundingBox(body, foot, deltaTime);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        gameRenderer.resize(width, height);
        mobileInputInterface.resize(width, height);
    }

    @Override
    public void dispose() {
        gameRenderer.dispose();
        mobileInputInterface.dispose();
        super.dispose();
    }
}
