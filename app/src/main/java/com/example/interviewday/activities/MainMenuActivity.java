package com.example.interviewday.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import com.example.interviewday.R;
import com.example.interviewday.dialogs.SettingsDialog;

/*
 * คลาส MainMenuActivity
 * - [OOP Concept: Inheritance] สืบทอดจาก BaseActivity
 * - [MVC Pattern: View] หน้าเมนูหลักสำหรับเลือกด่าน วิธีเล่น สมุดโน้ต หรือตั้งค่า
 * - [Event-driven] ดักจับการกดปุ่มเปลี่ยนหน้าเมนูต่างๆ
 * - [Multitasking] สลับหน้าจอด้วย Intent โดยเพลง BGM ยังคงเล่นต่อเนื่องในฉากหลัง
 */
public class MainMenuActivity extends BaseActivity {

    // ===== [OOP Concept: Encapsulation] Attributes: UI Buttons =====
    private ImageButton btnSettings;     // ปุ่มตั้งค่า
    private ImageButton btnSelectLevel;  // ปุ่มเลือกด่าน
    private ImageButton btnHowToPlay;   // ปุ่มวิธีเล่น
    private ImageButton btnNotes;       // ปุ่มสมุดโน้ต

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // 1. ผูกปุ่มกดใน XML เข้ากับ Attribute ฝั่ง Java
        initViews();

        // 2. [Event-driven Programming] ตั้งค่าการคลิกเปลี่ยนหน้าให้กับทุกปุ่ม
        setupListeners();
    }

    // เมธอดผูก View จาก XML เข้ากับตัวแปร
    private void initViews() {
        btnSettings = findViewById(R.id.btnSettings);
        btnSelectLevel = findViewById(R.id.btnSelectLevel);
        btnHowToPlay = findViewById(R.id.btnHowToPlay);
        btnNotes = findViewById(R.id.btnNotes);
    }

    // เมธอดจัดการ Event การกดปุ่มเปลี่ยนหน้า
    private void setupListeners() {
        // กดปุ่มเลือกด่าน -> ไปหน้า StageSelectionActivity
        btnSelectLevel.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenuActivity.this, StageSelectionActivity.class);
            startActivity(intent);
        });

        // กดปุ่มวิธีเล่น -> ไปหน้า HowToPlayActivity
        btnHowToPlay.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenuActivity.this, HowToPlayActivity.class);
            startActivity(intent);
        });

        // กดปุ่มสมุดโน้ต -> ไปหน้า NotebookActivity
        btnNotes.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenuActivity.this, NotebookActivity.class);
            startActivity(intent);
        });

        // กดปุ่มตั้งค่า -> เปิดป๊อปอัพตั้งค่า SettingsDialog
        btnSettings.setOnClickListener(v -> {
            SettingsDialog settingsDialog = SettingsDialog.newInstance();
            settingsDialog.show(getSupportFragmentManager(), "SettingsDialog");
        });
    }
}
