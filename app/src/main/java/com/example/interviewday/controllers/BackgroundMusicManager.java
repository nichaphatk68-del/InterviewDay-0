package com.example.interviewday.controllers;

import android.content.Context;
import android.media.MediaPlayer;
import com.example.interviewday.R;

/**
 * คลาส BackgroundMusicManager
 * หน้าที่: จัดการการเล่นเพลงพื้นหลัง (BGM) bg_music.mp3 แบบต่อเนื่องทั่วทั้งแอป
 */
public class BackgroundMusicManager {
    private static MediaPlayer bgMediaPlayer;
    private static float currentVolume = 1.0f;
    private static boolean isBgmOn = true;

    public static synchronized void start(Context context) {
        if (context == null) return;
        if (bgMediaPlayer == null) {
            try {
                bgMediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.bg_music);
                if (bgMediaPlayer != null) {
                    bgMediaPlayer.setLooping(true);
                    bgMediaPlayer.setOnCompletionListener(mp -> {
                        mp.seekTo(0);
                        if (isBgmOn) {
                            mp.start();
                        }
                    });
                    updateVolumeInternal();
                    if (isBgmOn) {
                        bgMediaPlayer.start();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (!bgMediaPlayer.isPlaying() && isBgmOn) {
                bgMediaPlayer.start();
            }
        }
    }

    public static synchronized void setBgmState(boolean on, int volumePercent) {
        isBgmOn = on;
        currentVolume = on ? (Math.max(0, Math.min(100, volumePercent)) / 100.0f) : 0.0f;
        updateVolumeInternal();
        if (bgMediaPlayer != null) {
            if (on && !bgMediaPlayer.isPlaying()) {
                bgMediaPlayer.start();
            } else if (!on && bgMediaPlayer.isPlaying()) {
                bgMediaPlayer.pause();
            }
        }
    }

    private static void updateVolumeInternal() {
        if (bgMediaPlayer != null) {
            float vol = isBgmOn ? currentVolume : 0.0f;
            bgMediaPlayer.setVolume(vol, vol);
        }
    }

    public static synchronized void stop() {
        if (bgMediaPlayer != null) {
            try {
                if (bgMediaPlayer.isPlaying()) {
                    bgMediaPlayer.stop();
                }
                bgMediaPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
            bgMediaPlayer = null;
        }
    }

    public static boolean isBgmOn() {
        return isBgmOn;
    }
}
