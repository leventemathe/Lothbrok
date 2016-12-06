package com.lothbrok.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.assets.utils.AssetsConstants;
import com.lothbrok.game.screens.utils.ScreensConstants;

public class GameOverScreen extends AbstractScreen {

    private Stage stage;
    private Skin skin;

    private Table rootTable;

    private Image gameOverLogo;
    private TextButton btnBackToMainMenu;

    @Override
    public void show() {
        super.show();
        stage = new Stage(new FitViewport(ScreensConstants.VIEWPORT_MENU_WIDTH,
                ScreensConstants.VIEWPORT_MENU_HEIGHT), spriteBatch);
        skin = Assets.instance.getUI();
        Gdx.input.setInputProcessor(stage);
        rebuildStage();
    }

    private void rebuildStage() {
        stage.clear();
        rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);


        gameOverLogo = skin.get(AssetsConstants.UI_GAME_OVER_LOGO, Image.class);

        TextButton.TextButtonStyle style = skin.get(AssetsConstants.UI_TEXT_BUTTON_STYLE, TextButton.TextButtonStyle.class);
        style.font = Assets.instance.getMainMenuAssets().getFont48();
        style.fontColor = skin.get("white", Color.class);
        btnBackToMainMenu = new TextButton("BACK TO MENU", style);
        btnBackToMainMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
            }
        });

        rootTable.add(gameOverLogo).expand().top().center().padTop(50f).row();
        rootTable.add(btnBackToMainMenu).expand().padTop(50f);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render(delta);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        super.resize(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        super.dispose();
    }
}
