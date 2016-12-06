package com.lothbrok.game.renderers;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.lothbrok.game.assets.utils.AssetsConstants;

public class YouWonRenderer extends EndOfGameRenderer {

    private Image youWonLogo;

    public YouWonRenderer(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        super(spriteBatch, shapeRenderer);
        youWonLogo = skin.get(AssetsConstants.UI_YOU_WON_LOGO, Image.class);
        rootTable.add(youWonLogo).expand().center().padTop(50f).row();
        buildBtnMainMenu();
    }

    @Override
    protected void rebuildStage(ShapeRenderer shapeRenderer) {
        super.rebuildStage(shapeRenderer);
        rootTable.add(youWonLogo).expand().top().center().padTop(50f).row();
    }
}
