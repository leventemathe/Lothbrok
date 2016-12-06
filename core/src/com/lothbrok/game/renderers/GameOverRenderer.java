package com.lothbrok.game.renderers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.lothbrok.game.assets.utils.AssetsConstants;

public class GameOverRenderer extends EndOfGameRenderer {

    private Image gameOverLogo;

    public GameOverRenderer(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        super(spriteBatch, shapeRenderer);
        gameOverLogo = skin.get(AssetsConstants.UI_GAME_OVER_LOGO, Image.class);
        rootTable.add(gameOverLogo).expand().center().padTop(50f).row();
        buildBtnMainMenu();
    }

    @Override
    protected void rebuildStage(ShapeRenderer shapeRenderer) {
        super.rebuildStage(shapeRenderer);
        rootTable.add(gameOverLogo).expand().top().center().padTop(50f).row();
    }
}
