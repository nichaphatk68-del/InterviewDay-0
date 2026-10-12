package com.example.interviewday.activities;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.interviewday.R;
import com.example.interviewday.controllers.NotebookController;

/*
 * คลาส NotebookActivity
 * - [OOP Concept: Inheritance] สืบทอดจาก BaseActivity
 * - [MVC Pattern: View] หน้าสมุดโน้ตแสดงคำศัพท์และไอเท็ม ดึงข้อมูลจาก NotebookController
 * - [Event-driven] ดักจับ Event กดปุ่ม Home เพื่อย้อนกลับ
 * - [Multitasking] ซิงค์ข้อมูลไอเท็มที่ค้นพบจากด่านต่างๆ มาแสดงผล
 */
public class NotebookActivity extends BaseActivity {

    // ===== [OOP Concept: Encapsulation] Attributes: UI Views =====
    private ImageButton btnHome;      // ปุ่มกดกลับหน้าหลัก (Main Menu)
    private TextView tvContentLeft;   // พื้นที่แสดงข้อความ/คำศัพท์ บนกระดาษฝั่งซ้าย
    private TextView tvContentRight;  // พื้นที่แสดงข้อความ/รายการไอเท็ม บนกระดาษฝั่งขวา

    // ===== [MVC Pattern: Controller] =====
    private NotebookController notebookController; // Controller สำหรับจัดการข้อมูลสมุดโน้ตตามหลัก MVC

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook);

        // [OOP Concept: Instantiation] สร้าง Object Controller
        notebookController = new NotebookController();

        initViews();          // 1. ผูก View
        setupListeners();     // 2. [Event-driven Programming] ตั้งค่าการคลิกปุ่ม
        loadNotebookData();   // 3. [MVC Pattern] โหลดข้อมูลลงหน้ากระดาษผ่าน Controller
    }

    // ผูก View จาก XML เข้ากับ Attribute
    private void initViews() {
        btnHome = findViewById(R.id.btnHome);
        tvContentLeft = findViewById(R.id.tvContentLeft);
        tvContentRight = findViewById(R.id.tvContentRight);
    }

    // กำหนดคำสั่งเมื่อผู้เล่นคลิกปุ่ม Home
    private void setupListeners() {
        btnHome.setOnClickListener(v -> finish()); // ปิดหน้านี้ ย้อนกลับไปหน้าเดิม
    }

    // ดึงข้อมูลสมุดโน้ตจาก NotebookModel ผ่าน Controller มาแสดงผลที่ View
    private void loadNotebookData() {
        if (notebookController != null) {
            updateNotebookContent(
                    notebookController.getLeftPageText(),
                    notebookController.getRightPageText()
            );
        }
    }

    // เมธอดอัปเดตข้อความบนกระดาษฝั่งซ้ายและขวา
    public void updateNotebookContent(String leftPageText, String rightPageText) {
        if (tvContentLeft != null) {
            tvContentLeft.setText(leftPageText);
        }
        if (tvContentRight != null) {
            tvContentRight.setText(rightPageText);
        }
    }

    public NotebookController getNotebookController() {
        return notebookController;
    }
}
