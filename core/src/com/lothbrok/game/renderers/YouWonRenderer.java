package com.lothbrok.game.renderers;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.constants.UIConstants;

public class YouWonRenderer extends EndOfGameRenderer {

    private Image youWonLogo;

    public YouWonRenderer(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, Assets assets) {
        super(spriteBatch, shapeRenderer, assets);
        youWonLogo = skin.get(UIConstants.UI_YOU_WON_LOGO, Image.class);
        rootTable.add(youWonLogo).expand().center().padTop(50f).row();
        buildBtnMainMenu();
    }

    @Override
    protected void rebuildStage(ShapeRenderer shapeRenderer) {
        super.rebuildStage(shapeRenderer);
        rootTable.add(youWonLogo).expand().top().center().padTop(50f).row();
    }
}
