package com.lothbrok.game.renderers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.constants.Resolution;
import com.lothbrok.game.constants.UIConstants;
import com.lothbrok.game.screens.MainMenuScreen;

public class EndOfGameRenderer implements Disposable {

    protected Assets assets;

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;

    protected Stage stage;
    protected Skin skin;

    protected Table rootTable;

    protected TextButton btnMainMenu;

    public EndOfGameRenderer() {}

    public EndOfGameRenderer(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, Assets assets) {
        this.assets = assets;
        this.spriteBatch = spriteBatch;
        this.shapeRenderer = shapeRenderer;
        stage = new Stage(new ExtendViewport(Resolution.instance.getMenuWidth(),
                Resolution.instance.getMenuHeight()), spriteBatch);
        skin = assets.getUI();
        Gdx.input.setInputProcessor(stage);
        rebuildStage(shapeRenderer);
    }

    protected void rebuildStage(ShapeRenderer shapeRenderer) {
        stage.clear();
        rootTable = new Table();
        rootTable.setFillParent(true);

        stage.addActor(rootTable);
    }

    protected void buildBtnMainMenu() {
        TextButton.TextButtonStyle style = skin.get(UIConstants.UI_TEXT_BUTTON_STYLE, TextButton.TextButtonStyle.class);
        style.font = assets.getPrVikingFont().getLargeFont();
        style.fontColor = skin.get(UIConstants.UI_SKIN_COLOR_WHITE, Color.class);
        btnMainMenu = new TextButton(UIConstants.UI_BTN_MAIN_MENU, style);
        btnMainMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(assets));
            }
        });

        rootTable.add(btnMainMenu).expand().padTop(Resolution.instance.getPaddingSmall()).row();
    }

    public void render(float delta) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.setColor(0f, 0f, 0f, 0.5f);
        shapeRenderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(-stage.getViewport().getWorldWidth()/2f,
                -stage.getViewport().getWorldHeight()/2f,
                stage.getViewport().getWorldWidth(),
                stage.getViewport().getWorldHeight());
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        stage.act(delta);
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public Stage getStage() {
        return stage;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
