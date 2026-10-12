package com.example.interviewday.controllers;

import android.content.Context;
import android.media.MediaPlayer;

public class AudioController {
    private MediaPlayer mediaPlayer;

    public AudioController() {}

    public void playSentenceAudio(Context context, int soundResId) {
        SettingsController settingsController = new SettingsController();
        if (!settingsController.isSfxOn() || settingsController.getSfxVolume() <= 0) return;

        stopAudio();

        mediaPlayer = MediaPlayer.create(context, soundResId);
        if (mediaPlayer != null) {
            float vol = settingsController.getSfxVolume() / 100.0f;
            mediaPlayer.setVolume(vol, vol);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(mp -> stopAudio());
        }
    }

    public void stopAudio() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
