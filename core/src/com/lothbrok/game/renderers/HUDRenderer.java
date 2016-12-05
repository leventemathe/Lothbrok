package com.lothbrok.game.renderers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.screens.utils.ScreensConstants;

public class HUDRenderer implements Disposable {

    private Stage stage;
    private Skin skin;

    private Table rootTable;

    private HorizontalGroup healthImages;
    private Image treasureImage;
    private Label treasureLabel;

    private int health;
    private int treasure;

    public HUDRenderer(SpriteBatch batch, int health, int treasure) {
        stage = new Stage(new FitViewport(ScreensConstants.VIEWPORT_MENU_WIDTH,
                ScreensConstants.VIEWPORT_MENU_HEIGHT), batch);
        skin = Assets.instance.getUI();
        this.health = health;
        this.treasure = treasure;
        rebuildStage();
    }

    //TODO a padding erteket kiszervezni
    private void rebuildStage() {
        stage.clear();

        rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        HorizontalGroup trasureGroup = buildTreasure();
        rootTable.add(trasureGroup).expand().top().left().padLeft(50f).padTop(50f);

        healthImages = buildHealth();
        rootTable.add(healthImages).expand().top().right().padRight(50f).padTop(50f);
    }

    private HorizontalGroup buildHealth() {
        HorizontalGroup result = new HorizontalGroup();
        result.space(50f);
        for(int i = 0; i < health; i++) {
            Image healthImage = new Image();
            healthImage.setDrawable(skin.get("health", Image.class).getDrawable());
            result.addActor(healthImage);
        }
        return result;
    }

    private HorizontalGroup buildTreasure() {
        HorizontalGroup result = new HorizontalGroup();
        result.space(50f);

        treasureImage = skin.get("treasure", Image.class);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        //TODO fix the font system
        labelStyle.font = Assets.instance.getMainMenuAssets().getFont48();
        treasureLabel = new Label(Integer.toString(treasure), labelStyle);

        result.addActor(treasureImage);
        result.addActor(treasureLabel);
        return result;
    }

    public void updateTreasure(int treasure) {
        if(this.treasure != treasure) {
            this.treasure = treasure;
            treasureLabel.setText(Integer.toString(treasure));
        }
    }

    public void updateHealth(int health) {
        if(this.health != health) {
            this.health = health;
            rootTable.removeActor(healthImages);
            healthImages = buildHealth();
            rootTable.add(healthImages).expand().top().right().padRight(50f).padTop(50f);
        }
    }

    public void render(float deltaTime) {
        stage.act(deltaTime);
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public int getHealth() {
        return health;
    }

    public int getTreasure() {
        return treasure;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
