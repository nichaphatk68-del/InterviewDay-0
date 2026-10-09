package com.example.interviewday.models;

import com.example.interviewday.R;

/**
 * คลาสลูก: จัดการรายละเอียดและเนื้อหาของด่านที่ 2
 * OOP: Inheritance + Polymorphism
 */
public class Level2 extends Level {

    private String grammarTopic;

    public Level2() {
        super(2, "ห้องเรียนเครื่องมือคอมพิวเตอร์และอุปกรณ์อิเล็กทรอนิกส์พื้นฐาน");
        this.grammarTopic = "Computer Hardware Tools";
        this.storyBgResId = R.drawable.bg_story_stage2;
        this.gameBgResId = R.drawable.bg_game_stage2;

        setStory(
                "08:30 น. อีกครึ่งชั่วโมงจะถึงเวลาสัมภาษณ์! แต่พี่ HR เพิ่งไลน์มาบอกว่า พี่ๆ วิศวกรอยากดูทั้งซอร์สโค้ดและไฟล์สำรองแล็บด้วย ต้องแวะเข้าห้องแล็บคอมฯ คณะเพื่อเอาของก่อน แต่ห้องแล็บเพิ่งผ่านเซกชันแล็บฮาร์ดแวร์มา สภาพรกมาก ต้องรีบหาของตาม TO-DO LIST ให้เจอ!",
                "เวลาหมดลงก่อนที่ผู้เล่นจะหาอุปกรณ์ครบตามที่อาจารย์สั่ง อาจารย์ประกาศว่าสอบไม่ผ่าน ผู้เล่นถูกเชิญออกจากห้องเรียนและไม่สามารถเดินหน้าไปสู่ด่านสัมภาษณ์งานได้"
        );

        addRequiredItem(new NormalItem(1, "Ethernet Cable", "สาย LAN", R.drawable.ic_s2_item1_shadow, R.drawable.ic_s2_item1_color, 0.51f, 0.92f));
        addRequiredItem(new NormalItem(2, "External HDD", "เอ็กเทอร์นัล ฮาร์ดดิสก์", R.drawable.ic_s2_item2_shadow, R.drawable.ic_s2_item2_color, 0.38f, 0.50f));
        addRequiredItem(new NormalItem(3, "Algorithm Paper", "กระดาษอัลกอริทึม", R.drawable.ic_s2_item3_shadow, R.drawable.ic_s2_item3_color, 0.53f, 0.54f));

        setSecretItem(new SecretItem(102, "Special Microcontroller Board", "ไอเทมลับด่าน 2", "บอร์ดทดลองคอมพิวเตอร์", R.drawable.ic_secret_stage2, 0.54f, 0.77f));
    }

    @Override
    public void loadContent() {
        System.out.println("โหลดเนื้อหาด่าน 2: หัวข้อ " + grammarTopic);
    }

    public String getGrammarTopic() {
        return grammarTopic;
    }
}
