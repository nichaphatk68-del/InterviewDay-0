package com.example.interviewday.activities;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.interviewday.R;
import com.example.interviewday.controllers.NotebookController;

/*
 * คลาส NotebookActivity
 * - [OOP Concept: Inheritance] สืบทอดความสามารถจากคลาสแม่ BaseActivity (จัดการการแสดงผลแบบเต็มหน้าจอ)
 * - [MVC Pattern: View] ทำหน้าที่เป็น View แสดงหน้าสมุดโน้ต รวมคำศัพท์และไอเทมที่ผู้เล่นค้นพบ
 * - [Event-driven Programming] จัดการ Event เมื่อผู้เล่นกดปุ่ม Home เพื่อปิดหน้าสมุดโน้ต
 * - [Multitasking & Data Syncing] ดึงข้อมูลสถิติคำศัพท์และไอเทมที่ถูกบันทึกไว้ระหว่างเล่นด่านมาแสดงผล
 */
public class NotebookActivity extends BaseActivity {

    // ===== [OOP Concept: Encapsulation] Attributes: UI Views =====
    private ImageButton btnHome;      // ปุ่มกดกลับย้อนกลับ (Close Notebook / Return)
    private TextView tvContentLeft;   // ข้อความแสดงหมวดคำศัพท์บนหน้ากระดาษฝั่งซ้าย
    private TextView tvContentRight;  // ข้อความแสดงรายการไอเทมที่ค้นพบบนหน้ากระดาษฝั่งขวา

    // ===== [MVC Pattern: Controller] =====
    private NotebookController notebookController; // Controller จัดการประมวลผลข้อมูลสมุดโน้ตตามหลัก MVC

    // ===== Android Lifecycle Methods =====
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook);

        // [OOP Concept: Instantiation] สร้าง Object ของ NotebookController เพื่อใช้งาน
        notebookController = new NotebookController();

        // 1. ผูกองค์ประกอบ UI กับไฟล์ XML Layout
        initViews();

        // 2. [Event-driven Programming] กำหนด Listener ให้กับปุ่มควบคุม
        setupListeners();

        // 3. [MVC Pattern] โหลดและแสดงข้อมูลสมุดโน้ตลงหน้ากระดาษผ่าน Controller
        loadNotebookData();
    }

    // ===== View Initialization Method =====
    // ผูก View จากไฟล์ XML layout (activity_notebook.xml) เข้ากับ Attribute
    private void initViews() {
        btnHome = findViewById(R.id.btnHome);
        tvContentLeft = findViewById(R.id.tvContentLeft);
        tvContentRight = findViewById(R.id.tvContentRight);
    }

    // ===== [Event-driven Programming] =====
    // กำหนด Event Listener สำหรับการคลิกปุ่ม Home เพื่อย้อนกลับไปหน้าเดิม
    private void setupListeners() {
        btnHome.setOnClickListener(v -> finish()); // ปิด NotebookActivity แล้วย้อนกลับ
    }

    // ===== [MVC Pattern: View-Controller Integration] =====
    // ดึงข้อมูลข้อความหน้าฝั่งซ้ายและขวาจาก NotebookController มาแสดงผลบน View
    private void loadNotebookData() {
        if (notebookController != null) {
            updateNotebookContent(
                    notebookController.getLeftPageText(),
                    notebookController.getRightPageText()
            );
        }
    }

    // ===== UI Update Method =====
    // อัปเดตข้อความลงบน TextView กระดาษฝั่งซ้ายและฝั่งขวา
    public void updateNotebookContent(String leftPageText, String rightPageText) {
        if (tvContentLeft != null) {
            tvContentLeft.setText(leftPageText);
        }
        if (tvContentRight != null) {
            tvContentRight.setText(rightPageText);
        }
    }
}
