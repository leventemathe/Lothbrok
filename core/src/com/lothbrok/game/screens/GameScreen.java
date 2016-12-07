package com.lothbrok.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.ObjectMap;
import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.assets.entities.EnemyAnimation;
import com.lothbrok.game.controllers.PlayerController;
import com.lothbrok.game.controllers.EnemyController;
import com.lothbrok.game.controllers.CameraController;
import com.lothbrok.game.controllers.input.MobileInputInterface;
import com.lothbrok.game.controllers.input.PCInput;
import com.lothbrok.game.model.GameModel;
import com.lothbrok.game.model.entities.Enemy;
import com.lothbrok.game.model.entities.Entity;
import com.lothbrok.game.model.entities.Player;
import com.lothbrok.game.model.entities.Treasure;
import com.lothbrok.game.renderers.EndOfGameRenderer;
import com.lothbrok.game.renderers.ExtendedCamera;
import com.lothbrok.game.renderers.GameOverRenderer;
import com.lothbrok.game.renderers.GameRenderer;
import com.lothbrok.game.renderers.HUDRenderer;
import com.lothbrok.game.renderers.YouWonRenderer;

import java.util.Iterator;

public class GameScreen extends AbstractScreen {

    public static final String TAG = GameScreen.class.getSimpleName();
    public static boolean debugCamera = false;

    private boolean isGameFinished = false;
    private float gameFinishedTimer = 0f;
    private float GAME_FINISHED_TIME = 3f;

    //M
    private GameModel gameModel;

    //V
    private GameRenderer gameRenderer;
    private HUDRenderer hudRenderer;
    //TODO polimorphism instead of if statements
    private MobileInputInterface mobileInputInterface;
    private Box2DDebugRenderer box2DDebugRenderer;
    private EndOfGameRenderer endOfGameRenderer;

    //C
    private InputProcessor inputProcessor;
    private PlayerController playerController;
    private CameraController cameraController;
    private EnemyController enemyController;

    @Override
    public void show() {
        super.show();
        TiledMap map = Assets.instance.getMap(1);
        gameModel = new GameModel(map);

        gameRenderer = new GameRenderer(gameModel, spriteBatch, shapeRenderer);
        hudRenderer = new HUDRenderer(spriteBatch, gameModel.getPlayer().getHealth(), gameModel.getPlayer().getTreasure());
        box2DDebugRenderer = new Box2DDebugRenderer();
        gameRenderer.getExtendedCamera().snapTo(gameModel.getPlayer().position);

        playerController = new PlayerController();
        cameraController = new CameraController();
        mobileInputInterface = new MobileInputInterface(playerController, spriteBatch);
        inputProcessor = new PCInput(playerController, cameraController);
        //inputProcessor = mobileInputInterface.getStage();
        Gdx.input.setInputProcessor(inputProcessor);
        enemyController = new EnemyController();

        gameRenderer.getPlayerAnimation().setStopAttackingListener(gameModel.getPlayer().getAttackingComponent());
        ObjectMap<Enemy, EnemyAnimation> enemyAnimations = gameRenderer.getEnemyAnimations();
        for(ObjectMap.Entry<Enemy, EnemyAnimation> entry : enemyAnimations) {
            entry.value.setStopAttackingListener(entry.key.getAttackingComponent());
        }
    }

    @Override
    public void render(float deltaTime) {
        if(isGameFinished) {
            updateAfterGameFinished(deltaTime);
            renderAfterGameFinished(deltaTime);
        } else {
            updateRegular(deltaTime);
            renderRegular(deltaTime);
        }
        super.render(deltaTime);
    }

