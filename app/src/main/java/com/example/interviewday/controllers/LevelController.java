package com.example.interviewday.controllers;

import com.example.interviewday.models.Level;
import com.example.interviewday.models.Level1;
import com.example.interviewday.models.Level2;
import com.example.interviewday.models.Level3;

public class LevelController {
    // --Attributes--
    private int currentStage;
    private static int unlockedStage = 1; // เก็บสถานะการปลดล็อกด่านในหน่วยความจำ (จะรีเซ็ตเป็น 1 เมื่อปิดแอป)
    private Level currentLevelModel;

    // --Constructor--
    public LevelController() {
        this.currentStage = 1;
        loadLevelModel(1);
    }

    public LevelController(int stageNumber) {
        this.currentStage = stageNumber;
        loadLevelModel(stageNumber);
    }

    public void loadLevelModel(int stageNumber) {
        this.currentStage = stageNumber;
        switch (stageNumber) {
            case 2:
                this.currentLevelModel = new Level2();
                break;
            case 3:
                this.currentLevelModel = new Level3();
                break;
            case 1:
            default:
                this.currentLevelModel = new Level1();
                break;
        }
    }

    public Level getCurrentLevelModel() {
        if (currentLevelModel == null) {
            loadLevelModel(currentStage);
        }
        return currentLevelModel;
    }

    public void selectStage(int stage) {
        if (checkStageUnlock(stage)) {
            this.currentStage = stage;
            loadLevelModel(stage);
        }
    }

    public void startStage(int stage) {
        if (checkStageUnlock(stage)) {
            this.currentStage = stage;
            loadLevelModel(stage);
        }
    }

    public static void unlockNextStage() {
        if (unlockedStage < 3) {
            unlockedStage++;
        }
    }

    public static void unlockStage(int stage) {
        if (stage > unlockedStage && stage <= 3) {
            unlockedStage = stage;
        }
    }

    public static boolean checkStageUnlock(int stage) {
        return stage <= unlockedStage;
    }

    public int getCurrentStage() {
        return currentStage;
    }

    public static int getUnlockedStage() {
        return unlockedStage;
    }

    public static void setUnlockedStage(int stage) {
        unlockedStage = stage;
    }

    public static void resetProgress() {
        unlockedStage = 1;
    }
}
