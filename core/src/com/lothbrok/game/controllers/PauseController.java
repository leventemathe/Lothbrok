package com.lothbrok.game.controllers;

public class PauseController {

    private boolean paused = false;

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void switchPaused() {
        paused = !paused;
    }
}