    // TODO move bounding box setting to a playerController?
    public void updateRegular(float deltaTime) {
        gameModel.update(deltaTime, gameRenderer);
        cameraController.control(deltaTime, gameRenderer.getExtendedCamera());
        playerController.control(deltaTime, gameModel.getPlayer());
        enemyController.control(deltaTime, gameModel.getEnemies(), gameModel.getPlayer());
        if(!debugCamera) {
            updateCamera(deltaTime);
        }
        updateTreasure(deltaTime);
        if(gameModel.getPlayer() != null) {
            hudRenderer.updateHealth(gameModel.getPlayer().getHealth());
            hudRenderer.updateTreasure(gameModel.getPlayer().getTreasure());
        }
        isGameFinished(deltaTime);
    }

    public void updateAfterGameFinished(float deltaTime) {
        gameModel.update(deltaTime, gameRenderer);
        enemyController.control(deltaTime, gameModel.getEnemies(), gameModel.getPlayer());
        if(gameModel.getPlayer() != null && gameModel.getPlayer().isVictoryAchieved()) {
            gameModel.getPlayer().getMovementComponent().moveRight(deltaTime);
        }
    }

    private void updateTreasure(float deltaTime) {
        if(gameModel.getPlayer() == null) {
            return;
        }
        int treasureAmount = gameModel.getPlayer().getTreasure();
        int prevTreasureAmount = hudRenderer.getTreasure();
        int count = prevTreasureAmount - treasureAmount;
        for(int i = 0; i < count; i++) {
            Treasure treasure = gameModel.spawnLostTreasure(gameRenderer.getPlayerAnimation().getTreasurePosition());
            gameRenderer.addLostTreasure(treasure);
        }

        Iterator<Treasure> it = gameModel.getTreasures().iterator();
        while (it.hasNext()) {
            Treasure treasure = it.next();
            treasure.update(deltaTime);
            if(treasure.isTimeUp()) {
                gameModel.getWorld().destroyBody(treasure.getBody());
                gameRenderer.removeLostTreasure(treasure);
                it.remove();
            }
        }
    }

    private void updateCamera(float deltaTime) {
        Player player = gameModel.getPlayer();
        if(player == null) {
            return;
        }
        ExtendedCamera camera = gameRenderer.getExtendedCamera();

        Vector2 targetPos = new Vector2();
        float offsetX = 0.7f;

        if(player.direction == Entity.Direction.RIGHT) {
            targetPos.x = player.position.x + offsetX;
        } else if(player.direction == Entity.Direction.LEFT){
            targetPos.x = player.position.x - offsetX;
        }
        targetPos.y = player.position.y;

        camera.moveToX(targetPos.x, deltaTime);
        //camera.snapToX(targetPos.x);
        camera.snapToY(targetPos.y);
    }

    private void isGameFinished(float deltaTime) {
        if(gameModel.getPlayer() == null) {
            if(endOfGameRenderer == null) {
                endOfGameRenderer = new GameOverRenderer(spriteBatch, shapeRenderer);
            }
            //isGameFinished = true;
            gameFinishedTimer += deltaTime;
            if(gameFinishedTimer >= GAME_FINISHED_TIME) {
                isGameFinished = true;
            }
        } else if(gameModel.getPlayer().isVictoryAchieved()) {
            if(endOfGameRenderer == null) {
                endOfGameRenderer = new YouWonRenderer(spriteBatch, shapeRenderer);
            }
            isGameFinished = true;
        }
    }

    private void renderRegular(float deltaTime) {
        gameRenderer.render(deltaTime);

        hudRenderer.render(deltaTime);
        mobileInputInterface.render(deltaTime);
        box2DDebugRenderer.render(gameModel.getWorld(), gameRenderer.getExtendedCamera().getCamera().combined);
    }

    private void renderAfterGameFinished(float deltaTime) {
        gameRenderer.render(deltaTime);
        endOfGameRenderer.render(deltaTime);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        gameRenderer.resize(width, height);
        hudRenderer.resize(width, height);
        mobileInputInterface.resize(width, height);
    }

    @Override
    public void dispose() {
        gameRenderer.dispose();
        mobileInputInterface.dispose();
        super.dispose();
    }
}
