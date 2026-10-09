package com.example.interviewday.controllers;

import com.example.interviewday.models.Level;
import com.example.interviewday.models.Level1;
import com.example.interviewday.models.Level2;
import com.example.interviewday.models.Level3;

public class LevelController {
    // --Attributes--
    private int currentStage;
    private int unlockedStage;
    private Level currentLevelModel;

    // --Constructor--
    public LevelController() {
        this.currentStage = 1;
        this.unlockedStage = 1;
        loadLevelModel(1);
    }

    public LevelController(int stageNumber) {
        this.currentStage = stageNumber;
        this.unlockedStage = 1;
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

    public void unlockNextStage() {
        if (unlockedStage < 3) {
            unlockedStage++;
        }
    }

    public boolean checkStageUnlock(int stage) {
        return stage <= unlockedStage;
    }

    public int getCurrentStage() {
        return currentStage;
    }

    public int getUnlockedStage() {
        return unlockedStage;
    }

    public void setUnlockedStage(int unlockedStage) {
        this.unlockedStage = unlockedStage;
    }
}
