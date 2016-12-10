package com.lothbrok.game.renderers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.lothbrok.game.assets.Assets;
import com.lothbrok.game.constants.Resolution;
import com.lothbrok.game.constants.UIConstants;

public class GameOverRenderer extends EndOfGameRenderer {

    private Image gameOverLogo;

    public GameOverRenderer(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, Assets assets) {
        super(spriteBatch, shapeRenderer, assets);
        gameOverLogo = skin.get(UIConstants.UI_GAME_OVER_LOGO, Image.class);
        rootTable.add(gameOverLogo).expand().center().padTop(Resolution.instance.getPaddingMedium()).row();
        buildBtnMainMenu();
    }
}
