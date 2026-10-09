package com.example.interviewday.controllers;

import com.example.interviewday.models.Notebook;

import java.util.List;

/**
 * คลาส NotebookController
 * หน้าที่: ควบคุมและจัดการข้อมูลสมุดโน้ต สรุปเนื้อหาคำศัพท์ และไอเท็มที่ค้นพบเพื่อส่งให้ NotebookActivity (View)
 */
public class NotebookController {

    private Notebook notebookModel; // Model เก็บข้อมูลสมุดโน้ต

    public NotebookController() {
        this.notebookModel = new Notebook();
        loadInitialData();
    }

    public NotebookController(Notebook notebookModel) {
        if (notebookModel == null) {
            this.notebookModel = new Notebook();
            loadInitialData();
        } else {
            this.notebookModel = notebookModel;
        }
    }

    // โหลดข้อมูลเริ่มต้นลงใน Notebook Model
    private void loadInitialData() {
        if (notebookModel.getVocabulary().isEmpty()) {
            notebookModel.addVocabulary("1. Encapsulation - การซ่อนรายละเอียดภายใน");
            notebookModel.addVocabulary("2. Inheritance - การสืบทอดคุณสมบัติ");
            notebookModel.addVocabulary("3. Polymorphism - การมีได้หลายรูปแบบ");
            notebookModel.addVocabulary("4. Microcontroller - ชิปควบคุมอุปกรณ์");
            notebookModel.addVocabulary("5. Software Engineering - วิศวกรรมซอฟต์แวร์");
        }

        if (notebookModel.getDiscoveredItems().isEmpty()) {
            notebookModel.addDiscoveredItem("- โน้ตบุ๊กติดสติ๊กเกอร์รวมเทคโนโลยี (Dev Laptop)");
            notebookModel.addDiscoveredItem("- แฟลชไดร์ฟ USB (USB Flash Drive)");
            notebookModel.addDiscoveredItem("- เรซูเม่ (Resume)");
            notebookModel.addDiscoveredItem("- สาย LAN (Ethernet Cable)");
            notebookModel.addDiscoveredItem("- ปากกาไวท์บอร์ด (Whiteboard Marker)");
        }

        if (notebookModel.getStories().isEmpty()) {
            notebookModel.addStory("ภารกิจเตรียมสัมภาษณ์งานของเด็กปี 2");
            notebookModel.addStory("ค้นหาของสำคัญและตอบคำถามภาษาอังกฤษ");
        }
    }

    // จัดฟอร์แมตข้อความสำหรับแสดงบนหน้ากระดาษฝั่งซ้าย (Vocabulary & Concepts)
    public String getLeftPageText() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== VOCABULARY & CONCEPTS ===\n\n");
        List<String> vocabList = notebookModel.getVocabulary();
        for (String vocab : vocabList) {
            sb.append(vocab).append("\n");
        }
        return sb.toString();
    }

    // จัดฟอร์แมตข้อความสำหรับแสดงบนหน้ากระดาษฝั่งขวา (Discovered Items)
    public String getRightPageText() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== DISCOVERED ITEMS ===\n\n");
        List<String> itemList = notebookModel.getDiscoveredItems();
        for (String item : itemList) {
            sb.append(item).append("\n");
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
        notebookModel.addVocabulary(word);
    }

    public void addDiscoveredItem(String item) {
        notebookModel.addDiscoveredItem(item);
    }
}
