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
import androidx.fragment.app.DialogFragment;
import com.example.interviewday.R;

// 1. สืบทอดมาจาก DialogFragment เพื่อให้จัดการ Lifecycle และการแสดงผลโปร่งแสงได้ดี
public class PauseDialog extends BaseDialog {

    // 2. สร้าง Interface (Contract) เพื่อส่ง Event การกดปุ่มกลับไปหา GameStageActivity
    public interface PauseDialogListener {
        void onContinueClicked();
        void onOpenSettingsClicked();
        void onHomeClicked();
        void onRestartClicked();
    }

    private PauseDialogListener listener;
    private String remainingTime = "00:00";

    // 3. Factory Method (newInstance) สำหรับใช้สร้าง Object และรับส่งข้อมูลเวลา
    public static PauseDialog newInstance(String remainingTime) {
        PauseDialog dialog = new PauseDialog();
        Bundle args = new Bundle();
        args.putString("REMAINING_TIME", remainingTime);
        dialog.setArguments(args);
        return dialog;
    }

    // 4. เมธอดสำหรับรับตัวดักจับเหตุการณ์ (Listener) จาก Activity
    public void setListener(PauseDialogListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // ดึงไฟล์ XML layout (dialog_pause.xml) มาแสดงผล
        View view = inflater.inflate(R.layout.activity_pause_dialog, container, false);

        // ปรับแต่งขอบ Dialog ให้พื้นหลังโปร่งแสง (ไม่ติดกรอบสีขาวสี่เหลี่ยมของระบบ Android)
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

        // 5. ดักจับการกดปุ่มต่างๆ
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

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (getActivity() instanceof com.example.interviewday.activities.GameStageActivity) {
            ((com.example.interviewday.activities.GameStageActivity) getActivity()).resumeGameStage();
        }
    }
}