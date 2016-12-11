package com.lothbrok.game.screens;

import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.controllers.input.MobileInputInterface;

public class MobileGameScreen extends GameScreen {

    private MobileInputInterface mobileInputInterface;

    public MobileGameScreen(Assets assets) {
        super(assets);
    }

    @Override
    protected void setInputProcessor() {
        mobileInputInterface = new MobileInputInterface(playerController, pauseController, spriteBatch, assets);
        inputProcessor = mobileInputInterface.getStage();
    }

    @Override
    public void render(float deltaTime) {
        super.render(deltaTime);
        if(!isGameFinished && !pauseController.isPaused()) {
            mobileInputInterface.render(deltaTime);
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        mobileInputInterface.resize(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
        mobileInputInterface.dispose();
    }
}
