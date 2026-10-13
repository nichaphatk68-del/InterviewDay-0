package com.example.interviewday.models;

public class Setting {

    // ===== Attributes =====
    private String language;
    private int musicVolume;
    private int soundVolume;
    private int voiceVolume;
    private boolean vibration;

    // ===== Constructor =====
    public Setting() {
        language = "English";
        musicVolume = 100;
        soundVolume = 100;
        voiceVolume = 100;
        vibration = true;
    }

    // ===== Language =====

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    // ===== Music =====

    public int getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(int musicVolume) {
        this.musicVolume = limitVolume(musicVolume);
    }

    // ===== Sound Effect =====

    public int getSoundVolume() {
        return soundVolume;
    }

    public void setSoundVolume(int soundVolume) {
        this.soundVolume = limitVolume(soundVolume);
    }

    // ===== Voice =====

    public int getVoiceVolume() {
        return voiceVolume;
    }

    public void setVoiceVolume(int voiceVolume) {
        this.voiceVolume = limitVolume(voiceVolume);
    }

    // ===== Vibration =====

    public boolean isVibrationEnabled() {
        return vibration;
    }

    public void setVibration(boolean vibration) {
        this.vibration = vibration;
    }

    public void toggleVibration() {
        vibration = !vibration;
    }

    // ===== Check Volume =====

    private int limitVolume(int volume) {

        if (volume < 0) {
            return 0;
        }

        if (volume > 100) {
            return 100;
        }

        return volume;
    }
}