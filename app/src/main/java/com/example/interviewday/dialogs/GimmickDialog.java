package com.example.interviewday.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.interviewday.R;
import com.example.interviewday.controllers.GimmickController;

import java.util.ArrayList;

/**
 * คลาส GimmickDialog
 * หน้าที่: แสดงผล UI สำหรับมินิเกมตอบคำถามภาษาอังกฤษ (ปุ่มฟังเสียง, ไมโครโฟนตอบคำถาม, กล่องโน้ต)
 * หลักการ MVC: ดึงข้อมูลและส่งคำสั่งผ่าน GimmickController เท่านั้น ห้ามฮาร์ดโค้ดข้อมูลโจทย์หรือเฉลยใน View
 */
public class GimmickDialog extends BaseDialog {

    // ===== Attributes: UI Views =====
    private ImageView ivQuestionBadge;    // โลโก้แสดงหมายเลขข้อ (QUESTION 1, 2, 3)
    private TextView tvInstruction;       // ข้อความคำแนะนำประจำด่าน
    private LinearLayout btnListen;       // ปุ่มกดฟังเสียงโจทย์ภาษาอังกฤษ
    private LinearLayout btnSpeak;        // ปุ่มกดเปิดไมโครโฟนเพื่อพูดตอบ
    private TextView tvPlayerAnswer;      // แสดงข้อความภาษาอังกฤษที่ผู้เล่นพูดออกเสียง
    private ImageButton btnNextInside;    // ปุ่มถัดไป (สำหรับข้อ 1 และ 2)
    private ImageButton btnNextOutside;   // ปุ่มถัดไป (สำหรับข้อ 3)
    private View cardNotepad;             // การ์ดแสดงสมุดโน้ตฝั่งขวา (ใช้ในด่าน 3)

    // ===== Attribute: Controller (MVC) =====
    private GimmickController gimmickController; // ตัวกลางควบคุมลอจิกมินิเกม

