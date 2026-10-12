package com.example.interviewday.controllers;

import android.content.Context;
import android.media.MediaPlayer;
import com.example.interviewday.R;

/*
 * คลาส BackgroundMusicManager
 * - [OOP Concept: Encapsulation & Singleton Pattern] จัดการการเล่นเพลงพื้นหลัง (BGM) แบบส่วนกลาง
 * - [MVC Pattern: Controller] ตัวกลางควบคุมสภาวะการเล่นเพลง วนลูป (Looping) และการปรับระดับเสียงเพลง
 * - [Resource Management] ควบคุม Instance เดียวของ MediaPlayer ตลอดทั้งแอปพลิเคชัน
 */
public class BackgroundMusicManager {

    // ===== [OOP Concept: Encapsulation - Static Attributes] =====
    private static MediaPlayer bgMediaPlayer; // วัตถุ MediaPlayer สำหรับเล่นไฟล์เพลงพื้นหลัง
    private static float currentVolume = 1.0f;  // ระดับเสียงปัจจุบัน (ช่วง 0.0f ถึง 1.0f)
    private static boolean isBgmOn = true;      // สถานะเปิด/ปิด เพลง BGM

    // ===== [Core Audio Methods] =====
    // เริ่มต้นเล่นเพลงพื้นหลัง (bg_music.mp3) แบบซิงโครไนซ์ (Thread-safe)
    public static synchronized void start(Context context) {
        if (context == null) return;
        if (bgMediaPlayer == null) {
            try {
                // สร้าง MediaPlayer จากไฟล์เสียง bg_music ใน raw resource
                bgMediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.bg_music);
                if (bgMediaPlayer != null) {
                    bgMediaPlayer.setLooping(true); // เล่นวนลูปต่อเนื่อง
                    bgMediaPlayer.setOnCompletionListener(mp -> {
                        mp.seekTo(0);
                        if (isBgmOn) {
                            mp.start();
                        }
                    });
                    updateVolumeInternal(); // อัปเดตระดับเสียงตามตั้งค่า
                    if (isBgmOn) {
                        bgMediaPlayer.start(); // เริ่มเล่นเพลงหากสถานะ BGM เปิดอยู่
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // หากมี Instance อยู่แล้วและเพลงไม่ได้เล่น ให้เริ่มเล่นต่อ
            if (!bgMediaPlayer.isPlaying() && isBgmOn) {
                bgMediaPlayer.start();
            }
        }
    }

    // กำหนดสถานะเปิด/ปิด BGM และปรับระดับเสียงแบบเปอร์เซ็นต์ (0 - 100)
    public static synchronized void setBgmState(boolean on, int volumePercent) {
        isBgmOn = on;
        currentVolume = on ? (Math.max(0, Math.min(100, volumePercent)) / 100.0f) : 0.0f;
        updateVolumeInternal();
        if (bgMediaPlayer != null) {
            if (on && !bgMediaPlayer.isPlaying()) {
                bgMediaPlayer.start(); // เล่นเพลงเมื่อสั่งเปิด
            } else if (!on && bgMediaPlayer.isPlaying()) {
                bgMediaPlayer.pause(); // หยุดชั่วคราวเมื่อสั่งปิด
            }
        }
    }

    // ===== Private Helper Method =====
    // อัปเดตระดับเสียงลงในตัวเล่น MediaPlayer โดยอ้างอิงสถานะเปิด/ปิด BGM
    private static void updateVolumeInternal() {
        if (bgMediaPlayer != null) {
            float vol = isBgmOn ? currentVolume : 0.0f;
            bgMediaPlayer.setVolume(vol, vol);
        }
    }
}
