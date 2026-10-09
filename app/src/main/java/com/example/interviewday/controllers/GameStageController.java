package com.example.interviewday.controllers;

import com.example.interviewday.models.Item;
import com.example.interviewday.models.Level;
import com.example.interviewday.models.Level1;
import com.example.interviewday.models.Level2;
import com.example.interviewday.models.Level3;
import com.example.interviewday.models.SecretItem;

import java.util.List;

public class GameStageController {
    // --Attributes--
    private int currentStage;
    private Level currentLevel;
    private TimerController timerController;
    private String gameState;
    private boolean isPlaying;

    // --Constructor--
    public GameStageController() {
        this(1);
    }

    public GameStageController(int stageNumber) {
        this.currentStage = stageNumber;
        this.gameState = "IDLE";
        this.isPlaying = false;
        this.timerController = new TimerController();
        loadLevel(stageNumber);
    }

    public void loadLevel(int stageNumber) {
        this.currentStage = stageNumber;
        switch (stageNumber) {
            case 2:
                this.currentLevel = new Level2();
                break;
            case 3:
                this.currentLevel = new Level3();
                break;
            case 1:
            default:
                this.currentLevel = new Level1();
                break;
        }
    }

    public void startStage() {
        this.gameState = "PLAYING";
        this.isPlaying = true;
        if (currentLevel != null) {
            currentLevel.startLevel();
        }
        if (timerController != null) {
            timerController.startTimer();
        }
    }

    public void pauseStage() {
        this.gameState = "PAUSED";
        this.isPlaying = false;
        if (timerController != null) {
            timerController.pauseTimer();
        }
    }

    public void resumeStage() {
        this.gameState = "PLAYING";
        this.isPlaying = true;
        if (timerController != null) {
            timerController.resumeTimer();
        }
    }

    public void stopStage() {
        this.gameState = "STOPPED";
        this.isPlaying = false;
        if (timerController != null) {
            timerController.stopTimer();
        }
    }

    // จัดการเก็บไอเทมปกติ
    public boolean collectNormalItem(int itemIndex) {
        if (currentLevel == null) return false;
        List<Item> items = currentLevel.getRequiredItems();
        if (itemIndex >= 0 && itemIndex < items.size()) {
            Item item = items.get(itemIndex);
            item.collect();
        }
        return currentLevel.isAllItemsFound();
    }

    // จัดการเก็บไอเทมลับ
    public String collectSecretItem() {
        if (currentLevel == null) return "";
        SecretItem secret = currentLevel.getSecretItem();
        if (secret != null) {
            secret.collect();
            return secret.getItemName();
        }
        return "";
    }

    public boolean isAllItemsFound() {
        return currentLevel != null && currentLevel.isAllItemsFound();
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public List<Item> getNormalItems() {
        return currentLevel != null ? currentLevel.getRequiredItems() : null;
    }

    public SecretItem getSecretItem() {
        return currentLevel != null ? currentLevel.getSecretItem() : null;
    }

    public int getBackgroundResId() {
        return currentLevel != null ? currentLevel.getGameBgResId() : 0;
    }

    public TimerController getTimerController() {
        return timerController;
    }

    public int getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(int currentStage) {
        this.currentStage = currentStage;
    }

    public String getGameState() {
        return gameState;
    }

    public boolean isPlaying() {
        return isPlaying;
    }
}
