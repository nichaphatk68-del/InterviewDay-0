package com.example.interviewday.models;

import java.util.ArrayList;
import java.util.List;

/**
 * คลาสแม่: จัดเก็บข้อมูลพื้นฐานและสถานะของแต่ละด่าน
 * OOP: Abstraction + Encapsulation
 */
public abstract class Level {
    protected int levelID;
    protected String levelName;
    protected LevelStatus status;
    protected String introStory;
    protected String failStory;
    protected int storyBgResId;
    protected int gameBgResId;
    protected List<Item> requiredItems = new ArrayList<>();
    protected SecretItem secretItem;
    protected Time timer = new Time();

    public Level(int levelID, String levelName) {
        this.levelID = levelID;
        this.levelName = levelName;
        this.status = LevelStatus.LOCKED;
    }

    public abstract void loadContent();

    protected void setStory(String introStory, String failStory) {
        this.introStory = introStory;
        this.failStory = failStory;
    }

    public String getIntroStory() {
        return introStory;
    }

    public String getFailStory() {
        return failStory;
    }

    public int getStoryBgResId() {
        return storyBgResId;
    }

    public void setStoryBgResId(int storyBgResId) {
        this.storyBgResId = storyBgResId;
    }

    public int getGameBgResId() {
        return gameBgResId;
    }

    public void setGameBgResId(int gameBgResId) {
        this.gameBgResId = gameBgResId;
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

    public boolean isAllItemsFound() {
        for (Item item : requiredItems) {
            if (!item.isCollected()) {
                return false;
            }
        }
        return true;
    }

    public Time getTimer() {
        return timer;
    }

    public void triggerFail() {
        System.out.println(levelName + " : ผู้เล่นล้มเหลวในด่านนี้");
        System.out.println("เนื้อเรื่อง: " + failStory);
    }

    public boolean updateTime(int elapsedSeconds) {
        timer.decreaseTime(elapsedSeconds);
        if (timer.isTimeUp() && !isAllItemsFound()) {
            triggerFail();
            return true;
        }
        return false;
    }

    public void startLevel() {
        if (status == LevelStatus.LOCKED) {
            System.out.println(levelName + " ยังไม่ถูกปลดล็อก");
            return;
        }
        loadContent();
        timer.reset();
        timer.start();
        System.out.println("เริ่มด่าน: " + levelName
                + " (มีเวลา " + timer.getMinutes() + " นาที " + timer.getSeconds() + " วินาที)");
    }

    public void completeLevel() {
        timer.stop();
        this.status = LevelStatus.COMPLETED;
        System.out.println(levelName + " ผ่านเรียบร้อยแล้ว");
    }

    public void unlock() {
        this.status = LevelStatus.UNLOCKED;
    }

    public LevelStatus getStatus() {
        return status;
    }

    public void setStatus(LevelStatus status) {
        this.status = status;
    }

    public int getLevelID() {
        return levelID;
    }

    public String getLevelName() {
        return levelName;
    }
}
