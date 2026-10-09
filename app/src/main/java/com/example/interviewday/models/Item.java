package com.example.interviewday.models;

/**
 * คลาสแม่: จัดเก็บข้อมูลพื้นฐานของไอเทมภายในเกม
 * OOP: Abstraction + Encapsulation
 */
public abstract class Item {
    protected int itemID;
    protected String itemName;
    protected String description;
    protected int shadowResId;
    protected int colorResId;
    protected float biasX;
    protected float biasY;
    private boolean isCollected;

    public Item(int itemID, String itemName, String description) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.description = description;
        this.isCollected = false;
    }

    public Item(int itemID, String itemName, String description, int shadowResId, int colorResId, float biasX, float biasY) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.description = description;
        this.shadowResId = shadowResId;
        this.colorResId = colorResId;
        this.biasX = biasX;
        this.biasY = biasY;
        this.isCollected = false;
    }

    public void collect() {
        this.isCollected = true;
        System.out.println("เก็บไอเทม: " + itemName);
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        this.isCollected = collected;
    }

    public int getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public String getDescription() {
        return description;
    }

    public int getShadowResId() {
        return shadowResId;
    }

    public int getColorResId() {
        return colorResId;
    }

    public float getBiasX() {
        return biasX;
    }

    public float getBiasY() {
        return biasY;
    }

    public void setPosition(float biasX, float biasY) {
        this.biasX = biasX;
        this.biasY = biasY;
    }

    public void setResourceIds(int shadowResId, int colorResId) {
        this.shadowResId = shadowResId;
        this.colorResId = colorResId;
    }
}
