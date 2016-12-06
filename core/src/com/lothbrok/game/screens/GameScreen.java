package com.lothbrok.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.ObjectMap;
import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.assets.entities.EnemyAnimation;
import com.lothbrok.game.controllers.Controller;
import com.lothbrok.game.controllers.EnemyController;
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
import java.util.List;

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
    private Controller controller;
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

        controller = new Controller(gameRenderer.getExtendedCamera(), gameModel.getPlayer());
        mobileInputInterface = new MobileInputInterface(controller, spriteBatch);
        inputProcessor = new PCInput(controller);
        //inputProcessor = mobileInputInterface.getStage();
        Gdx.input.setInputProcessor(inputProcessor);
        enemyController = new EnemyController(gameModel.getEnemies(), gameModel.getPlayer());

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

    // TODO move bounding box setting to a controller?
    public void updateRegular(float deltaTime) {
        if(gameModel.getPlayer().lifeState != Entity.LifeState.DYING && gameModel.getPlayer().lifeState != Entity.LifeState.DEAD) {
            updatePlayerBoundingBox();
        }
        updateEnemiesBoundingBox();
        gameModel.update(deltaTime);
        controller.control(deltaTime);
        enemyController.control(deltaTime);
        if(!debugCamera) {
            updateCamera(deltaTime);
        }
        updateTreasure(deltaTime);
        hudRenderer.updateHealth(gameModel.getPlayer().getHealth());
        hudRenderer.updateTreasure(gameModel.getPlayer().getTreasure());
        isGameFinished(deltaTime);
    }

    public void updateAfterGameFinished(float deltaTime) {
        updateEnemiesBoundingBox();
        gameModel.update(deltaTime);
        enemyController.control(deltaTime);
        if(gameModel.getPlayer().isVictoryAchieved()) {
            gameModel.getPlayer().getMovementComponent().moveRight(deltaTime);
        }
    }

    private void updateTreasure(float deltaTime) {
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

    private void updatePlayerBoundingBox() {
        Rectangle body = gameRenderer.getPlayerAnimation().getBodyBoundingBox();
        Rectangle foot = gameRenderer.getPlayerAnimation().getFootSensor();
        Rectangle weapon = gameRenderer.getPlayerAnimation().getWeaponBoundingBox();
        gameModel.getPlayer().updateBoundingBoxes(body, foot, weapon);
    }

    private void updateEnemiesBoundingBox() {
        ObjectMap<Enemy, EnemyAnimation> anims = gameRenderer.getEnemyAnimations();
        List<Enemy> enemies = gameModel.getEnemies();
        for(int i = 0; i < enemies.size(); i++) {
            Rectangle body = anims.get(enemies.get(i)).getBodyBoundingBox();
            Rectangle foot = anims.get(enemies.get(i)).getFootSensor();
            Rectangle weapon = anims.get(enemies.get(i)).getWeaponBoundingBox();
            enemies.get(i).updateBoundingBox(body, foot, weapon);
        }
    }

    private void isGameFinished(float deltaTime) {
        if(gameModel.getPlayer().isGameOVerAchieved()) {
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

        gameRenderer.renderRectangle(gameModel.getPlayer().getBodyBox());
        gameRenderer.renderRectangle(gameModel.getPlayer().getFootSensor());
        gameRenderer.renderRectangle(gameModel.getPlayer().getWeaponBox());
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
