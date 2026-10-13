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

/*
 * คลาส EndingActivity
 * - [OOP Concept: Inheritance] สืบทอดจาก BaseActivity
 * - [MVC Pattern: View] หน้าแสดงฉากจบ (Normal, Special, Failed) ดึงข้อมูลจาก EndingController
 * - [Event-driven] ดักจับ Event กดปุ่มถัดไป กลับหน้าหลัก หรือเล่นอีกครั้ง
 * - [Multitasking] จัดการ Intent Flags เพื่อล้าง Task Stack เก่าเมื่อย้อนกลับหน้าหลัก
 */
public class EndingActivity extends BaseActivity {

    // ===== [OOP Concept: Encapsulation] Attributes: UI Views =====
    private ImageView imgEndingBg;          // ภาพพื้นหลังฉากจบ
    private ImageView imgDialogueBg;        // ภาพพื้นหลังกล่องข้อความ
    private TextView tvEndingDescription;   // ข้อความบรรยายฉากจบ
    private ImageView imgLogoLeft;          // โลโก้แสดงฝั่งซ้าย
    private ImageView imgLogoRight;         // โลโก้แสดงฝั่งขวา

    private LinearLayout layoutFailedButtons; // เลย์เอาต์รวมปุ่มกรณีฉากจบ Failed
    private ImageButton btnMainMenu;          // ปุ่มกลับหน้าหลัก
    private ImageButton btnRestart;           // ปุ่มเล่นใหม่อีกครั้ง
    private ImageButton btnNext;              // ปุ่มถัดไป

    // ===== [MVC Pattern: Controller] =====
    private EndingController endingController; // Controller จัดการฉากจบตามหลัก MVC

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);

        initViews();

        String endingType = getIntent().getStringExtra("ENDING_TYPE");
        endingController = new EndingController(endingType);

        setupEndingUI();  // [MVC Pattern] แสดงผล UI ตาม Ending Model
        setupListeners(); // [Event-driven Programming] ตั้งค่าการคลิกปุ่ม
    }
    //ผูก XML เข้ากับ Attribute
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
    //ผูกเหตุการณ์การคลิกปุ่ม (OnClickListener) ให้กับปุ่มกดทั้ง 3 ปุ่มในหน้าฉากจบ
    private void setupListeners() {
        btnNext.setOnClickListener(v -> goToSummaryOrMenu());
        btnMainMenu.setOnClickListener(v -> goToMainMenu());
        btnRestart.setOnClickListener(v -> restartGame());
    }

    //ตรวจสอบประเภทฉากจบแล้วเลือกหน้าที่จะพาผู้เล่นไปต่อ
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

    //สลับกลับไปหน้าเมนูหลัก ล้าง Activity เก่าๆ ทั้งหมดที่เคยเปิดค้างไว้ใน Back Stack ออก ช่วยคืนหน่วยความจำและป้องกันไม่ให้ผู้เล่นกดปุ่ม Back ย้อนกลับมาหน้าฉากจบได้
    private void goToMainMenu() {
        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    //เริ่มต้นเล่นเกมใหม่อีกครั้งทันที ตั้งค่าให้เกมเริ่มนับตั้งแต่ด่านแรก ใส่ Intent Flags เพื่อล้าง Stack หน้าจอเก่าทิ้งทั้งหมด
    private void restartGame() {
        Intent intent = new Intent(this, GameStageActivity.class);
        intent.putExtra("STAGE_NUMBER", 1);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
