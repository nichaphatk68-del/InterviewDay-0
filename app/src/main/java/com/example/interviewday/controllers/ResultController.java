package com.example.interviewday.controllers;

import com.example.interviewday.R;

/*
 * คลาส ResultController
 * - [MVC Pattern: Controller] ทำหน้าที่ประมวลผลโลจิกสำหรับสรุปผลคะแนน ตราสัญลักษณ์ฉากจบ และฟอร์แมตเวลา
 */
public class ResultController {

    // ===== [OOP Concept: Constructor] =====
    // คอนสตรัคเตอร์สำหรับสร้าง Instance ของ ResultController
    public ResultController() {
    }

    // ===== [MVC Pattern: Business Logic] =====
    // คืนค่า Resource ID ของป้ายฉากจบ (Ending Badge) โดยตรวจสอบประเภทฉากจบ (SPECIAL หรือ NORMAL)
    public int getEndingBadgeResId(String endingType) {
        if ("SPECIAL".equalsIgnoreCase(endingType) || "TRUE".equalsIgnoreCase(endingType)) {
            return R.drawable.ic_special_ending_badge;
        } else {
            return R.drawable.ic_normal_ending_badge;
        }
    }

    // จัดฟอร์แมตข้อความแสดงเวลา หากไม่มีข้อมูลเวลา (null) ให้คืนค่า "00:00" เป็นค่าเริ่มต้น
    public String formatTime(String time) {
        return time != null ? time : "00:00";
    }
}
