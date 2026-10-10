package com.example.interviewday;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.interviewday.activities.StartGameActivity;

/**
 * คลาส MainActivity
 * หน้าที่: จุดเริ่มต้นหลักของแอปพลิเคชัน
 * ทำหน้าที่เปลี่ยนหน้าเปิดไปยัง StartGameActivity ทันทีที่เข้าแอป
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // เริ่มเล่นเพลงพื้นหลัง bg_music.mp3 ตั้งแต่เข้าเกม
        com.example.interviewday.controllers.BackgroundMusicManager.start(getApplicationContext());
        
        // 1. สร้าง Intent เพื่อสลับหน้าไปยัง StartGameActivity
        Intent intent = new Intent(MainActivity.this, StartGameActivity.class);
        startActivity(intent);
        
        // 2. ปิดหน้า MainActivity เพื่อไม่ให้ผู้เล่นกดย้อนกลับมาหน้านี้ได้
        finish();
    }
}
