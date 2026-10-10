package com.example.interviewday.controllers;

import com.example.interviewday.models.Setting;

/**
 * คลาส SettingsController
 * หน้าที่: ควบคุมลอจิกการตั้งค่าเสียงเพลงประกอบ (BGM) และเสียงเอฟเฟกต์ (SFX) เชื่อมกับ Setting (Model)
 */
public class SettingsController {

    private static final Setting sharedSettingModel = new Setting();
    private static boolean sharedSfxOn = true;
    private static boolean sharedBgmOn = true;

    private Setting settingModel; // Model เก็บสถานะการตั้งค่า
    private boolean isSfxOn;      // สถานะเปิด/ปิด เสียง SFX
    private boolean isBgmOn;      // สถานะเปิด/ปิด เพลง BGM

    public SettingsController() {
        this.settingModel = sharedSettingModel;
        this.isSfxOn = sharedSfxOn;
        this.isBgmOn = sharedBgmOn;
    }

    public SettingsController(Setting settingModel) {
        if (settingModel != null) {
            this.settingModel = settingModel;
        } else {
            this.settingModel = sharedSettingModel;
        }
        this.isSfxOn = sharedSfxOn;
        this.isBgmOn = sharedBgmOn;
    }

    // บันทึกการตั้งค่าเสียงและอัปเดตลง Setting Model
    public void saveSettings(boolean sfxOn, int sfxVol, boolean bgmOn, int bgmVol) {
        this.isSfxOn = sfxOn;
        this.isBgmOn = bgmOn;
        sharedSfxOn = sfxOn;
        sharedBgmOn = bgmOn;
        if (settingModel != null) {
            settingModel.setSoundVolume(sfxOn ? sfxVol : 0);
            settingModel.setMusicVolume(bgmOn ? bgmVol : 0);
        }
    }

    // ===== Getters & Setters =====
    public Setting getSettingModel() {
        return settingModel;
    }

    public void setSettingModel(Setting settingModel) {
        if (settingModel != null) {
            this.settingModel = settingModel;
        }
    }

    public boolean isSfxOn() {
        return isSfxOn;
    }

    public void setSfxOn(boolean sfxOn) {
        this.isSfxOn = sfxOn;
        sharedSfxOn = sfxOn;
    }

    public boolean isBgmOn() {
        return isBgmOn;
    }

    public void setBgmOn(boolean bgmOn) {
        this.isBgmOn = bgmOn;
        sharedBgmOn = bgmOn;
    }

    public int getSfxVolume() {
        return settingModel != null ? settingModel.getSoundVolume() : 80;
    }

    public int getBgmVolume() {
        return settingModel != null ? settingModel.getMusicVolume() : 50;
    }
}
