package com.example.interviewday.controllers;

import com.example.interviewday.models.Setting;

/*
 * คลาส SettingsController
 * - [MVC Pattern: Controller] ตัวกลางควบคุมสภาวะการตั้งค่าเสียงเพลงประกอบ (BGM) และเสียงเอฟเฟกต์ (SFX)
 * - [OOP Concept: Encapsulation & Shared State] ใช้ Static Attributes สื่อสารค่าระหว่างสภาวะ
 */
public class SettingsController {

    // ===== [Shared State / Singleton Pattern] Attributes =====
    private static final Setting sharedSettingModel = new Setting();
    private static boolean sharedSfxOn = true;
    private static boolean sharedBgmOn = true;

    // ===== [OOP Concept: Encapsulation] Attributes =====
    private final Setting settingModel; // Model สำหรับเก็บค่าระดับเสียง
    private boolean isSfxOn;            // สถานะ เปิด/ปิด เสียง SFX
    private boolean isBgmOn;            // สถานะ เปิด/ปิด เพลง BGM

    // ===== [OOP Concept: Constructor] =====
    // คอนสตรัคเตอร์สำหรับกำหนดค่าเริ่มต้นโดยอ้างอิงจาก Shared State
    public SettingsController() {
        this.settingModel = sharedSettingModel;
        this.isSfxOn = sharedSfxOn;
        this.isBgmOn = sharedBgmOn;
    }

    // ===== [MVC Pattern: Business Logic] =====
    // บันทึกการตั้งค่าเสียง SFX และ BGM พร้อมอัปเดตระดับเสียงลงใน Setting Model
    public void saveSettings(boolean sfxOn, int sfxVol, boolean bgmOn, int bgmVol) {
        this.isSfxOn = sfxOn;
        this.isBgmOn = bgmOn;
        sharedSfxOn = sfxOn;
        sharedBgmOn = bgmOn;
        settingModel.setSoundVolume(sfxOn ? sfxVol : 0);
        settingModel.setMusicVolume(bgmOn ? bgmVol : 0);
    }

    // ===== [OOP Concept: Encapsulation - Getters & Setters] =====
    // ตรวจสอบสถานะเปิด/ปิด เสียง SFX
    public boolean isSfxOn() {
        return isSfxOn;
    }

    // กำหนดสถานะเปิด/ปิด เสียง SFX
    public void setSfxOn(boolean sfxOn) {
        this.isSfxOn = sfxOn;
        sharedSfxOn = sfxOn;
    }

    // ตรวจสอบสถานะเปิด/ปิด เพลง BGM
    public boolean isBgmOn() {
        return isBgmOn;
    }

    // กำหนดสถานะเปิด/ปิด เพลง BGM
    public void setBgmOn(boolean bgmOn) {
        this.isBgmOn = bgmOn;
        sharedBgmOn = bgmOn;
    }

    // ดึงระดับเสียง SFX จาก Setting Model
    public int getSfxVolume() {
        return settingModel.getSoundVolume();
    }

    // ดึงระดับเสียง BGM จาก Setting Model
    public int getBgmVolume() {
        return settingModel.getMusicVolume();
    }
}
