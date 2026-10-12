package com.example.interviewday.controllers;

import com.example.interviewday.models.Ending;
import com.example.interviewday.models.FailedEnding;
import com.example.interviewday.models.NormalEnding;
import com.example.interviewday.models.TrueEnding;

public class EndingController {
    private Ending currentEnding;
    private String endingType;

    public EndingController(String endingType) {
        if (endingType == null) {
            endingType = "NORMAL";
        }
        this.endingType = endingType;
        loadEndingModel(this.endingType);
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

    public String getEndingType() {
        return endingType;
    }
}
