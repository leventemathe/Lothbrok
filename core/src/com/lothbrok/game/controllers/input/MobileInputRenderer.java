package com.lothbrok.game.controllers.input;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.lothbrok.game.screens.utils.ScreensConstants;

public class MobileInputRenderer implements Disposable{

    private static final String TAG = MobileInputRenderer.class.getSimpleName();

    private Stage stage;

    private Touchpad moveControls;
    private ImageButton jumpButton;
    private ImageButton attackButton;

    public MobileInputRenderer() {
        stage = new Stage(new FitViewport(ScreensConstants.VIEWPORT_MENU_WIDTH,
                                          ScreensConstants.VIEWPORT_MENU_HEIGHT));
        rebuildStage();
    }

    private void rebuildStage() {
        stage.clear();
        moveControls = new Touchpad(10f, new Touchpad.TouchpadStyle());
        jumpButton = new ImageButton(new ImageButton.ImageButtonStyle());
        attackButton = new ImageButton(new ImageButton.ImageButtonStyle());
        stage.addActor(moveControls);
        stage.addActor(jumpButton);
        stage.addActor(attackButton);
    }

    public void render(float deltaTime) {
        stage.act(deltaTime);
        stage.draw();
        //Gdx.app.debug(TAG, "drawing mobile input gui");
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
