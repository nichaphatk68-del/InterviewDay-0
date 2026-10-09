package com.example.interviewday.models;

/**
 * คลาสลูก: จัดการไอเทมทั่วไปที่ผู้เล่นสามารถค้นหาและเก็บได้
 * OOP: Inheritance (สืบทอดจาก Item)
 */
public class NormalItem extends Item {

    public NormalItem(int itemID, String itemName, String description) {
        super(itemID, itemName, description);
    }

    public NormalItem(int itemID, String itemName, String description, int shadowResId, int colorResId, float biasX, float biasY) {
        super(itemID, itemName, description, shadowResId, colorResId, biasX, biasY);
    }
}
