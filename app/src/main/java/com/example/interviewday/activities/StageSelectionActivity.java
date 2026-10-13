package com.example.interviewday.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import com.example.interviewday.R;
import com.example.interviewday.controllers.LevelController;

/*
 * คลาส StageSelectionActivity
 * - [OOP Concept: Inheritance] สืบทอดจาก BaseActivity
 * - [MVC Pattern: View] หน้าเลือกด่าน ดึงสถานะการปลดล็อกผ่าน LevelController
 * - [Event-driven] ดักจับ Event การเลือกด่าน และอัปเดตสถานะปุ่มใน onResume()
 * - [Multitasking] ส่งผ่านหมายเลขด่านไป StoryActivity และจัดการสภาวะแอป
 */
public class StageSelectionActivity extends BaseActivity {

    // ===== [OOP Concept: Encapsulation] Attributes: UI Views =====
    private ImageButton btnHome;    // ปุ่มย้อนกลับหน้าหลัก
    private ImageButton btnLevel1;  // ปุ่มเลือกด่าน 1
    private ImageButton btnLevel2;  // ปุ่มเลือกด่าน 2
    private ImageButton btnLevel3;  // ปุ่มเลือกด่าน 3

    // ===== [MVC Pattern: Controller] =====
    private LevelController levelController; // Controller สำหรับควบคุมสถานะและการเลือกด่านตามหลัก MVC

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_selection);

        // สร้าง Controller
        levelController = new LevelController();

        initViews();
        setupListeners();
    }

    // [Event-driven Programming & Multitasking] Callback เมื่อหน้าจอกลับมาทำงานอีกครั้ง
    @Override
    protected void onResume() {
        super.onResume();
        updateStageStatusUI(); // อัปเดตสถานะปุ่มปลดล็อกด่านเมื่อกลับมาหน้านี้
    }

    private void initViews() {
        btnHome = findViewById(R.id.btnHome);
        btnLevel1 = findViewById(R.id.btnLevel1);
        btnLevel2 = findViewById(R.id.btnLevel2);
        btnLevel3 = findViewById(R.id.btnLevel3);
    }

    private void setupListeners() {
        btnHome.setOnClickListener(v -> finish());

        // ปุ่มด่าน 1: เช็กสถานะการปลดล็อกผ่าน Controller
        btnLevel1.setOnClickListener(v -> {
            if (levelController.checkStageUnlock(1)) {
                openGameStage(1);
            }
        });

        // ปุ่มด่าน 2: เช็กสถานะการปลดล็อกผ่าน Controller
        btnLevel2.setOnClickListener(v -> {
            if (levelController.checkStageUnlock(2)) {
                openGameStage(2);
            }
        });

        // ปุ่มด่าน 3: เช็กสถานะการปลดล็อกผ่าน Controller
        btnLevel3.setOnClickListener(v -> {
            if (levelController.checkStageUnlock(3)) {
                openGameStage(3);
            }
        });
    }

    // เปิดหน้าเล่าเนื้อเรื่องประจำด่าน
    private void openGameStage(int stageNumber) {
        levelController.selectStage(stageNumber);
        Intent intent = new Intent(StageSelectionActivity.this, StoryActivity.class);
        intent.putExtra("STAGE_NUMBER", stageNumber);
        startActivity(intent);
    }

    // อัปเดตรูปภาพปุ่มเลือกด่านตามสถานะการปลดล็อกที่ได้รับจาก LevelController
    public void updateStageStatusUI() {
        boolean level2Unlocked = levelController.checkStageUnlock(2);
        boolean level3Unlocked = levelController.checkStageUnlock(3);

        btnLevel2.setImageResource(level2Unlocked ? R.drawable.btn_level_2 : R.drawable.btn_level_locked);
        btnLevel3.setImageResource(level3Unlocked ? R.drawable.btn_level_3 : R.drawable.btn_level_locked);
    }
}
