package com.lothbrok.game.audio;

import com.badlogic.gdx.audio.Music;
import com.lothbrok.game.assets.entities.audio.MusicAssets;
import com.lothbrok.game.assets.entities.audio.SoundAssets;

public class Audio {

    private MusicAssets musicAssets;
    private SoundAssets soundAssets;

    public Audio(MusicAssets musicAssets, SoundAssets soundAssets) {
        this.musicAssets = musicAssets;
        this.soundAssets = soundAssets;
        this.musicAssets.getFootsteps().setVolume(0.3f);
        this.musicAssets.getGamePlay().setVolume(0.8f);
        this.musicAssets.getMainMenu().setVolume(0.8f);
    }

    public void playMenuMusic() {
        musicAssets.getGamePlay().stop();
        musicAssets.getDeath().stop();
        musicAssets.getVictory().stop();

        musicAssets.getMainMenu().play();
        musicAssets.getMainMenu().setLooping(true);
        musicAssets.getMainMenu().setVolume(1f);
    }

    public void playGamePlayMusic() {
        musicAssets.getDeath().setVolume(0f);
        musicAssets.getVictory().setVolume(0f);

        musicAssets.getMainMenu().stop();
        musicAssets.getDeath().stop();
        musicAssets.getVictory().stop();

        musicAssets.getGamePlay().play();
        musicAssets.getGamePlay().setLooping(true);
        musicAssets.getGamePlay().setVolume(1f);
    }

    public void playDeathMusic(float deltaTime) {
        fadeIn(deltaTime, musicAssets.getDeath());
        fadeOut(deltaTime, musicAssets.getGamePlay());
        musicAssets.getDeath().play();
    }

    public void playVictoryMusic(float deltaTime) {
        fadeIn(deltaTime, musicAssets.getVictory());
        fadeOut(deltaTime, musicAssets.getGamePlay());
        musicAssets.getVictory().play();
    }

    private void fadeIn(float deltaTime, Music music) {
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
        if(music.getVolume() <= 0.1f) {
            music.stop();
        } else {
            music.setVolume(musicAssets.getGamePlay().getVolume() - deltaTime);
        }
    }

    public void playFootsteps() {
        musicAssets.getFootsteps().play();
        musicAssets.getFootsteps().setLooping(true);
    }

    public void stopFootsteps() {
        musicAssets.getFootsteps().stop();
    }

    public void playSwing() {
        soundAssets.getSwing().play();
    }

    public void playSlice() {
        soundAssets.getSlice().play();
    }

    public void playEhh() {
        soundAssets.getEhh().play();
    }
}
