package com.lothbrok.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.assets.entities.animation.EnemyAnimation;
import com.lothbrok.game.audio.Audio;
import com.lothbrok.game.controllers.CameraController;
import com.lothbrok.game.controllers.EnemyController;
import com.lothbrok.game.controllers.PauseController;
import com.lothbrok.game.controllers.PauseListener;
import com.lothbrok.game.controllers.PlayerController;
import com.lothbrok.game.controllers.input.PCInput;
import com.lothbrok.game.model.GameModel;
import com.lothbrok.game.model.entities.ActionListener;
import com.lothbrok.game.model.entities.Enemy;
import com.lothbrok.game.model.entities.Entity;
import com.lothbrok.game.model.entities.Player;
import com.lothbrok.game.model.entities.Treasure;
import com.lothbrok.game.model.entities.components.AttackingComponent;
import com.lothbrok.game.model.entities.components.JumpingComponent;
import com.lothbrok.game.renderers.EndOfGameRenderer;
import com.lothbrok.game.renderers.ExtendedCamera;
import com.lothbrok.game.renderers.GameOverRenderer;
import com.lothbrok.game.renderers.GameRenderer;
import com.lothbrok.game.renderers.HUDRenderer;
import com.lothbrok.game.renderers.PauseRenderer;
import com.lothbrok.game.renderers.YouWonRenderer;

import java.util.Iterator;

public class GameScreen extends AbstractScreen {

    public static final String TAG = GameScreen.class.getSimpleName();
    public static boolean debugCamera = false;

    protected boolean isGameFinished = false;
    private float gameFinishedTimer = 0f;
    private float GAME_FINISHED_TIME = 3f;

    //M
    private GameModel gameModel;

    //V
    protected GameRenderer gameRenderer;
    protected HUDRenderer hudRenderer;
    //TODO polimorphism instead of if statements
    protected Box2DDebugRenderer box2DDebugRenderer;
    protected EndOfGameRenderer endOfGameRenderer;
    protected PauseRenderer pauseRenderer;
    protected Audio audio;

    //C
    protected InputProcessor inputProcessor;
    protected PlayerController playerController;
    private CameraController cameraController;
    private EnemyController enemyController;
    protected PauseController pauseController;


    public GameScreen(Assets assets) {
        super(assets);
    }

    @Override
    public void show() {
        super.show();
        setupModel();
        setupControllers();
        setUpRenderers();
        setupAnimationListeners();
        setupAudio();
        setInputProcessor();
        setupInputProcessing();
    }

    protected void setupModel() {
        TiledMap map = assets.getMap(1);
        gameModel = new GameModel(map);
    }

    protected void setupControllers() {
        playerController = new PlayerController();
        cameraController = new CameraController();
        pauseController = new PauseController();
        enemyController = new EnemyController();
    }

    protected void setUpRenderers() {
        gameRenderer = new GameRenderer(gameModel, spriteBatch, shapeRenderer, assets);
        hudRenderer = new HUDRenderer(spriteBatch, gameModel.getPlayer().getHealth(), gameModel.getPlayer().getTreasure(), assets);
        box2DDebugRenderer = new Box2DDebugRenderer();
        gameRenderer.getExtendedCamera().snapTo(gameModel.getPlayer().position);
        pauseRenderer = new PauseRenderer(spriteBatch, shapeRenderer, pauseController, assets);
    }

    protected void setupAnimationListeners() {
        gameRenderer.getPlayerAnimation().setStopAttackingListener(gameModel.getPlayer().getAttackingComponent());

        ObjectMap<Enemy, EnemyAnimation> enemyAnimations = gameRenderer.getEnemyAnimations();
        for(ObjectMap.Entry<Enemy, EnemyAnimation> entry : enemyAnimations) {
            entry.value.setStopAttackingListener(entry.key.getAttackingComponent());
        }
    }

    protected void setupAudio() {
        audio = new Audio(assets.getMusicAssets(), assets.getSoundAssets());
        audio.playGamePlayMusic();

        AttackingComponent playerAttackingComponent = gameModel.getPlayer().getAttackingComponent();
        JumpingComponent playerJumpingComponent = gameModel.getPlayer().getJumpingComponent();

        playerAttackingComponent.setStartAttackingListener(new ActionListener() {
            @Override
            public void listen() {
                audio.playSwing();
            }
        });
        playerAttackingComponent.setHitListener(new ActionListener() {
            @Override
            public void listen() {
                audio.playSlice();
            }
        });
        playerJumpingComponent.setJumpListener(new ActionListener() {
            @Override
            public void listen() {
                audio.playEhh();
            }
        });

        Array<Enemy> enemies = gameModel.getEnemies();
        for(int i = 0; i < enemies.size; i++) {
            AttackingComponent attackingComponent = enemies.get(i).getAttackingComponent();
            attackingComponent.setStartAttackingListener(new ActionListener() {
                @Override
                public void listen() {
                    audio.playSwing();
                }
            });
            attackingComponent.setHitListener(new ActionListener() {
                @Override
                public void listen() {
                    audio.playSlice();
                }
            });
        }
    }

