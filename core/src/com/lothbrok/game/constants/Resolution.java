package com.lothbrok.game.constants;

import com.lothbrok.game.screens.loadingscreens.EssentialsLoadingScreen;

public class Resolution {

    private float worldScale;

    private float menuWidth;
    private float menuHeight;

    public static Resolution instance = new Resolution();

    private Resolution() {}

    public void init(EssentialsLoadingScreen.Size size) {
        if(size == EssentialsLoadingScreen.Size.XL) {
            worldScale = 1f/540f;
        } else if(size == EssentialsLoadingScreen.Size.L) {
            worldScale = 1f/360f;
        } else if(size == EssentialsLoadingScreen.Size.M) {
            worldScale = 1f/270f;
        } else {
            worldScale = 1f/180f;
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
}
