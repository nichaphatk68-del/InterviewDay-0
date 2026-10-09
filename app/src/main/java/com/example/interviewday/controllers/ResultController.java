package com.example.interviewday.controllers;

import com.example.interviewday.R;

public class ResultController {
    // --Attributes--
    private int correctCount;
    private int totalQuestion;
    private String result;

    // --Constructor--
    public ResultController() {
        this.correctCount = 0;
        this.totalQuestion = 0;
        this.result = "PENDING";
    }

    public boolean checkPass() {
        if (totalQuestion == 0) return false;
        double scorePercent = ((double) correctCount / totalQuestion) * 100;
        return scorePercent >= 50.0;
    }

    public boolean checkFail() {
        return !checkPass();
    }

    public String checkResult(int correctCount, int totalQuestion) {
        this.correctCount = correctCount;
        this.totalQuestion = totalQuestion;

        if (checkPass()) {
            this.result = "PASS";
        } else {
            this.result = "FAIL";
        }
        return this.result;
    }

    public int getEndingBadgeResId(String endingType) {
        if ("SPECIAL".equalsIgnoreCase(endingType) || "TRUE".equalsIgnoreCase(endingType)) {
            return R.drawable.ic_special_ending_badge;
        } else {
            return R.drawable.ic_normal_ending_badge;
        }
    }

    public String formatTime(String time) {
        return time != null ? time : "00:00";
    }

    public String getResult() {
        return result;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(int correctCount) {
        this.correctCount = correctCount;
    }

    public int getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(int totalQuestion) {
        this.totalQuestion = totalQuestion;
    }
}
