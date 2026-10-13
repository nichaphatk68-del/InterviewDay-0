package com.example.interviewday.dialogs;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.interviewday.R;

/*
 * คลาส PauseDialog
 * - [OOP Concept: Inheritance] สืบทอดจาก BaseDialog
 * - [MVC Pattern: View] หน้าต่างป๊อปอัพหยุดเกม แสดงเวลาคงเหลือและปุ่มทางเลือก
 * - [Event-driven] ดักจับการกดปุ่มแล้วส่ง Event Callback กลับไปหา Activity
 * - [Multitasking] สลับสถานะหยุด/เล่นเวลาต่อเมื่อเปิดหรือปิด Dialog
 */
public class PauseDialog extends BaseDialog {

    // [OOP Concept: Abstraction] Interface สัญญาการติดต่อสื่อสารระหว่าง PauseDialog กับ GameStageActivity
    //ประกาศชุดเมธอดต้นแบบ (Abstract Methods) 4 เมธอดเพื่อเป็นสัญญาในการส่ง Event เมื่อมีการกดปุ่มต่างๆ บน Dialog
    public interface PauseDialogListener {
        void onContinueClicked();
        void onOpenSettingsClicked();
        void onHomeClicked();
        void onRestartClicked();
    }

    private PauseDialogListener listener; //ตัวแปรถือ Reference ของ Listener เพื่อใช้เรียกส่ง Event กลับไปยัง Activity ที่เรียกใช้
    private String remainingTime = "00:00"; //ตัวแปรเก็บข้อความเวลาถอยหลังคงเหลือสำหรับนำไปแสดงผลบนหน้าต่าง Dialog

    // [OOP Concept: Factory Method Pattern] สร้าง Object PauseDialog และส่งข้อมูลเวลาคงเหลือผ่าน Bundle
    //ประกาศเมธอดแบบ static เพื่อให้คลาสอื่น สามารถเรียกใช้งานเพื่อสร้าง Dialog ได้ทันที
    public static PauseDialog newInstance(String remainingTime) {
        PauseDialog dialog = new PauseDialog();
        Bundle args = new Bundle();
        args.putString("REMAINING_TIME", remainingTime);//สร้างวัตถุ Bundle ซึ่งเป็นถุงเก็บข้อมูล Key-Value ของ Android แล้วฝากข้อความเวลาคงเหลือไว้ภายใต้ Key "REMAINING_TIME"
        dialog.setArguments(args);// ผูกถุงข้อมูล Bundle เข้ากับตัว Dialog เพื่อให้ Dialog นำไปแกะใช้แสดงผล
        return dialog;
    }

    // [OOP Concept: Encapsulation] เมธอดสำหรับกำหนด Listener รับเหตุการณ์
    public void setListener(PauseDialogListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // ดึงไฟล์ XML layout (activity_pause_dialog.xml) มาแสดงผล
        View view = inflater.inflate(R.layout.activity_pause_dialog, container, false);

        // ปรับแต่งขอบ Dialog ให้พื้นหลังโปร่งแสง
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        // อ่านค่าเวลาที่ส่งมาจาก newInstance
        if (getArguments() != null) {
            remainingTime = getArguments().getString("REMAINING_TIME", "00:00");
        }

        // ผูก View จาก XML
        TextView tvRemainingTime = view.findViewById(R.id.tvRemainingTime);
        ImageButton btnContinue = view.findViewById(R.id.btnContinue);
        ImageButton btnSettings = view.findViewById(R.id.btnSettings);
        ImageButton btnHome = view.findViewById(R.id.btnHome);
        ImageButton btnRestart = view.findViewById(R.id.btnRestart);

        // แสดงผลตัวเลขเวลาคงเหลือลงบนป้ายนาฬิกา
        tvRemainingTime.setText(remainingTime);

        // [Event-driven Programming] ดักจับเหตุการณ์การกดปุ่มต่างๆ แล้วส่ง Event Callback ไปที่ Listener
        btnContinue.setOnClickListener(v -> {
            if (listener != null) listener.onContinueClicked();
            dismiss(); // ปิด Dialog
        });

        btnSettings.setOnClickListener(v -> {
            if (listener != null) listener.onOpenSettingsClicked();
            dismiss(); // ปิด Dialog
        });

        btnHome.setOnClickListener(v -> {
            if (listener != null) listener.onHomeClicked();
            dismiss(); // ปิด Dialog
        });

        btnRestart.setOnClickListener(v -> {
            if (listener != null) listener.onRestartClicked();
            dismiss(); // ปิด Dialog
        });

        return view;
    }

    // [Event-driven Programming & Multitasking] ดักจับการปิด Dialog เพื่อสั่งเล่นเกม/เวลาต่อโดยอัตโนมัติ
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (getActivity() instanceof com.example.interviewday.activities.GameStageActivity) {
            ((com.example.interviewday.activities.GameStageActivity) getActivity()).resumeGameStage();
        }
    }
}