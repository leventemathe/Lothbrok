package com.lothbrok.game.renderers;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class YouWonRenderer extends EndOfGameRenderer {

    private Image youWonLogo;

    public YouWonRenderer(SpriteBatch spriteBatch) {
        super(spriteBatch);
        youWonLogo = skin.get("youWonLogo", Image.class);
        rootTable.add(youWonLogo).expand().center().padTop(50f).row();
        buildBtnMainMenu();
    }

    @Override
    protected void rebuildStage() {
        super.rebuildStage();
        rootTable.add(youWonLogo).expand().top().center().padTop(50f).row();
    }
}
