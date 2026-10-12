package com.example.interviewday.controllers;

import com.example.interviewday.models.Item;
import com.example.interviewday.models.Level;
import com.example.interviewday.models.Level1;
import com.example.interviewday.models.Level2;
import com.example.interviewday.models.Level3;
import com.example.interviewday.models.SecretItem;

import java.util.List;

/*
 * คลาส GameStageController
 * - [MVC Pattern: Controller] ทำหน้าที่เป็น Controller หลักในการควบคุมสภาวะและเนื้อหาของแต่ละด่าน (Stage 1, 2, 3)
 * - [OOP Concept: Encapsulation & Composition] ควบคุม Level (Model) และ TimerController (Sub-controller)
 * - [Polymorphism] โหลดวัตถุด่านลูก (Level1, Level2, Level3) เข้าสู่ตัวแปรอ้างอิงคลาสแม่ (Level)
 */
public class GameStageController {

    // ===== [OOP Concept: Encapsulation] Attributes =====
    private int currentStage;                   // หมายเลขด่านปัจจุบัน (1, 2, 3)
    private Level currentLevel;                 // วัตถุด่านปัจจุบัน (Level1, Level2, หรือ Level3)
    private final TimerController timerController;   // ตัวจัดการเวลาประจำด่าน

    // ===== [OOP Concept: Constructor] =====
    // คอนสตรัคเตอร์เริ่มต้นด่านตามหมายเลขด่านที่ระบุ และสร้าง TimerController
    public GameStageController(int stageNumber) {
        this.currentStage = stageNumber;
        this.timerController = new TimerController();
        loadLevel(stageNumber);
    }

    // ===== [OOP Concept: Polymorphism] =====
    // โหลดอินสแตนซ์ของด่านตามหมายเลข stageNumber (Factory Method Logic)
    public void loadLevel(int stageNumber) {
        this.currentStage = stageNumber;
        switch (stageNumber) {
            case 2:
                this.currentLevel = new Level2();
                break;
            case 3:
                this.currentLevel = new Level3();
                break;
            case 1:
            default:
                this.currentLevel = new Level1();
                break;
        }
    }

    // ===== [Stage Lifecycle & Timer Control Methods] =====
    // เริ่มด่านเกมและเปิดใช้งานตัวจับเวลา
    public void startStage() {
        if (currentLevel != null) {
            currentLevel.startLevel();
        }
        if (timerController != null) {
            timerController.startTimer();
        }
    }

    // หยุดเวลาด่านเกมชั่วคราว (Pause)
    public void pauseStage() {
        if (timerController != null) {
            timerController.pauseTimer();
        }
    }

    // เล่นเวลาด่านเกมต่อ (Resume)
    public void resumeStage() {
        if (timerController != null) {
            timerController.resumeTimer();
        }
    }

    // หยุดระบบด่านเกมอย่างสมบูรณ์
    public void stopStage() {
        if (timerController != null) {
            timerController.stopTimer();
        }
    }

    // ===== [MVC Pattern: Item Collection Business Logic] =====
    // จัดการเก็บไอเทมปกติ และคืนค่า true หากเก็บไอเทมปกติครบทั้งหมดในด่านแล้ว
    public boolean collectNormalItem(int itemIndex) {
        if (currentLevel == null) return false;
        List<Item> items = currentLevel.getRequiredItems();
        if (itemIndex >= 0 && itemIndex < items.size()) {
            Item item = items.get(itemIndex);
            item.collect();
        }
        return currentLevel.isAllItemsFound();
    }

    // จัดการเก็บไอเทมลับประจำด่าน และคืนค่าชื่อไอเทมลับที่เก็บได้
    public String collectSecretItem() {
        if (currentLevel == null) return "";
        SecretItem secret = currentLevel.getSecretItem();
        if (secret != null) {
            secret.collect();
            return secret.getItemName();
        }
        return "";
    }

    // ===== [OOP Concept: Encapsulation - Getters] =====
    // ดึงรายการไอเทมปกติทั้งหมดของด่านปัจจุบัน
    public List<Item> getNormalItems() {
        return currentLevel != null ? currentLevel.getRequiredItems() : null;
    }

    // ดึงวัตถุไอเทมลับประจำด่านปัจจุบัน
    public SecretItem getSecretItem() {
        return currentLevel != null ? currentLevel.getSecretItem() : null;
    }

    // ดึง Resource ID ภาพพื้นหลังของด่านปัจจุบัน
    public int getBackgroundResId() {
        return currentLevel != null ? currentLevel.getGameBgResId() : 0;
    }

    // ดึงวัตถุ TimerController
    public TimerController getTimerController() {
        return timerController;
    }

    // ดึงหมายเลขด่านปัจจุบัน
    public int getCurrentStage() {
        return currentStage;
    }
}
