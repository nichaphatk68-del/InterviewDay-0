package com.example.interviewday.activities;

import android.os.Bundle;
import android.widget.ImageButton;
import com.example.interviewday.R;

/*
 * คลาส HowToPlayActivity
 * - [OOP Concept: Inheritance] สืบทอดจาก BaseActivity
 * - [MVC Pattern: View] หน้าคู่มือแนะนำวิธีเล่นเกม
 * - [Event-driven] ดักจับ Event กดปุ่ม Home เพื่อปิดหน้านี้
 * - [Multitasking] คืนพื้นที่หน่วยความจำและย้อนกลับ Activity เดิมใน Back Stack
 */
public class HowToPlayActivity extends BaseActivity {

    // [OOP Concept: Encapsulation] Attributes: UI View
    private ImageButton btnHome; // อ้างอิงถึงปุ่มกลับหน้าหลัก

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);

        // 1. ผูกปุ่มกดใน XML เข้ากับ Attribute
        initViews();

        // 2. [Event-driven Programming] ตั้งค่าเหตุการณ์เมื่อผู้เล่นกดปุ่ม
        setupListeners();
    }

    // เมธอดสำหรับค้นหาและผูกองค์ประกอบ UI จากไฟล์ XML
    private void initViews() {
        btnHome = findViewById(R.id.btnHome);
    }

    // เมธอดสำหรับจัดการคำสั่งกดปุ่ม
    private void setupListeners() {
        // กดปุ่ม Home -> ปิดหน้าคู่นี้แล้วกลับไปที่หน้า MainMenuActivity
        btnHome.setOnClickListener(v -> {
            finish(); // สั่งทำลาย Activity นี้เพื่อย้อนกลับไปหน้าเดิมที่เปิดมา
        });
    }
}