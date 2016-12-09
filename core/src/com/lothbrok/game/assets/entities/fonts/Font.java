package com.lothbrok.game.assets.entities.fonts;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Font {

    private FreeTypeFontGenerator generator;

    private BitmapFont font96;
    private BitmapFont font64;
    private BitmapFont font48;
    private BitmapFont font32;

    public Font(FileHandle fileHandle) {
        generator = new FreeTypeFontGenerator(fileHandle);
        generateFonts();
    }

    private void generateFonts() {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 96;
        font96 = generator.generateFont(parameter);
        parameter.size = 64;
        font64 = generator.generateFont(parameter);
        parameter.size = 48;
        font48 = generator.generateFont(parameter);
        parameter.size = 32;
        font32 = generator.generateFont(parameter);
    }

    public BitmapFont getFont96() {
        return font96;
    }

    public BitmapFont getFont64() {
        return font64;
    }

    public BitmapFont getFont48() {
        return font48;
    }

    public BitmapFont getFont32() {
        return font32;
    }
}
