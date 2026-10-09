package com.example.interviewday.models;

/**
 * คลาสลูก: จัดการไอเทมลับที่ใช้ปลดล็อก True Ending
 * OOP: Inheritance + Encapsulation
 */
public class SecretItem extends Item {
    private String secretDetails;

    public SecretItem(int itemID, String itemName, String description, String secretDetails) {
        super(itemID, itemName, description);
        this.secretDetails = secretDetails;
    }

    public SecretItem(int itemID, String itemName, String description, String secretDetails, int colorResId, float biasX, float biasY) {
        super(itemID, itemName, description, 0, colorResId, biasX, biasY);
        this.secretDetails = secretDetails;
    }

    public String getSecretDetails() {
        return secretDetails;
    }

    public void setSecretDetails(String secretDetails) {
        this.secretDetails = secretDetails;
    }
}
