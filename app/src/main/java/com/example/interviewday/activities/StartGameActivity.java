package com.example.interviewday.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import com.example.interviewday.R;

/*
 * คลาส StartGameActivity
 * - [OOP Concept: Inheritance] สืบทอดจาก BaseActivity
 * - [MVC Pattern: View] หน้าจอเริ่มต้นเกม (Title Screen)
 * - [Event-driven] ดักจับ Event กดปุ่ม START GAME เพื่อเปลี่ยนหน้า
 * - [Multitasking] เริ่มเล่นเพลง BGM ในฉากหลัง
 */
public class StartGameActivity extends BaseActivity {

    // [OOP Concept: Encapsulation] Attributes: UI Button
    private ImageButton btnStartGame; // ตัวแปรเก็บวัตถุปุ่ม START GAME

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        // [Multitasking] เริ่มเล่นเพลงพื้นหลัง bg_music.mp3 ในฉากหลัง
        com.example.interviewday.controllers.BackgroundMusicManager.start(this);

        // ดึงปุ่มจาก XML มาใส่ Attribute
        btnStartGame = findViewById(R.id.btnStartGame);

        // [Event-driven Programming] กำหนด Event Listener เมื่อคลิกปุ่มเพื่อเปลี่ยนหน้าไป MainMenuActivity
        btnStartGame.setOnClickListener(v -> {
            Intent intent = new Intent(StartGameActivity.this, MainMenuActivity.class);
            startActivity(intent);
        });
    }
}