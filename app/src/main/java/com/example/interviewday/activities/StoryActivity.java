package com.example.interviewday.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.interviewday.R;
import com.example.interviewday.controllers.LevelController;
import com.example.interviewday.models.Level;

/*
 * คลาส StoryActivity
 * - [OOP Concept: Inheritance] สืบทอดจาก BaseActivity
 * - [MVC Pattern: View] หน้าแสดงเกริ่นนำเนื้อเรื่องประจำด่าน ดึงข้อมูลจาก LevelController
 * - [Event-driven] ดักจับการแตะกล่องข้อความเพื่อเริ่มด่านเล่นเกม
 * - [Multitasking] ส่งหมายเลขด่านไปยัง GameStageActivity เพื่อเริ่มนับเวลา
 */
public class StoryActivity extends BaseActivity {

    // ===== [OOP Concept: Encapsulation] Attributes: UI Views =====
    private ImageView imgBackground;         // ภาพพื้นหลังประจำด่าน
    private TextView tvStoryText;            // ข้อความบรรยายเนื้อเรื่องเกริ่นนำ
    private ConstraintLayout layoutStoryBox; // กล่องข้อความ (สำหรับแตะเพื่อไปต่อเข้าเล่นเกม)

    private int currentStage = 1;            // หมายเลขด่านปัจจุบัน
    private LevelController levelController; // [MVC Pattern: Controller] ตัวกลางจัดการข้อมูล Level และ Model

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        // 1. รับค่าหมายเลขด่านที่ส่งมาจาก StageSelectionActivity
        currentStage = getIntent().getIntExtra("STAGE_NUMBER", 1);
        levelController = new LevelController(currentStage);

        // 2. ผูก View
        initViews();

        // 3. [MVC Pattern] โหลดเนื้อหาและรูปภาพประจำด่านจาก Model ผ่าน Controller
        displayStoryContent();

        // 4. [Event-driven Programming] ตั้งค่า Event เมื่อแตะกล่องข้อความเพื่อเข้าสู่เกม
        setupListeners();
    }

    private void initViews() {
        imgBackground = findViewById(R.id.imgBackground);
        tvStoryText = findViewById(R.id.tvStoryText);
        layoutStoryBox = findViewById(R.id.layoutStoryBox);
    }

    // ดึงข้อมูลภาพและเนื้อเรื่องจาก Level Model ผ่าน LevelController มาแสดงผลที่ View
    private void displayStoryContent() {
        Level level = levelController.getCurrentLevelModel();
        if (level != null) {
            imgBackground.setImageResource(level.getStoryBgResId());
            tvStoryText.setText(level.getIntroStory());
        }
    }

    private void setupListeners() {
        // แตะที่กล่องข้อความเพื่อเริ่มด่านเล่นเกม (GameStageActivity)
        layoutStoryBox.setOnClickListener(v -> startGameStage());
    }

    private void startGameStage() {
        Intent intent = new Intent(StoryActivity.this, GameStageActivity.class);
        intent.putExtra("STAGE_NUMBER", currentStage);
        startActivity(intent);
        finish();
    }
}
