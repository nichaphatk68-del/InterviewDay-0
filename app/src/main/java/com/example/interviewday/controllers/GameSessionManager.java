package com.example.interviewday.controllers;

/**
 * คลาส GameSessionManager
 * หน้าที่: จัดการบันทึกเวลาที่ใช้เล่นในแต่ละด่าน (Level 1, 2, 3) และคำนวณเวลารวม
 * พร้อมฟังก์ชันรีเซ็ตเวลาทุกครั้งที่เริ่มเล่นเกมใหม่จากด่าน 1
 */
public class GameSessionManager {
    private static int level1TimeSeconds = 0;
    private static int level2TimeSeconds = 0;
    private static int level3TimeSeconds = 0;

    public static void resetSession() {
        level1TimeSeconds = 0;
        level2TimeSeconds = 0;
        level3TimeSeconds = 0;
    }

    public static void recordStageTime(int stageNumber, int timeLimitSeconds, int remainingSeconds) {
        int elapsed = Math.max(0, timeLimitSeconds - remainingSeconds);
        if (stageNumber == 1) {
            level1TimeSeconds = elapsed;
        } else if (stageNumber == 2) {
            level2TimeSeconds = elapsed;
        } else if (stageNumber == 3) {
            level3TimeSeconds = elapsed;
        }
    }

    public static String getLevel1FormattedTime() {
        return formatSeconds(level1TimeSeconds);
    }

    public static String getLevel2FormattedTime() {
        return formatSeconds(level2TimeSeconds);
    }

    public static String getLevel3FormattedTime() {
        return formatSeconds(level3TimeSeconds);
    }

    public static String getTotalFormattedTime() {
        int total = level1TimeSeconds + level2TimeSeconds + level3TimeSeconds;
        return formatSeconds(total);
    }

    private static String formatSeconds(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
