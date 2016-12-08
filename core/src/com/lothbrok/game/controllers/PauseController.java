package com.lothbrok.game.controllers;

public class PauseController {

    private boolean paused = false;
    private PauseListener pauseListener;

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
        if(pauseListener != null) {
            pauseListener.listen(paused);
        }
    }

    public void switchPaused() {
        paused = !paused;
        if(pauseListener != null) {
            pauseListener.listen(paused);
        }
    }

    public void setPauseListener(PauseListener pauseListener) {
        this.pauseListener = pauseListener;
    }
}
