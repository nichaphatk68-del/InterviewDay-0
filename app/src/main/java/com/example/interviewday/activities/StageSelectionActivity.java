package com.example.interviewday.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import com.example.interviewday.R;
import com.example.interviewday.controllers.LevelController;

/**
 * คลาส StageSelectionActivity (View ตามหลัก MVC)
 * หน้าที่: หน้าเลือกด่าน (Stage 1, Stage 2, Stage 3)
 * หลักการ MVC: ตรวจสอบสถานะการปลดล็อกด่านจาก LevelModel ผ่าน LevelController
 */
public class StageSelectionActivity extends BaseActivity {

    // ===== Attributes: UI Views =====
    private ImageButton btnHome;    // ปุ่มย้อนกลับหน้าหลัก
    private ImageButton btnLevel1;  // ปุ่มเลือกด่าน 1
    private ImageButton btnLevel2;  // ปุ่มเลือกด่าน 2
    private ImageButton btnLevel3;  // ปุ่มเลือกด่าน 3

    // ===== Attribute: Controller (MVC) =====
    private LevelController levelController; // Controller สำหรับควบคุมสถานะและการเลือกด่าน

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_selection);

        // สร้าง Controller
        levelController = new LevelController();

        initViews();
        setupListeners();
    }

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

    // สั่งเปิดหน้าเล่าเนื้อเรื่องประจำด่าน
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
