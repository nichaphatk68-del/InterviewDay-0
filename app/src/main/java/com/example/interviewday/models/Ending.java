package com.example.interviewday.models;

/**
 * คลาสแม่: จัดเก็บข้อมูลและกำหนดรูปแบบของฉากจบเกม
 * OOP: Abstraction + Encapsulation
 */
public abstract class Ending {
    protected int endingID;
    protected String endingName;
    protected int bgResId;
    protected int dialogueBgResId;
    protected String description;
    protected int logoResId;
    protected boolean isLogoLeft;

    public Ending(int endingID, String endingName) {
        this.endingID = endingID;
        this.endingName = endingName;
    }

    public abstract boolean checkCondition(int completedLevels, int completedQuests, int secretItemsCollected);

    public void trigger(int completedLevels, int completedQuests, int secretItemsCollected) {
        if (checkCondition(completedLevels, completedQuests, secretItemsCollected)) {
            showCutscene();
        }
    }

    protected void showCutscene() {
        System.out.println("แสดงฉากจบ: " + endingName);
    }

    public int getBgResId() {
        return bgResId;
    }

    public void setBgResId(int bgResId) {
        this.bgResId = bgResId;
    }

    public int getDialogueBgResId() {
        return dialogueBgResId;
    }

    public void setDialogueBgResId(int dialogueBgResId) {
        this.dialogueBgResId = dialogueBgResId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLogoResId() {
        return logoResId;
    }

    public void setLogoResId(int logoResId) {
        this.logoResId = logoResId;
    }

    public boolean isLogoLeft() {
        return isLogoLeft;
    }

    public void setLogoLeft(boolean logoLeft) {
        isLogoLeft = logoLeft;
    }

    public int getEndingID() {
        return endingID;
    }

    public String getEndingName() {
        return endingName;
    }
}
