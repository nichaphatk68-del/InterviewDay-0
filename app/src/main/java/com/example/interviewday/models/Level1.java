package com.example.interviewday.models;

import com.example.interviewday.R;

/**
 * คลาสลูก: จัดการรายละเอียดและเนื้อหาของด่านที่ 1
 * OOP: Inheritance (สืบทอดจาก Level) + Polymorphism
 */
public class Level1 extends Level {

    private String vocabularyTopic;

    public Level1() {
        super(1, "ห้องนอนสุดรก");
        this.vocabularyTopic = "OOP Concepts";
        this.storyBgResId = R.drawable.bg_story_stage1;
        this.gameBgResId = R.drawable.bg_game_stage1;

        setStory(
                "8:00 น. แล้ว! วันนี้คือวันสัมภาษณ์ขอฝึกงานครั้งแรกในชีวิตของเด็กปี 2 ตื่นเต้นจนนอนไม่ค่อยหลับเลย! ถึงจะเป็นแค่การฝึกงาน แต่บริษัทนี้รับยากมาก ก่อนออกเดินทาง ต้องรีบเช็กกระเป๋าและเก็บอุปกรณ์พร้อมผลงานโปรเจกต์รายวิชาปี 2 ไปโชว์พี่ๆ ให้ครบ!",
                "เวลาหมดลงก่อนที่ผู้เล่นจะหาของครบในห้อง หนังสือทุกเล่มในห้องสมุดปิดตัวลงพร้อมกัน ประตูทางออกล็อกสนิท ผู้เล่นติดอยู่ในห้องสมุดแห่งความรู้ และไม่มีโอกาสไปต่อยังด่านถัดไป"
        );

        addRequiredItem(new NormalItem(1, "Dev Laptop", "โน้ตบุ๊กติดสติ๊กเกอร์รวมเทคโนโลยี", R.drawable.ic_s1_item1_shadow, R.drawable.ic_s1_item1_color, 0.78f, 0.42f));
        addRequiredItem(new NormalItem(2, "USB Flash Drive", "แฟลชไดร์ฟ USB", R.drawable.ic_s1_item2_shadow, R.drawable.ic_s1_item2_color, 0.66f, 0.86f));
        addRequiredItem(new NormalItem(3, "Resume", "เรซูเม่", R.drawable.ic_s1_item3_shadow, R.drawable.ic_s1_item3_color, 0.62f, 0.96f));

        setSecretItem(new SecretItem(101, "OOP Secret Flashcard", "ไอเทมลับด่าน 1", "เก็บครบเพื่อปลด True Ending", R.drawable.ic_secret_stage1, 0.24f, 0.38f));
    }

    @Override
    public void loadContent() {
        System.out.println("โหลดเนื้อหาด่าน 1: หัวข้อ " + vocabularyTopic);
    }

    public String getVocabularyTopic() {
        return vocabularyTopic;
    }
}
