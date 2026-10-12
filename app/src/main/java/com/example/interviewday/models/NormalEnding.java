package com.example.interviewday.models;

import com.example.interviewday.R;

/**
 * คลาสลูก: จัดการฉากจบแบบธรรมดาเมื่อผู้เล่นผ่านเงื่อนไขทั่วไป
 * OOP: Inheritance + Polymorphism
 */
public class NormalEnding extends Ending {

    public NormalEnding(int endingID) {
        super(endingID, "Normal Ending");
        this.bgResId = R.drawable.bg_ending_normal;
        this.dialogueBgResId = R.drawable.bg_dialogue_box;
        this.description = "คุณนั่งรอผลการสัมภาษณ์งานอยู่ที่โต๊ะทำงานของตัวเอง และมี HR โทรมาบอกว่าผ่านการสัมภาษณ์ ทำให้คุณโล่งใจและดีใจมาก";
        this.logoResId = R.drawable.ic_logo_normal;
        this.isLogoLeft = true;
    }
}