    protected void setInputProcessor() {
        inputProcessor = new PCInput(playerController, cameraController, pauseController);
    }

    protected void setupInputProcessing() {
        Gdx.input.setInputProcessor(inputProcessor);
        pauseController.setPauseListener(new PauseListener() {
            @Override
            public void listen(boolean paused) {
                if(paused) {
                    Gdx.input.setInputProcessor(pauseRenderer.getInputMultiplexer());
                } else {
                    Gdx.input.setInputProcessor(inputProcessor);
                }
            }
        });
    }



    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(pauseController.isPaused()) {
            gameRenderer.renderWithoutUpdate(deltaTime);
            pauseRenderer.render(deltaTime);
        } else if(isGameFinished) {
            updateAfterGameFinished(deltaTime);
            renderAfterGameFinished(deltaTime);
        } else {
            updateRegular(deltaTime);
            renderRegular(deltaTime);
        }
        super.render(deltaTime);
    }



    public void updateRegular(float deltaTime) {
        gameModel.update(deltaTime, gameRenderer);

        cameraController.control(deltaTime, gameRenderer.getExtendedCamera());
        playerController.control(deltaTime, gameModel.getPlayer());
        enemyController.control(deltaTime, gameModel.getEnemies(), gameModel.getPlayer());

        if(!debugCamera) {
            updateCamera(deltaTime);
        }

        updateTreasure(deltaTime);

        if(gameModel.getPlayer() == null) {
            audio.playDeathMusic(deltaTime);
        } else {
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
            audio.playVictoryMusic(deltaTime);
        }
    }

    protected void updateTreasure(float deltaTime) {
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

    protected void updateCamera(float deltaTime) {
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
        camera.snapToY(targetPos.y);
    }

    protected void isGameFinished(float deltaTime) {
        if(gameModel.getPlayer() == null) {
            if(endOfGameRenderer == null) {
                endOfGameRenderer = new GameOverRenderer(spriteBatch, shapeRenderer, assets);
            }
            gameFinishedTimer += deltaTime;
            if(gameFinishedTimer >= GAME_FINISHED_TIME) {
                isGameFinished = true;
            }
        } else if(gameModel.getPlayer().isVictoryAchieved()) {
            if(endOfGameRenderer == null) {
                endOfGameRenderer = new YouWonRenderer(spriteBatch, shapeRenderer, assets);
            }
            isGameFinished = true;
        }
    }



    protected void renderRegular(float deltaTime) {
        gameRenderer.render(deltaTime);
        hudRenderer.render(deltaTime);
        box2DDebugRenderer.render(gameModel.getWorld(), gameRenderer.getExtendedCamera().getCamera().combined);
        renderSound();
    }

    protected void renderAfterGameFinished(float deltaTime) {
        gameRenderer.render(deltaTime);
        endOfGameRenderer.render(deltaTime);
    }

    protected void renderSound() {
        Player player = gameModel.getPlayer();
        if(player != null &&
                (player.movementState == Entity.MovementState.MOVING || player.movementState == Entity.MovementState.MIDMOVING) &&
                player.actionState != Entity.ActionState.FALLING &&
                player.actionState != Entity.ActionState.JUMPING &&
                !player.isVictoryAchieved()) {
            audio.playFootsteps();
        } else {
            audio.stopFootsteps();
        }
    }



    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        gameRenderer.resize(width, height);
        hudRenderer.resize(width, height);
        if(endOfGameRenderer != null) {
            endOfGameRenderer.resize(width, height);
        }
        pauseRenderer.resize(width, height);
    }

    @Override
    public void dispose() {
        gameRenderer.dispose();
        hudRenderer.dispose();
        box2DDebugRenderer.dispose();
        pauseRenderer.dispose();
        if(endOfGameRenderer != null) {
            endOfGameRenderer.dispose();
        }
        super.dispose();
    }

    @Override
    public void pause() {
        pauseController.setPaused(true);
    }
}
