package com.example.interviewday.controllers;

import android.os.CountDownTimer;
import java.util.Locale;

/*
 * คลาส TimerController
 * - [MVC Pattern: Controller] จัดการโลจิกการจับถอยหลังเวลา (CountDownTimer) ของแต่ละด่านในเกม
 * - [Event-driven / Listener] ใช้ Callback Interface สื่อสารกับ View (GameStageActivity)
 */
public class TimerController {

    // ===== [OOP Concept: Encapsulation] Attributes =====
    private final int timeLimit;                    // เวลาจำกัดทั้งหมดของด่าน (วินาที)
    private int remainingTime;                       // เวลาที่เหลืออยู่ปัจจุบัน (วินาที)
    private boolean isRunning;                       // สถานะว่าตัวจับเวลากำลังทำงานอยู่หรือไม่
    private CountDownTimer countDownTimer;           // วัตถุ CountDownTimer ของ Android
    private OnTimerTickListener onTimerTickListener; // Listener สำหรับรับ Callback

    // ===== Interface Listener =====
    // Callback Interface สำหรับส่งเหตุการณ์เวลาเปลี่ยน (onTick) และหมดเวลา (onFinish) ไปยัง View
    public interface OnTimerTickListener {
        void onTick(String formattedTime);
        void onFinish();
    }

    // ===== [OOP Concept: Constructor] =====
    // คอนสตรัคเตอร์กำหนดค่าเริ่มต้นเวลาจำกัด 180 วินาที (3 นาที)
    public TimerController() {
        this.timeLimit = 180;
        this.remainingTime = 180;
        this.isRunning = false;
    }

    // กำหนด Listener สำหรับรับ Callback เหตุการณ์จากตัวจับเวลา
    public void setOnTimerTickListener(OnTimerTickListener listener) {
        this.onTimerTickListener = listener;
    }

    // ===== [Timer Control Methods] =====
    // เริ่มต้นนับถอยหลังเวลา
    public void startTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(remainingTime * 1000L, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime = (int) (millisUntilFinished / 1000);
                if (onTimerTickListener != null) {
                    onTimerTickListener.onTick(getFormattedTime());
                }
            }

            @Override
            public void onFinish() {
                remainingTime = 0;
                isRunning = false;
                if (onTimerTickListener != null) {
                    onTimerTickListener.onFinish();
                }
            }
        };

        countDownTimer.start();
        isRunning = true;
    }

    // หยุดจับเวลาชั่วคราว (Pause)
    public void pauseTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            isRunning = false;
        }
    }

    // ทำงานต่อจากเวลาที่เหลืออยู่ (Resume)
    public void resumeTimer() {
        if (!isRunning && remainingTime > 0) {
            startTimer();
        }
    }

    // ยกเลิกและหยุดการจับเวลาอย่างสมบูรณ์ (Stop)
    public void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            isRunning = false;
        }
    }

    // ===== [OOP Concept: Encapsulation - Getters] =====
    // ดึงเวลาจำกัดทั้งหมดของด่าน
    public int getTimeLimit() {
        return timeLimit;
    }

    // ดึงเวลาที่เหลืออยู่ปัจจุบัน
    public int getRemainingTime() {
        return remainingTime;
    }

    // แปลงเวลาที่เหลืออยู่เป็นข้อความฟอร์แมต "MM:SS" (เช่น "02:30")
    public String getFormattedTime() {
        int minutes = remainingTime / 60;
        int seconds = remainingTime % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }
}
