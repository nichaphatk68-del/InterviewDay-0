package com.example.interviewday.models;

import com.example.interviewday.R;

/*
 * คลาสลูก Level3
 * - [OOP Concept: Inheritance] สืบทอดจากคลาสแม่ Level
 * - [OOP Concept: Polymorphism] Override เมธอด loadContent() เพื่อจัดการโหลดเนื้อหาของด่านที่ 3
 */
public class Level3 extends Level {

    // ===== [OOP Concept: Constructor] =====
    // กำหนดรายละเอียด ไอเทม และเรื่องราวประจำด่านที่ 3 (ห้องสัมภาษณ์งาน)
    public Level3() {
        super(3, "ห้องสัมภาษณ์งานกับทีมเทคนิคและวิศวกร");
        this.storyBgResId = R.drawable.bg_story_stage3;
        this.gameBgResId = R.drawable.bg_game_stage3;

        setStory(
                "08:45 น. ในที่สุดก็มาถึงบริษัท! บรรยากาศดูเป็นมืออาชีพและกดดันกว่าที่คิด พี่ HR บอกว่าถึงจะเป็นเด็กปี 2 แต่พี่ๆ จะทดสอบแนวคิดการเขียนโค้ดบนกระดาน และสัมภาษณ์เป็นภาษาอังกฤษด้วย! ต้องรวบรวมอุปกรณ์เตรียมตัวให้พร้อมก่อนถูกเรียกชื่อเข้าห้องสัมภาษณ์"
        );

        addRequiredItem(new NormalItem(1, "Whiteboard Marker", "ปากกาไวท์บอร์ด", R.drawable.ic_s3_item1_shadow, R.drawable.ic_s3_item1_color, 0.20f, 0.80f));
        addRequiredItem(new NormalItem(2, "Architecture Diagram", "ผังไดอะแกรม", R.drawable.ic_s3_item2_shadow, R.drawable.ic_s3_item2_color, 0.43f, 0.31f));
        addRequiredItem(new NormalItem(3, "Employee Card", "บัตรพนักงาน", R.drawable.ic_s3_item3_shadow, R.drawable.ic_s3_item3_color, 0.94f, 0.38f));

        setSecretItem(new SecretItem(103, "Executive Recommendation Letter", "ไอเทมลับด่าน 3", "จดหมายรับรองจากผู้บริหาร", R.drawable.ic_secret_stage3, 0.64f, 0.30f));
    }

    // ===== [OOP Concept: Polymorphism] =====
    @Override
    public void loadContent() {
        System.out.println("โหลดเนื้อหาด่าน 3 เรียบร้อยแล้ว");
    }
}