    // ===== Speech-to-Text Launcher =====
    // ระบบเปิด Activity รับเสียงพูดของผู้เล่น แล้วแปลงเป็นข้อความ String
    private final ActivityResultLauncher<Intent> speechLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    ArrayList<String> matches = result.getData().getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (matches != null && !matches.isEmpty()) {
                        String spokenText = matches.get(0);
                        onSpeechResult(spokenText); // ส่งข้อความที่พูดได้ไปประมวลผลต่อ
                    }
                }
            }
    );

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // สร้าง Controller หากยังไม่มีการกำหนดค่าเริ่มต้น
        if (gimmickController == null) {
            gimmickController = new GimmickController(1, 0);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // ปรับขนาดหน้าต่างสำหรับ GimmickDialog ให้กว้างพอสำหรับแสดงสมุดโน้ตฝั่งขวาในด่าน 3
        if (getDialog() != null && getDialog().getWindow() != null) {
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int width = (int) (metrics.widthPixels * 0.88f);
            getDialog().getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // วาดหน้าจอ UI จากไฟล์ activity_gimmick_dialog.xml
        View view = inflater.inflate(R.layout.activity_gimmick_dialog, container, false);

        initViews(view);      // 1. ผูก View
        setupListeners();     // 2. ตั้งค่าปุ่มกด
        updateUI();           // 3. แสดงผลหน้าจอตาม Model ผ่าน Controller

        return view;
    }

    // เมธอดผูก Element ใน XML เข้ากับตัวแปร Java
    private void initViews(View view) {
        ivQuestionBadge = view.findViewById(R.id.ivQuestionBadge);
        tvInstruction = view.findViewById(R.id.tvInstruction);
        btnListen = view.findViewById(R.id.btnListen);
        btnSpeak = view.findViewById(R.id.btnSpeak);
        tvPlayerAnswer = view.findViewById(R.id.tvPlayerAnswer);
        btnNextInside = view.findViewById(R.id.btnNextInside);
        btnNextOutside = view.findViewById(R.id.btnNextOutside);
        cardNotepad = view.findViewById(R.id.cardNotepad);
    }

    // เมธอดกำหนด Event การรับคำสั่งจากผู้เล่น (Input Handling)
    private void setupListeners() {
        // กดปุ่มฟังเสียงอ่านโจทย์ภาษาอังกฤษ -> ส่งคำสั่งไป Controller
        if (btnListen != null) {
            btnListen.setOnClickListener(v -> {
                if (gimmickController != null && getContext() != null) {
                    gimmickController.playQuestionAudio(getContext());
                }
            });
        }

        // กดปุ่มไมโครโฟนเพื่อเริ่มอัดเสียงพูด -> เรียกใช้ Android Intent Speech Recognizer
        if (btnSpeak != null) {
            btnSpeak.setOnClickListener(v -> {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please pronounce the English sentence....");
                speechLauncher.launch(intent);
            });
        }

        // กดปุ่มถัดไป -> ให้ Controller เลื่อนข้อ
        if (btnNextInside != null) {
            btnNextInside.setOnClickListener(v -> handleNextQuestion());
        }

        if (btnNextOutside != null) {
            btnNextOutside.setOnClickListener(v -> handleNextQuestion());
        }
    }

    // เมธอดอัปเดตการแสดงผลบน View โดยดึงข้อมูลจาก Controller (MVC)
    public void updateUI() {
        if (getContext() == null || gimmickController == null) return;

        // 1. ตั้งค่ารูปโลโก้ข้อจาก Model ผ่าน Controller
        if (ivQuestionBadge != null) {
            ivQuestionBadge.setImageResource(gimmickController.getBadgeResId());
        }

        // 2. ตั้งค่าข้อความคำแนะนำจาก Model ผ่าน Controller
        if (tvInstruction != null) {
            tvInstruction.setText(gimmickController.getInstructionText());
        }

        // 3. กำหนดการมองเห็นของการ์ดสมุดโน้ตจาก Model ผ่าน Controller
        if (cardNotepad != null) {
            cardNotepad.setVisibility(gimmickController.isNotepadVisible() ? View.VISIBLE : View.GONE);
        }

        // 4. รีเซ็ตกล่องคำตอบและซ่อนปุ่มถัดไปก่อน
        if (tvPlayerAnswer != null) {
            tvPlayerAnswer.setText("กดปุ่มไมค์แล้วเริ่มพูดคำตอบ...");
        }
        if (btnNextInside != null) {
            btnNextInside.setVisibility(View.GONE);
        }
        if (btnNextOutside != null) {
            btnNextOutside.setVisibility(View.GONE);
        }
    }

    // เมธอดทำงานเมื่อระบบ Speech-to-Text ส่งเสียงพูดกลับมา
    public void onSpeechResult(String recognizedText) {
        // แสดงข้อความที่ผู้เล่นพูดลงใน TextView
        if (tvPlayerAnswer != null) {
            tvPlayerAnswer.setText(recognizedText);
        }

        if (gimmickController == null) {
            gimmickController = new GimmickController(1, 0);
        }

        // ส่งข้อความที่พูดไปให้ Controller ตรวจสอบความถูกต้องเปรียบเทียบกับ Model
        boolean isCorrect = gimmickController.checkAnswer(recognizedText);

        if (isCorrect) {
            // กรณีพูดถูกต้อง: แสดง Toast ชื่นชม และปลดล็อกปุ่ม "ถัดไป"
            if (getContext() != null) {
                Toast.makeText(getContext(), "Well done! That's the correct answer. (" + recognizedText + ")", Toast.LENGTH_SHORT).show();
            }

            // สลับปุ่มถัดไปตามข้อที่เล่น
            if (gimmickController.getCurrentQuestion() == 2) {
                if (btnNextOutside != null) btnNextOutside.setVisibility(View.VISIBLE);
                if (btnNextInside != null) btnNextInside.setVisibility(View.GONE);
            } else {
                if (btnNextInside != null) btnNextInside.setVisibility(View.VISIBLE);
                if (btnNextOutside != null) btnNextOutside.setVisibility(View.GONE);
            }
        } else {
            // กรณีพูดไม่ถูก: ดึงเฉลยจาก Controller มาแสดงคำแนะแนวเพื่อลองใหม่
            if (getContext() != null) {
                String target = gimmickController.getTargetSentence();
                Toast.makeText(getContext(), "That's still not correct. Try saying: \"" + target + "\"", Toast.LENGTH_LONG).show();
            }
        }
    }

    // เมธอดสั่งให้ Controller เปลี่ยนข้อคำถาม หรือปิดหน้าต่างป๊อปอัพเมื่อเล่นจบ
    private void handleNextQuestion() {
        if (gimmickController != null) {
            boolean hasNext = gimmickController.nextQuestion();
            if (hasNext) {
                updateUI(); // อัปเดต View ข้อใหม่
            } else {
                dismiss();  // ปิด Dialog เมื่อทำครบทุกด่าน
            }
        }
    }

    // เมธอดสำหรับกำหนดด่านและข้อจากภายนอก
    public void setStageAndQuestion(int stage, int question) {
        if (gimmickController == null) {
            gimmickController = new GimmickController(stage, question);
        } else {
            gimmickController.setStageAndQuestion(stage, question);
        }
    }

    // Getter สำหรับ Controller
    public GimmickController getGimmickController() {
        return gimmickController;
    }
}
