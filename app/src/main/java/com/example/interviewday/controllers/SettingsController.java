package com.example.interviewday.controllers;

import com.example.interviewday.models.Setting;

/**
 * คลาส SettingsController
 * หน้าที่: ควบคุมลอจิกการตั้งค่าเสียงเพลงประกอบ (BGM) และเสียงเอฟเฟกต์ (SFX) เชื่อมกับ Setting (Model)
 */
public class SettingsController {

    private Setting settingModel; // Model เก็บสถานะการตั้งค่า
    private boolean isSfxOn;      // สถานะเปิด/ปิด เสียง SFX
    private boolean isBgmOn;      // สถานะเปิด/ปิด เพลง BGM

    public SettingsController() {
        this.settingModel = new Setting();
        this.isSfxOn = true;
        this.isBgmOn = true;
    }

    public SettingsController(Setting settingModel) {
        if (settingModel == null) {
            this.settingModel = new Setting();
        } else {
            this.settingModel = settingModel;
        }
        this.isSfxOn = true;
        this.isBgmOn = true;
    }

    // บันทึกการตั้งค่าเสียงและอัปเดตลง Setting Model
    public void saveSettings(boolean sfxOn, int sfxVol, boolean bgmOn, int bgmVol) {
        this.isSfxOn = sfxOn;
        this.isBgmOn = bgmOn;
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
        this.settingModel = settingModel;
    }

    public boolean isSfxOn() {
        return isSfxOn;
    }

    public void setSfxOn(boolean sfxOn) {
        isSfxOn = sfxOn;
    }

    public boolean isBgmOn() {
        return isBgmOn;
    }

    public void setBgmOn(boolean bgmOn) {
        isBgmOn = bgmOn;
    }

    public int getSfxVolume() {
        return settingModel != null ? settingModel.getSoundVolume() : 100;
    }

    public int getBgmVolume() {
        return settingModel != null ? settingModel.getMusicVolume() : 100;
    }
}
