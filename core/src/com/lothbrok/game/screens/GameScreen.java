package com.lothbrok.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.lothbrok.game.controllers.Controller;
import com.lothbrok.game.controllers.commands.Command;
import com.lothbrok.game.controllers.input.GameInputProcessor;
import com.lothbrok.game.model.GameModel;
import com.lothbrok.game.model.entities.MovingEntity;
import com.lothbrok.game.model.entities.Player;
import com.lothbrok.game.renderers.GameRenderer;

public class GameScreen extends AbstractScreen {

    public static final String TAG = GameScreen.class.getSimpleName();

    //M
    private GameModel gameModel;

    //V
    private GameRenderer gameRenderer;

    //C
    private Controller<MovingEntity, Command<MovingEntity>> playerController;
    private GameInputProcessor gameInputProcessor;

    @Override
    public void show() {
        super.show();
        gameModel = new GameModel(new Player());

        playerController = new Controller<>(gameModel.getPlayer());
        gameInputProcessor = new GameInputProcessor(playerController);
        gameRenderer = new GameRenderer(gameModel, playerController);
        Gdx.input.setInputProcessor(gameInputProcessor);
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);

        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameRenderer.render(deltaTime);
        super.render(deltaTime);
    }

    public void update(float deltaTime) {
        gameModel.getPlayer().update(deltaTime);
        gameInputProcessor.handleInput();
        playerController.executeCommands(deltaTime);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        gameRenderer.resize(width, height);
    }

    @Override
    public void dispose() {
        gameRenderer.dispose();
        super.dispose();
    }
}
