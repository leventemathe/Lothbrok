package com.lothbrok.game.constants;

import com.lothbrok.game.screens.loadingscreens.EssentialsLoadingScreen;

public class Resolution {

    private float worldScale;

    private float menuWidth;
    private float menuHeight;

    private float paddingLarge;
    private float paddingMedium;
    private float paddingSmall;

    public static Resolution instance = new Resolution();

    private Resolution() {}

    public void init(EssentialsLoadingScreen.Size size) {
        if(size == EssentialsLoadingScreen.Size.XL) {
            worldScale = 1f/540f;
            paddingLarge = 50f;
            paddingMedium = 40f;
            paddingSmall = 30f;
        } else if(size == EssentialsLoadingScreen.Size.L) {
            worldScale = 1f/360f;
            paddingLarge = 33f;
            paddingMedium = 27f;
            paddingSmall = 20f;
        } else if(size == EssentialsLoadingScreen.Size.M) {
            worldScale = 1f/270f;
            paddingLarge = 25;
            paddingMedium = 20f;
            paddingSmall = 15f;
        } else {
            worldScale = 1f/180f;
            paddingLarge = 17f;
            paddingMedium = 14f;
            paddingSmall = 10f;
        }
        menuWidth = size.width;
        menuHeight = size.height;
    }

    public float getWorldScale() {
        return worldScale;
    }

    public float getMenuWidth() {
        return menuWidth;
    }

    public float getMenuHeight() {
        return menuHeight;
    }

    public float getPaddingLarge() {
        return paddingLarge;
    }

    public float getPaddingMedium() {
        return paddingMedium;
    }

    public float getPaddingSmall() {
        return paddingSmall;
    }
}
