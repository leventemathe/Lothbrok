package com.lothbrok.game.renderers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.assets.utils.AssetsConstants;
import com.lothbrok.game.controllers.PauseController;

public class PauseRenderer extends EndOfGameRenderer {

    private PauseController pauseController;

    private Image pausedLogo;
    private TextButton btnResume;

    private InputMultiplexer inputMultiplexer;

    public PauseRenderer(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, PauseController pauseController) {
        super(spriteBatch, shapeRenderer);
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(new PauseKeyProcessor());

        this.pauseController = pauseController;
        pausedLogo = skin.get(AssetsConstants.UI_PAUSED_LOGO, Image.class);
        rootTable.add(pausedLogo).expand().center().padTop(0f).row();
        buildBtnResume();
        buildBtnMainMenu();
    }

    private void buildBtnResume() {
        TextButton.TextButtonStyle style = skin.get(AssetsConstants.UI_TEXT_BUTTON_STYLE, TextButton.TextButtonStyle.class);
        style.font = Assets.instance.getMainMenuAssets().getFont48();
        style.fontColor = skin.get("white", Color.class);
        btnResume = new TextButton("RESUME", style);
        btnResume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pauseController.setPaused(false);
            }
        });

        rootTable.add(btnResume).expand().padTop(25f).row();
    }

    @Override
    protected void rebuildStage(ShapeRenderer shapeRenderer) {
        super.rebuildStage(shapeRenderer);
        rootTable.add(pausedLogo).expand().top().center().padTop(25).row();
    }

    public InputMultiplexer getInputMultiplexer() {
        return inputMultiplexer;
    }

    private class PauseKeyProcessor extends InputAdapter{

        @Override
        public boolean keyUp(int keycode) {
            if(keycode == Input.Keys.ESCAPE) {
                pauseController.switchPaused();
            }
            return true;
        }
    }
}
