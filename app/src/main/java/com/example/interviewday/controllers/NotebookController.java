package com.example.interviewday.controllers;

import com.example.interviewday.models.Notebook;

import java.util.ArrayList;
import java.util.List;

/**
 * คลาส NotebookController
 * หน้าที่: ควบคุมและจัดการข้อมูลสมุดโน้ต สรุปเนื้อหาคำศัพท์ และไอเท็มที่ค้นพบเพื่อส่งให้ NotebookActivity (View)
 * หมายเหตุ: ข้อมูลจะแสดงผลเฉพาะคำศัพท์และไอเท็มที่ผู้เล่นตอบคำถามผ่านหรือค้นพบแล้วเท่านั้น
 */
public class NotebookController {

    // เก็บรายการคำศัพท์และไอเท็มที่ปลดล็อกแล้วในหน่วยความจำชั่วคราว
    private static final List<String> unlockedVocabulary = new ArrayList<>();
    private static final List<String> unlockedDiscoveredItems = new ArrayList<>();

    private Notebook notebookModel; // Model เก็บข้อมูลสมุดโน้ต

    public NotebookController() {
        this.notebookModel = new Notebook();
        syncDataWithModel();
    }

    public NotebookController(Notebook notebookModel) {
        if (notebookModel == null) {
            this.notebookModel = new Notebook();
        } else {
            this.notebookModel = notebookModel;
        }
        syncDataWithModel();
    }

    // ซิงค์เฉพาะคำศัพท์และไอเท็มที่ปลดล็อกแล้วเข้าสู่ Notebook Model
    private void syncDataWithModel() {
        for (String vocab : unlockedVocabulary) {
            notebookModel.addVocabulary(vocab);
        }
        for (String item : unlockedDiscoveredItems) {
            notebookModel.addDiscoveredItem(item);
        }
    }

    // เมธอด static สำหรับบันทึกคำศัพท์ลงสมุดโน้ตเมื่อตอบคำถามถูกต้อง
    public static void addUnlockedVocabulary(String word) {
        if (word != null && !word.trim().isEmpty() && !unlockedVocabulary.contains(word)) {
            unlockedVocabulary.add(word);
        }
    }

    // เมธอด static สำหรับบันทึกไอเท็มที่ค้นพบลงสมุดโน้ต
    public static void addUnlockedDiscoveredItem(String item) {
        if (item != null && !item.trim().isEmpty()) {
            String itemText = item.startsWith("-") ? item : "- " + item;
            if (!unlockedDiscoveredItems.contains(itemText)) {
                unlockedDiscoveredItems.add(itemText);
            }
        }
    }

    // รีเซ็ตข้อมูลโน้ตทั้งหมด
    public static void resetNotebook() {
        unlockedVocabulary.clear();
        unlockedDiscoveredItems.clear();
    }

    // จัดฟอร์แมตข้อความสำหรับแสดงบนหน้ากระดาษฝั่งซ้าย (Vocabulary & Concepts)
    public String getLeftPageText() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== VOCABULARY & CONCEPTS ===\n\n");
        List<String> vocabList = notebookModel.getVocabulary();
        if (vocabList.isEmpty()) {
            sb.append("(No information yet – Answer the question regarding the secret item in the level to record the vocabulary.)\n");
        } else {
            for (String vocab : vocabList) {
                sb.append(vocab).append("\n\n");
            }
        }
        return sb.toString();
    }

    // จัดฟอร์แมตข้อความสำหรับแสดงบนหน้ากระดาษฝั่งขวา (Discovered Items)
    public String getRightPageText() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== DISCOVERED ITEMS ===\n\n");
        List<String> itemList = notebookModel.getDiscoveredItems();
        if (itemList.isEmpty()) {
            sb.append("(No information yet – find items in the level to record notes.)\n");
        } else {
            for (String item : itemList) {
                sb.append(item).append("\n\n");
            }
        }
        return sb.toString();
    }

    // ===== Getters & Setters =====
    public Notebook getNotebookModel() {
        return notebookModel;
    }

    public void setNotebookModel(Notebook notebookModel) {
        this.notebookModel = notebookModel;
    }

    public void addVocabulary(String word) {
        addUnlockedVocabulary(word);
        notebookModel.addVocabulary(word);
    }

    public void addDiscoveredItem(String item) {
        addUnlockedDiscoveredItem(item);
        notebookModel.addDiscoveredItem(item);
    }
}
