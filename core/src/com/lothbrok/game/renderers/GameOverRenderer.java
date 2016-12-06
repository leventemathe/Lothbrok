package com.lothbrok.game.renderers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class GameOverRenderer extends EndOfGameRenderer {

    private Image gameOverLogo;

    public GameOverRenderer(SpriteBatch spriteBatch) {
        super(spriteBatch);
        gameOverLogo = skin.get("gameOverLogo", Image.class);
        rootTable.add(gameOverLogo).expand().center().padTop(50f).row();
        buildBtnMainMenu();
    }

    @Override
    protected void rebuildStage() {
        super.rebuildStage();
        rootTable.add(gameOverLogo).expand().top().center().padTop(50f).row();
    }
}
