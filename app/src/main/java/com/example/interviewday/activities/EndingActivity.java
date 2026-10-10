package com.example.interviewday.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.interviewday.R;
import com.example.interviewday.controllers.EndingController;
import com.example.interviewday.models.Ending;

/**
 * คลาส EndingActivity
 * หน้าที่: แสดงผลฉากจบของเกม (Normal, Special/True, Failed Ending)
 * หลักการ MVC: ดึงข้อมูลภาพพื้นหลัง โลโก้ และเนื้อเรื่องฉากจบมาจาก Ending Model ผ่าน EndingController
 */
public class EndingActivity extends BaseActivity {

    // ===== Attributes: UI Views =====
    private ImageView imgEndingBg;          // ภาพพื้นหลังฉากจบ
    private ImageView imgDialogueBg;        // ภาพพื้นหลังกล่องข้อความ
    private TextView tvEndingDescription;   // ข้อความบรรยายฉากจบ
    private ImageView imgLogoLeft;          // โลโก้แสดงฝั่งซ้าย
    private ImageView imgLogoRight;         // โลโก้แสดงฝั่งขวา

    private LinearLayout layoutFailedButtons; // เลย์เอาต์รวมปุ่มกรณีฉากจบ Failed
    private ImageButton btnMainMenu;          // ปุ่มกลับหน้าหลัก
    private ImageButton btnRestart;           // ปุ่มเล่นใหม่อีกครั้ง
    private ImageButton btnNext;              // ปุ่มถัดไป

    // ===== Attribute: Controller (MVC) =====
    private EndingController endingController; // Controller จัดการฉากจบ

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);

        initViews();

        String endingType = getIntent().getStringExtra("ENDING_TYPE");
        endingController = new EndingController(endingType);

        setupEndingUI();  // แสดงผล UI ตาม Ending Model
        setupListeners(); // ตั้งค่าการคลิกปุ่ม
    }

    private void initViews() {
        imgEndingBg = findViewById(R.id.imgEndingBg);
        imgDialogueBg = findViewById(R.id.imgDialogueBg);
        tvEndingDescription = findViewById(R.id.tvEndingDescription);
        imgLogoLeft = findViewById(R.id.imgLogoLeft);
        imgLogoRight = findViewById(R.id.imgLogoRight);

        layoutFailedButtons = findViewById(R.id.layoutFailedButtons);
        btnMainMenu = findViewById(R.id.btnMainMenu);
        btnRestart = findViewById(R.id.btnRestart);
        btnNext = findViewById(R.id.btnNext);
    }

    // ดึงข้อมูลภาพและข้อความจาก Ending Model ผ่าน Controller มาแสดงผลที่ View
    private void setupEndingUI() {
        Ending ending = endingController.getCurrentEnding();
        if (ending == null) return;

        // กำหนดภาพและข้อความจาก Model
        imgEndingBg.setImageResource(ending.getBgResId());
        imgDialogueBg.setImageResource(ending.getDialogueBgResId());
        tvEndingDescription.setText(ending.getDescription());

        // กำหนดตำแหน่งการแสดงโลโก้และปุ่มกดตามประเภทฉากจบ
        if (ending.isLogoLeft()) {
            imgLogoLeft.setImageResource(ending.getLogoResId());
            imgLogoLeft.setVisibility(View.VISIBLE);
            imgLogoRight.setVisibility(View.GONE);

            btnNext.setVisibility(View.VISIBLE);
            layoutFailedButtons.setVisibility(View.GONE);
        } else {
            imgLogoLeft.setVisibility(View.GONE);
            imgLogoRight.setImageResource(ending.getLogoResId());
            imgLogoRight.setVisibility(View.VISIBLE);

            btnNext.setVisibility(View.GONE);
            layoutFailedButtons.setVisibility(View.VISIBLE);
        }
    }

    private void setupListeners() {
        btnNext.setOnClickListener(v -> goToSummaryOrMenu());
        btnMainMenu.setOnClickListener(v -> goToMainMenu());
        btnRestart.setOnClickListener(v -> restartGame());
    }

    private void goToSummaryOrMenu() {
        String endingType = endingController != null ? endingController.getEndingType() : "NORMAL";
        if ("FAILED".equals(endingType) || "FAILED_ENDING".equals(endingType)) {
            goToMainMenu();
        } else {
            Intent intent = new Intent(this, SummaryActivity.class);
            if (getIntent() != null && getIntent().getExtras() != null) {
                intent.putExtras(getIntent().getExtras());
            }
            intent.putExtra("ENDING_TYPE", endingType);
            startActivity(intent);
            finish();
        }
    }

    private void goToMainMenu() {
        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void restartGame() {
        Intent intent = new Intent(this, GameStageActivity.class);
        intent.putExtra("STAGE_NUMBER", 1);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
