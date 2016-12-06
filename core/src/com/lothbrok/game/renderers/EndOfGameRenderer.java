package com.lothbrok.game.renderers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.assets.utils.AssetsConstants;
import com.lothbrok.game.screens.MainMenuScreen;
import com.lothbrok.game.screens.utils.ScreensConstants;

public class EndOfGameRenderer implements Disposable {

    protected Stage stage;
    protected Skin skin;

    protected Table rootTable;

    protected TextButton btnMainMenu;

    public EndOfGameRenderer(SpriteBatch spriteBatch) {
        stage = new Stage(new FitViewport(ScreensConstants.VIEWPORT_MENU_WIDTH,
                ScreensConstants.VIEWPORT_MENU_HEIGHT), spriteBatch);
        skin = Assets.instance.getUI();
        Gdx.input.setInputProcessor(stage);
        rebuildStage();
    }

    protected void rebuildStage() {
        stage.clear();
        rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);
    }

    protected void buildBtnMainMenu() {
        TextButton.TextButtonStyle style = skin.get(AssetsConstants.UI_TEXT_BUTTON_STYLE, TextButton.TextButtonStyle.class);
        style.font = Assets.instance.getMainMenuAssets().getFont48();
        style.fontColor = skin.get("white", Color.class);
        btnMainMenu = new TextButton("MAIN MENU", style);
        btnMainMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
            }
        });

        rootTable.add(btnMainMenu).expand().padTop(50f);
    }

    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
