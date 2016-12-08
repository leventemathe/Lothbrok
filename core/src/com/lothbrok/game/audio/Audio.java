package com.lothbrok.game.audio;

import com.badlogic.gdx.audio.Music;
import com.lothbrok.game.assets.entities.MusicAssets;
import com.lothbrok.game.assets.entities.SoundAssets;

public class Audio {

    private MusicAssets musicAssets;
    private SoundAssets soundAssets;

    private final long EXTREMAL_ID = -1;
    private long playerFootStepID = 0;

    public Audio(MusicAssets musicAssets, SoundAssets soundAssets) {
        this.musicAssets = musicAssets;
        this.soundAssets = soundAssets;
    }

    public void playGamePlay() {
        musicAssets.getDeath().setVolume(0f);
        musicAssets.getVictory().setVolume(0f);

        musicAssets.getMainMenu().stop();
        musicAssets.getDeath().stop();
        musicAssets.getVictory().stop();

        musicAssets.getGamePlay().play();
        musicAssets.getGamePlay().setLooping(true);
        musicAssets.getGamePlay().setVolume(1f);
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
        if(music.getVolume() <= 0.1f) {
            music.stop();
        } else {
            music.setVolume(musicAssets.getGamePlay().getVolume() - deltaTime);
        }
    }

    public void playFootsteps() {
        if(playerFootStepID != EXTREMAL_ID) {
            return;
        } else {
            playerFootStepID = soundAssets.getFootStep().loop();
        }
    }

    public void stopFootsteps() {
        if(playerFootStepID != EXTREMAL_ID) {
            soundAssets.getFootStep().stop(playerFootStepID);
            playerFootStepID = EXTREMAL_ID;
        }
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
