package com.lothbrok.game.renderers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.constants.Resolution;
import com.lothbrok.game.constants.UIConstants;

public class HUDRenderer implements Disposable {

    private Assets assets;

    private Stage stage;
    private Skin skin;

    private Table rootTable;

    private HorizontalGroup healthImages;
    private Image treasureImage;
    private Label treasureLabel;

    private int health;
    private int treasure;

    public HUDRenderer(SpriteBatch batch, int health, int treasure, Assets assets) {
        this.assets = assets;
        stage = new Stage(new ExtendViewport(Resolution.instance.getMenuWidth(), Resolution.instance.getMenuHeight()), batch);
        skin = assets.getUI();
        this.health = health;
        this.treasure = treasure;
        rebuildStage();
    }

    private void rebuildStage() {
        stage.clear();

        rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        HorizontalGroup trasureGroup = buildTreasure();
        rootTable.add(trasureGroup).expand().top().left().padLeft(Resolution.instance.getPaddingMedium()).padTop(Resolution.instance.getPaddingMedium());

        healthImages = buildHealth();
        rootTable.add(healthImages).expand().top().right().padRight(Resolution.instance.getPaddingMedium()).padTop(Resolution.instance.getPaddingMedium());
    }

    private HorizontalGroup buildHealth() {
        HorizontalGroup result = new HorizontalGroup();
        result.space(Resolution.instance.getPaddingSmall());
        for(int i = 0; i < health; i++) {
            Image healthImage = new Image();
            healthImage.setDrawable(skin.get(UIConstants.UI_HEALTH, Image.class).getDrawable());
            result.addActor(healthImage);
        }
        return result;
    }

    private HorizontalGroup buildTreasure() {
        HorizontalGroup result = new HorizontalGroup();
        result.space(Resolution.instance.getPaddingSmall());

        treasureImage = skin.get(UIConstants.UI_TREASURE, Image.class);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = assets.getPrVikingFont().getLargeFont();
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
            rootTable.add(healthImages).expand().top().right().padRight(Resolution.instance.getPaddingMedium()).padTop(Resolution.instance.getPaddingMedium());
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
