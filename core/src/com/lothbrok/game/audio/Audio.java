package com.lothbrok.game.audio;

import com.badlogic.gdx.audio.Music;
import com.lothbrok.game.assets.entities.MusicAssets;

public class Audio {

    private MusicAssets musicAssets;

    public Audio(MusicAssets musicAssets) {
        this.musicAssets = musicAssets;
    }

    public void playGamePlay() {
        musicAssets.getGamePlay().setVolume(1f);
        musicAssets.getDeath().setVolume(0f);
        musicAssets.getVictory().setVolume(0f);

        musicAssets.getMainMenu().stop();
        musicAssets.getDeath().stop();
        musicAssets.getVictory().stop();

        musicAssets.getGamePlay().setLooping(true);
        musicAssets.getGamePlay().play();
    }

    public void playDeath(float deltaTime) {
        fadeIn(deltaTime, musicAssets.getDeath());
        fadeOut(deltaTime, musicAssets.getGamePlay());
        musicAssets.getDeath().play();
    }

    public void playVictory(float deltaTime) {
        fadeIn(deltaTime, musicAssets.getVictory());
        fadeOut(deltaTime, musicAssets.getGamePlay());
        musicAssets.getVictory().play();
    }

    private void fadeIn(float deltaTime, Music music) {
        music.play();
        if(music.getVolume() >= 1f) {
            return;
        } else {
            music.setVolume(musicAssets.getGamePlay().getVolume() + deltaTime);
        }
    }

    private void fadeOut(float deltaTime, Music music) {
        if(!music.isPlaying()) {
            return;
        }
        if(music.getVolume() <= 0f) {
            music.stop();
        } else {
            music.setVolume(musicAssets.getGamePlay().getVolume() - deltaTime);
        }
    }
}
