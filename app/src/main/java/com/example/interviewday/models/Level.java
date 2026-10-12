package com.example.interviewday.models;

import java.util.ArrayList;
import java.util.List;

/*
 * คลาสแม่ (Abstract Class): Level
 * - [OOP Concept: Abstraction & Inheritance] คลาสแม่สำหรับจัดเก็บข้อมูลพื้นฐานและสถานะของแต่ละด่าน
 * - [OOP Concept: Encapsulation] ปกป้องฟิลด์ข้อมูลด้วย protected และเข้าถึงผ่าน Getters
 * - [MVC Pattern: Model] เก็บข้อมูลไอเทมและเรื่องราวของด่าน
 */
public abstract class Level {

    // ===== [OOP Concept: Encapsulation] Attributes =====
    protected int levelID;                                     // รหัสประจำด่าน (1, 2, 3)
    protected String levelName;                                // ชื่อประจำด่าน
    protected LevelStatus status;                              // สถานะของด่าน (LOCKED, UNLOCKED)
    protected String introStory;                               // ข้อความเกริ่นนำเรื่องราวของด่าน
    protected int storyBgResId;                                // Resource ID ของภาพพื้นหลังหน้า Story
    protected int gameBgResId;                                 // Resource ID ของภาพพื้นหลังหน้า Gameplay
    protected List<Item> requiredItems = new ArrayList<>();   // รายการ Normal Items ที่ต้องหาในด่าน
    protected SecretItem secretItem;                           // Secret Item ประจำด่าน

    // ===== [OOP Concept: Constructor] =====
    // คอนสตรัคเตอร์กำหนดรหัสด่านและชื่อด่าน พร้อมตั้งสถานะเริ่มต้นเป็น LOCKED
    public Level(int levelID, String levelName) {
        this.levelID = levelID;
        this.levelName = levelName;
        this.status = LevelStatus.LOCKED;
    }

    // ===== [OOP Concept: Abstraction & Polymorphism] =====
    // เมธอด Abstract สำหรับโหลดเนื้อหาเฉพาะของแต่ละด่านลูก
    public abstract void loadContent();

    // กำหนดข้อความเรื่องราวเกริ่นนำด่าน
    protected void setStory(String introStory) {
        this.introStory = introStory;
    }

    // ===== Getters =====
    public String getIntroStory() {
        return introStory;
    }

    public int getStoryBgResId() {
        return storyBgResId;
    }

    public int getGameBgResId() {
        return gameBgResId;
    }

    protected void addRequiredItem(Item item) {
        requiredItems.add(item);
    }

    public List<Item> getRequiredItems() {
        return requiredItems;
    }

    public SecretItem getSecretItem() {
        return secretItem;
    }

    public void setSecretItem(SecretItem secretItem) {
        this.secretItem = secretItem;
    }

    // ตรวจสอบว่าผู้เล่นเก็บ Normal Items ในด่านครบถ้วนแล้วหรือยัง
    public boolean isAllItemsFound() {
        for (Item item : requiredItems) {
            if (!item.isCollected()) {
                return false;
            }
        }
        return true;
    }

    // เริ่มต้นทำงานด่านและโหลดเนื้อหา
    public void startLevel() {
        if (status == LevelStatus.LOCKED) {
            System.out.println(levelName + " ยังไม่ถูกปลดล็อก");
            return;
        }
        loadContent();
        System.out.println("เริ่มด่าน: " + levelName);
    }
}
