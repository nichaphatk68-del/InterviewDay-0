package com.example.interviewday.dialogs;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * คลาสพื้นฐานสำหรับสร้างหน้าต่างป๊อปอัพเด้งซ้อน
 */
public class BaseDialog extends DialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ใช้ Theme แบบ Dialog ไม่ใช่ Fullscreen
        setStyle(
                DialogFragment.STYLE_NO_TITLE,
                android.R.style.Theme_DeviceDefault_Dialog_NoActionBar
        );
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getDialog() == null || getDialog().getWindow() == null) {
            return;
        }

        Window window = getDialog().getWindow();

        // คำนวณขนาดความกว้างที่เหมาะสมสำหรับ Dialog (460dp หรือไม่เกิน 85% ของหน้าจอ)
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int desiredWidth = (int) (460 * metrics.density);
        int maxWidth = (int) (metrics.widthPixels * 0.85f);
        int width = Math.min(desiredWidth, maxWidth);

        // กำหนดความกว้างให้พอดีกับปุ่ม (ไม่อัดแน่นจนซ้อนกัน) และความสูงปรับตามเนื้อหา (WRAP_CONTENT)
        window.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);

        // จัดให้อยู่ตรงกลางจอ
        window.setGravity(Gravity.CENTER);

        // ทำพื้นหลังหน้าต่างให้โปร่งใส
        window.setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT)
        );

        // ปรับความมืดของฉากด้านหลัง
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 0.6f;
        window.setAttributes(params);
        window.addFlags(
                WindowManager.LayoutParams.FLAG_DIM_BEHIND
        );
    }
}
