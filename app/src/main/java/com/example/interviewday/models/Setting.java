package com.example.interviewday.models;

/*
 * คลาส Setting
 * - [MVC Pattern: Model] โมเดลเก็บข้อมูลการตั้งค่าระดับเสียงเพลงประกอบ (Music Volume) และเสียงเอฟเฟกต์ (Sound Volume)
 * - [OOP Concept: Encapsulation] ซ่อนฟิลด์ข้อมูลด้วย private และเข้าถึงผ่าน Getters/Setters
 */
public class Setting {

    // ===== [OOP Concept: Encapsulation] Attributes =====
    private int musicVolume; // ระดับเสียงเพลง BGM (0 - 100)
    private int soundVolume; // ระดับเสียงเอฟเฟกต์ SFX (0 - 100)

    // ===== [OOP Concept: Constructor] =====
    // คอนสตรัคเตอร์กำหนดค่าเริ่มต้นระดับเสียงเป็น 100
    public Setting() {
        this.musicVolume = 100;
        this.soundVolume = 100;
    }

    // ===== [OOP Concept: Encapsulation - Getters & Setters] =====
    // ดึงระดับเสียงเพลง BGM
    public int getMusicVolume() {
        return musicVolume;
    }

    // กำหนดระดับเสียงเพลง BGM โดยจำกัดช่วงให้อยู่ระหว่าง 0 ถึง 100
    public void setMusicVolume(int musicVolume) {
        this.musicVolume = limitVolume(musicVolume);
    }

    // ดึงระดับเสียงเอฟเฟกต์ SFX
    public int getSoundVolume() {
        return soundVolume;
    }

    // กำหนดระดับเสียงเอฟเฟกต์ SFX โดยจำกัดช่วงให้อยู่ระหว่าง 0 ถึง 100
    public void setSoundVolume(int soundVolume) {
        this.soundVolume = limitVolume(soundVolume);
    }

    // ===== Private Helper Method =====
    // เมธอดตรวจสอบและควบคุมระดับเสียงให้อยู่ในช่วง 0 - 100
    private int limitVolume(int volume) {
        return Math.max(0, Math.min(100, volume));
    }
}
