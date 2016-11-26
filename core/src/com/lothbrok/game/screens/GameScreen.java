package com.lothbrok.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.controllers.Controller;
import com.lothbrok.game.controllers.commands.Command;
import com.lothbrok.game.controllers.input.InputToControllerProcessor;
import com.lothbrok.game.controllers.input.MobileInputInterface;
import com.lothbrok.game.controllers.input.PCInput;
import com.lothbrok.game.model.GameModel;
import com.lothbrok.game.model.entities.MovingEntity;
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

    //C
    private Controller<MovingEntity, Command<MovingEntity>> playerController;
    private Controller<ExtendedCamera, Command<ExtendedCamera>> cameraController;
    private InputProcessor inputProcessor;
    private InputToControllerProcessor inputToControllerProcessor;

    @Override
    public void show() {
        super.show();
        TiledMap map = Assets.instance.getMap(1);
        Player player = new Player((TiledMapTileLayer)map.getLayers().get("tiles"));
        gameModel = new GameModel(player, map);

        playerController = new Controller<>(gameModel.getPlayer());
        gameRenderer = new GameRenderer(gameModel, playerController);
        cameraController = new Controller<>(gameRenderer.getExtendedCamera());

        inputToControllerProcessor = new InputToControllerProcessor(playerController, cameraController);

        mobileInputInterface = new MobileInputInterface(inputToControllerProcessor);

        inputProcessor = new PCInput(inputToControllerProcessor);
        //inputProcessor = mobileInputInterface.getStage();
        Gdx.input.setInputProcessor(inputProcessor);
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);

        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameRenderer.render(deltaTime);
        mobileInputInterface.render(deltaTime);
        super.render(deltaTime);
    }

    public void update(float deltaTime) {
        gameModel.getPlayer().update(deltaTime);
        inputToControllerProcessor.handleInput();
        playerController.executeCommands(deltaTime);
        cameraController.executeCommands(deltaTime);
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
