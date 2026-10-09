package com.example.interviewday.controllers;

import com.example.interviewday.models.Ending;
import com.example.interviewday.models.FailedEnding;
import com.example.interviewday.models.NormalEnding;
import com.example.interviewday.models.TrueEnding;

public class EndingController {
    private Ending currentEnding;
    private String endingType;
    private int secretItemCount;
    private boolean sideQuestCompleted;

    public EndingController() {
        this.endingType = "NORMAL";
        this.secretItemCount = 0;
        this.sideQuestCompleted = false;
        loadEndingModel(endingType);
    }

    public EndingController(String endingType) {
        if (endingType == null) {
            endingType = "NORMAL";
        }
        this.endingType = endingType;
        loadEndingModel(endingType);
    }

    public void loadEndingModel(String endingType) {
        if (endingType == null) endingType = "NORMAL";
        this.endingType = endingType.toUpperCase();
        switch (this.endingType) {
            case "SPECIAL":
            case "TRUE":
            case "TRUE_ENDING":
                this.currentEnding = new TrueEnding(2);
                break;
            case "FAILED":
            case "FAILED_ENDING":
                this.currentEnding = new FailedEnding(3);
                break;
            case "NORMAL":
            case "NORMAL_ENDING":
            default:
                this.currentEnding = new NormalEnding(1);
                break;
        }
    }

    public Ending getCurrentEnding() {
        if (currentEnding == null) {
            loadEndingModel(endingType);
        }
        return currentEnding;
    }

    public void calculateEnding() {
        if (secretItemCount >= 3) {
            this.endingType = "SPECIAL";
        } else {
            this.endingType = "NORMAL";
        }
        loadEndingModel(this.endingType);
    }

    public boolean checkEndingCondition(int secretItems, boolean sideQuest) {
        this.secretItemCount = secretItems;
        this.sideQuestCompleted = sideQuest;
        calculateEnding();
        return "SPECIAL".equals(endingType);
    }

    public String getEndingType() {
        return endingType;
    }

    public int getSecretItemCount() {
        return secretItemCount;
    }

    public void setSecretItemCount(int secretItemCount) {
        this.secretItemCount = secretItemCount;
    }

    public boolean isSideQuestCompleted() {
        return sideQuestCompleted;
    }

    public void setSideQuestCompleted(boolean sideQuestCompleted) {
        this.sideQuestCompleted = sideQuestCompleted;
    }
}
