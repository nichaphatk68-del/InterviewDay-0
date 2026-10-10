package com.example.interviewday.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import com.example.interviewday.R;

public class StartGameActivity extends BaseActivity {
    //ATTRIBUTE
    private ImageButton btnStartGame; // ตัวแปรเก็บวัตถุปุ่ม START GAME
    //METHOD
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        // เริ่มเล่นเพลงพื้นหลัง bg_music.mp3
        com.example.interviewday.controllers.BackgroundMusicManager.start(this);

        // ดึงปุ่มจาก XML มาใส่ Attribute
        btnStartGame = findViewById(R.id.btnStartGame);

        // กำหนดเมธอดรับฟังการคลิกเพื่อเปลี่ยนหน้าไป MainMenuActivity
        btnStartGame.setOnClickListener(v -> {
            Intent intent = new Intent(StartGameActivity.this, MainMenuActivity.class);
            startActivity(intent);
        });
    }
}