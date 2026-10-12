package com.example.interviewday.activities;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

/*
 * คลาส BaseActivity
 * - [OOP Concept: Inheritance] คลาสแม่สำหรับทุก Activity สืบทอดจาก AppCompatActivity
 * - [OOP Concept: Encapsulation & Polymorphism] ซ่อนระบบ hideSystemUI() และ Override onCreate/onResume
 * - [Multitasking] ซ่อนแถบระบบอัตโนมัติเมื่อพับจอ/สลับแอปแล้วกลับเข้ามาใหม่
 */
public class BaseActivity extends AppCompatActivity {

    // [OOP Concept: Polymorphism] Overriding onCreate จาก Lifecycle ของ AppCompatActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideSystemUI(); // เรียกฟังก์ชันซ่อนแถบระบบทันทีที่เริ่มสร้างหน้าจอ
    }

    // [OOP Concept: Polymorphism] Overriding onResume เพื่อจัดการเมื่อผู้เล่นกลับมาที่หน้าจอนี้
    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUI(); // ซ่อนแถบระบบอีกครั้งหากผู้เล่นพับจอ/สลับแอปแล้วกลับเข้ามาใหม่
    }

    // [OOP Concept: Encapsulation] เมธอดส่วนตัวสำหรับซ่อน Status Bar และ Navigation Bar
    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
    }
}