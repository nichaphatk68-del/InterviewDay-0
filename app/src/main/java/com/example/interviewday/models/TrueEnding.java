package com.example.interviewday.models;

import com.example.interviewday.R;

/**
 * คลาสลูก: จัดการฉากจบแบบสมบูรณ์ (True/Special Ending)
 * OOP: Inheritance + Polymorphism
 */
public class TrueEnding extends Ending {

    public TrueEnding(int endingID) {
        super(endingID, "True Ending");
        this.bgResId = R.drawable.bg_ending_special;
        this.dialogueBgResId = R.drawable.bg_dialogue_box;
        this.description = "ด้วยความที่คุณเตรียมตัวมาอย่างดีทำให้ หลังจากเดินออกมาจากห้องสัมภาษณ์ HR เดินมาหาคุณและบอกว่าได้รับเลือกให้ฝึกงานที่บริษัทนี้ ทำให้คุณดีใจอย่างมาก";
        this.logoResId = R.drawable.ic_logo_special;
        this.isLogoLeft = true;
    }

    @Override
    public boolean checkCondition(int completedLevels, int completedQuests, int secretItemsCollected) {
        return completedLevels >= 3 && secretItemsCollected >= 3;
    }
}
