package com.lothbrok.game.assets.entities.audio;

import com.badlogic.gdx.audio.Music;

public class MusicAssets {

    private Music mainMenu;
    private Music gamePlay;
    private Music death;
    private Music victory;

    public Music getMainMenu() {
        return mainMenu;
    }

    public void setMainMenu(Music mainMenu) {
        this.mainMenu = mainMenu;
    }

    public Music getGamePlay() {
        return gamePlay;
    }

    public void setGamePlay(Music gamePlay) {
        this.gamePlay = gamePlay;
    }

    public Music getDeath() {
        return death;
    }

    public void setDeath(Music death) {
        this.death = death;
    }

    public Music getVictory() {
        return victory;
    }

    public void setVictory(Music victory) {
        this.victory = victory;
    }
}
