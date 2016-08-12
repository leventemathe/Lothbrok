package com.lothbrok.game.assets.entities;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

public class MainMenuAssets implements Disposable {

    Skin skin;
    BitmapFont font48;

    FreeTypeFontGenerator fontGenerator;

    public Skin getSkin() {
        return skin;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    public BitmapFont getFont48() {
        return font48;
    }

    public void setFont48(BitmapFont font48) {
        this.font48 = font48;
    }

    public FreeTypeFontGenerator getFontGenerator() {
        return fontGenerator;
    }

    public void setFontGenerator(FreeTypeFontGenerator fontGenerator) {
        this.fontGenerator = fontGenerator;
    }

    @Override
    public void dispose() {
        fontGenerator.dispose();
    }
}
