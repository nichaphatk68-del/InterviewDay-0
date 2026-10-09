package com.example.interviewday.models;

import com.example.interviewday.R;

/**
 * คลาสลูก: จัดการฉากจบแบบล้มเหลว (Failed Ending)
 * OOP: Inheritance + Polymorphism
 */
public class FailedEnding extends Ending {

    public FailedEnding(int endingID) {
        super(endingID, "Failed Ending");
        this.bgResId = R.drawable.bg_ending_failed;
        this.dialogueBgResId = R.drawable.bg_dialogue_failed;
        this.description = "คุณใช้เวลาในการหาของและเตรียมตัวนานเกินไปจน เกินเวลาที่ทางบริษัทนัดไว้ทำให้คุณพลาดโอกาสในการฝึกงาน";
        this.logoResId = R.drawable.ic_logo_failed;
        this.isLogoLeft = false;
    }

    @Override
    public boolean checkCondition(int completedLevels, int completedQuests, int secretItemsCollected) {
        return false;
    }
}
