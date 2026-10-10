package com.example.interviewday.controllers;

import android.content.Context;
import android.media.MediaPlayer;

public class AudioController {
    // --Attributes--
    private float volume;//— ระดับความดังเสียง (ค่าระหว่าง 0.0f ถึง 1.0f)
    private boolean isMuted;// — สถานะเปิด/ปิดเสียง (true/false)
    private MediaPlayer mediaPlayer; // ตัวจัดการเล่นเสียง


    // --Constructor--
    public AudioController(){
        this.volume = 1.0f;// ตั้งค่าเสียงดังสุด 100% (1.0f)
        this.isMuted = false;
    }

    // --Methods--
    //1.playSentenceAudio() — เล่นเสียงอ่านประโยคภาษาอังกฤษ
    // เล่นเสียงอ่านประโยคเมื่อกดปุ่ม
    public void playSentenceAudio(Context context, int soundResId) {
        SettingsController settingsController = new SettingsController();
        if (!settingsController.isSfxOn() || settingsController.getSfxVolume() <= 0) return;
        if (isMuted) return;

        // หยุดเสียงเดิมที่กำลังเล่นอยู่ก่อนหน้า
        stopAudio();

        // สร้าง MediaPlayer ตัวใหม่และเริ่มเล่นเสียงทันที
        mediaPlayer = MediaPlayer.create(context, soundResId);
        if (mediaPlayer != null) {
            float vol = settingsController.getSfxVolume() / 100.0f;
            mediaPlayer.setVolume(vol, vol);
            mediaPlayer.start();

            // คืนหน่วยความจำเมื่อเล่นเสียงจบ
            mediaPlayer.setOnCompletionListener(mp -> stopAudio());
        }
    }
    // ฟังก์ชันสำหรับหยุดเล่นเสียง

    // ฟังก์ชันสำหรับหยุดเสียง
    public void stopAudio() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    //2.playCorrectSound() — เล่นเสียงเอฟเฟกต์ตอบถูก
    public void playCorrectSound(){
        SettingsController settingsController = new SettingsController();
        if (!settingsController.isSfxOn() || settingsController.getSfxVolume() <= 0) return;
        if (isMuted) return;
        // โค้ดสำหรับเล่นเสียงเอฟเฟกต์เมื่อตอบถูกต้อง
    }
    //3.playWrongSound() — เล่นเสียงเอฟเฟกต์ตอบผิด
    public void playWrongSound(){
        SettingsController settingsController = new SettingsController();
        if (!settingsController.isSfxOn() || settingsController.getSfxVolume() <= 0) return;
        if (isMuted) return;
        // โค้ดสำหรับเล่นเสียงเอฟเฟกต์เมื่อตอบผิด
    }
    //4.playBackgroundMusic() — เล่นเพลงประกอบฉาก
    public void playBackgroundMusic(){
        // Handled by BackgroundMusicManager
    }
    //5.setVolume(float volume) — ตั้งระดับความดังเสียง
    public void setVolume(float volume){
        this.volume = volume;
        if (volume <= 0.0f){
            this.isMuted = true;
        }else {
            this.isMuted = false;
        }
    }
    //6.mute() — สลับสถานะเปิด/ปิดเสียง
    public void mute(){
        this.isMuted = !this.isMuted;
    }

    // --Getters & Setters--
    public float getVolume(){return volume;}
    public boolean isMuted(){return isMuted;}

}
