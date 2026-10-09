package com.example.interviewday.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.interviewday.R;
import com.example.interviewday.controllers.ResultController;

/**
 * คลาส SummaryActivity
 * หน้าที่: แสดงผลหน้าสรุปคะแนน สถิติเวลาในการเล่นแต่ละด่าน และประเภทฉากจบ (Normal / Special Badge)
 * หลักการ MVC: ประมวลผลและฟอร์แมตข้อมูลเวลาผ่าน ResultController
 */
public class SummaryActivity extends BaseActivity {

    // ===== Attributes: UI Views =====
    private ImageView imgEndingTypeBadge; // ป้ายตราสัญลักษณ์ฉากจบ (Normal หรือ Special)
    private TextView tvTotalTime;          // ข้อความแสดงเวลารวมทั้งหมด
    private TextView tvLevel1Time;         // ข้อความแสดงเวลาด่านที่ 1
    private TextView tvLevel2Time;         // ข้อความแสดงเวลาด่านที่ 2
    private TextView tvLevel3Time;         // ข้อความแสดงเวลาด่านที่ 3
    private ImageButton btnMainMenu;       // ปุ่มกดกลับไปหน้า Main Menu

    // ===== Attribute: Controller (MVC) =====
    private ResultController resultController; // Controller ประมวลผลสถิติและคะแนน

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        resultController = new ResultController();

        // 1. ผูก View
        initViews();

        // 2. รับข้อมูลสถิติที่ส่งมาจาก Intent
        Intent intent = getIntent();
        String endingType = intent.getStringExtra("ENDING_TYPE"); // "NORMAL" หรือ "SPECIAL"
        String level1Time = intent.getStringExtra("LEVEL1_TIME");
        String level2Time = intent.getStringExtra("LEVEL2_TIME");
        String level3Time = intent.getStringExtra("LEVEL3_TIME");
        String totalTime = intent.getStringExtra("TOTAL_TIME");

        // 3. กำหนดรูปป้าย Normal / Special Ending ผ่าน Controller
        setupEndingBadge(endingType);

        // 4. แสดงผลเวลาผ่าน Controller
        displayTimeData(level1Time, level2Time, level3Time, totalTime);

        // 5. ตั้งค่าปุ่มกลับหน้า Main Menu
        btnMainMenu.setOnClickListener(v -> goToMainMenu());
    }

    private void initViews() {
        imgEndingTypeBadge = findViewById(R.id.imgEndingTypeBadge);
        tvTotalTime = findViewById(R.id.tvTotalTime);
        tvLevel1Time = findViewById(R.id.tvLevel1Time);
        tvLevel2Time = findViewById(R.id.tvLevel2Time);
        tvLevel3Time = findViewById(R.id.tvLevel3Time);
        btnMainMenu = findViewById(R.id.btnMainMenu);
    }

    // กำหนดรูปป้ายตราสัญลักษณ์ฉากจบโดยรับ Resource ID จาก ResultController
    private void setupEndingBadge(String endingType) {
        if (resultController != null) {
            imgEndingTypeBadge.setImageResource(resultController.getEndingBadgeResId(endingType));
        }
    }

    // แสดงผลข้อความเวลาโดยใช้ฟังก์ชันจัดฟอร์แมตจาก ResultController
    private void displayTimeData(String l1, String l2, String l3, String total) {
        if (resultController != null) {
            tvLevel1Time.setText("LEVEL1 :   " + resultController.formatTime(l1));
            tvLevel2Time.setText("LEVEL2 :   " + resultController.formatTime(l2));
            tvLevel3Time.setText("LEVEL3 :   " + resultController.formatTime(l3));
            tvTotalTime.setText(resultController.formatTime(total));
        }
    }

    // สลับหน้าย้อนกลับไปที่ MainMenuActivity
    private void goToMainMenu() {
        Intent intent = new Intent(SummaryActivity.this, MainMenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public ResultController getResultController() {
        return resultController;
    }
}
