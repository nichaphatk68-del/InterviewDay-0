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
import android.widget.SeekBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.interviewday.R;
import com.example.interviewday.controllers.BackgroundMusicManager;
import com.example.interviewday.controllers.SettingsController;

/*
 * คลาส SettingsDialog
 * - หน้าต่างป๊อปอัพ (Dialog) สำหรับตั้งค่าเสียงในเกม ทั้งเสียงเอฟเฟกต์ (SFX) และเพลงประกอบ (BGM)
 * - [OOP Concept: Inheritance] สืบทอดมาจาก BaseDialog
 * - [MVC Pattern: View] ทำหน้าที่แสดงผล UI หน้าตั้งค่า และรับการตอบสนองจากผู้ใช้
 * - [Event-driven] ทำงานเมื่อผู้ใช้กดปุ่ม หรือเลื่อนแถบปรับระดับเสียง (SeekBar)
 * - [Multitasking] สั่งปรับเสียงเพลง BGM ในฉากหลังทันทีโดยไม่ขัดจังหวะ UI
 */
public class SettingsDialog extends BaseDialog {

    /**
     * [OOP Concept: Abstraction / Interface]
     * อินเทอร์เฟซสำหรับส่งข้อมูลผลการตั้งค่าระดับเสียง BGM/SFX กลับไปยัง Activity หรือ Fragment ที่เปิด Dialog นี้
     */
    public interface SettingsDialogListener {
        // เรียกเมื่อกดปุ่มตกลง (บันทึกค่า) พร้อมส่งค่าสถานะ SFX และ BGM กลับไป
        void onSaveSettings(boolean sfxOn, int sfxVol, boolean bgmOn, int bgmVol);
        // เรียกเมื่อกดปุ่มยกเลิก
        void onCancelSettings();
    }

    // ตัวแปรเก็บ Listener สำหรับส่งเหตุการณ์กลับไปยัง Activity ที่เรียกใช้
    private SettingsDialogListener listener;

    // [MVC Pattern: Controller] ตัวกลางควบคุมสภาวะและจัดการตรรกะการตั้งค่า
    private SettingsController settingsController;

    // ส่วนประกอบของหน้าจอ (UI Components)
    private ImageButton btnSfxOn;   // ปุ่มเปิดเสียงเอฟเฟกต์ (SFX)
    private ImageButton btnSfxOff;  // ปุ่มปิดเสียงเอฟเฟกต์ (SFX)
    private ImageButton btnBgmOn;   // ปุ่มเปิดเพลงประกอบ (BGM)
    private ImageButton btnBgmOff;  // ปุ่มปิดเพลงประกอบ (BGM)
    private SeekBar seekbarSfx;     // แถบสไลด์ปรับระดับเสียง SFX
    private SeekBar seekbarBgm;     // แถบสไลด์ปรับระดับเสียง BGM

    /**
     * Static Factory Method สำหรับสร้าง Instance ใหม่ของ SettingsDialog
     */
    public static SettingsDialog newInstance() {
        return new SettingsDialog();
    }

    /**
     * เรียกเมื่อเริ่มต้นสร้าง Dialog
     * ใช้เตรียมวัตถุที่ไม่เกี่ยวกับ UI เช่น SettingsController
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ถ้ายังไม่มี Controller ให้สร้างขึ้นมาใหม่
        if (settingsController == null) {
            settingsController = new SettingsController();
        }
    }

    /**
     * กำหนด Listener เพื่อรอรับ Event จาก Dialog นี้ (เช่น จาก Activity หลัก)
     */
    public void setListener(SettingsDialogListener listener) {
        this.listener = listener;
    }

    /**
     * เรียกเมื่อสร้าง UI ของ Dialog
     * ทำหน้าที่ผูก View, กำหนดค่าเริ่มต้น และตั้งค่า Event Listener บนปุ่มและ SeekBar ต่างๆ
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // ดึง Layout XML มาแสดงผล
        View view = inflater.inflate(R.layout.activity_settings_dialog, container, false);

        // ปรับแต่งให้พื้นหลังป๊อปอัพโปร่งแสง และไม่มีแถบ Title ด้านบน
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        // ผูกตัวแปรเข้ากับ View ใน Layout XML
        btnSfxOn = view.findViewById(R.id.btnSfxOn);
        btnSfxOff = view.findViewById(R.id.btnSfxOff);
        btnBgmOn = view.findViewById(R.id.btnBgmOn);
        btnBgmOff = view.findViewById(R.id.btnBgmOff);
        seekbarSfx = view.findViewById(R.id.seekbarSfx);
        seekbarBgm = view.findViewById(R.id.seekbarBgm);
        ImageButton btnCancel = view.findViewById(R.id.btnCancel);
        ImageButton btnOk = view.findViewById(R.id.btnOk);

        // โหลดค่าความดังเสียงและสถานะเริ่มต้นจาก Controller มาแสดงบน UI
        if (settingsController != null) {
            seekbarSfx.setProgress(settingsController.getSfxVolume());
            seekbarBgm.setProgress(settingsController.getBgmVolume());
            updateSfxButtonsUI(settingsController.isSfxOn());
            updateBgmButtonsUI(settingsController.isBgmOn());
        }

        // --- สลับสถานะเปิด/ปิด SFX ---
        btnSfxOn.setOnClickListener(v -> {
            if (settingsController != null) settingsController.setSfxOn(true);
            updateSfxButtonsUI(true); // ปรับความโปร่งใสของปุ่มให้เห็นว่าเปิดอยู่
            applyAndSave();           // บันทึกและปรับปรุงเสียงทันที
        });
        btnSfxOff.setOnClickListener(v -> {
            if (settingsController != null) settingsController.setSfxOn(false);
            updateSfxButtonsUI(false); // ปรับความโปร่งใสของปุ่มให้เห็นว่าปิดอยู่
            applyAndSave();            // บันทึกและปรับปรุงเสียงทันที
        });

        // --- สลับสถานะเปิด/ปิด BGM ---
        btnBgmOn.setOnClickListener(v -> {
            if (settingsController != null) settingsController.setBgmOn(true);
            updateBgmButtonsUI(true); // ปรับความโปร่งใสของปุ่ม BGM ให้เห็นว่าเปิดอยู่
            applyAndSave();           // บันทึกและปรับปรุงเสียงเพลง BGM ทันที
        });
        btnBgmOff.setOnClickListener(v -> {
            if (settingsController != null) settingsController.setBgmOn(false);
            updateBgmButtonsUI(false); // ปรับความโปร่งใสของปุ่ม BGM ให้เห็นว่าปิดอยู่
            applyAndSave();            // บันทึกและปรับปรุงเสียงเพลง BGM ทันที
        });

        // --- ติดตามการเลื่อนแถบระดับเสียง SFX ---
        seekbarSfx.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // บันทึกค่าใหม่ทันทีเมื่อผู้ใช้เลื่อนแถบ
                if (fromUser) applyAndSave();
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // --- ติดตามการเลื่อนแถบระดับเสียง BGM ---
        seekbarBgm.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // บันทึกค่าใหม่และปรับระดับเสียงเพลงทันทีเมื่อผู้ใช้เลื่อนแถบ
                if (fromUser) applyAndSave();
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // --- ปุ่มยกเลิก ---
        btnCancel.setOnClickListener(v -> {
            applyAndSave();
            if (listener != null) listener.onCancelSettings();
            dismiss(); // ปิด Dialog
        });

        // --- ปุ่มตกลง/บันทึกการตั้งค่า ---
        btnOk.setOnClickListener(v -> {
            applyAndSave();
            if (listener != null) {
                boolean sfxOn = settingsController != null && settingsController.isSfxOn();
                boolean bgmOn = settingsController != null && settingsController.isBgmOn();
                int sfxVol = seekbarSfx.getProgress();
                int bgmVol = seekbarBgm.getProgress();
                // ส่งข้อมูลการตั้งค่าล่าสุดกลับไปยัง Activity
                listener.onSaveSettings(sfxOn, sfxVol, bgmOn, bgmVol);
            }
            dismiss(); // ปิด Dialog
        });

        return view;
    }

    /**
     * อัปเดตความโปร่งใส (Alpha) ของปุ่ม SFX On/Off
     * ปุ่มที่ถูกเลือกจะมี Alpha = 1.0 (สว่างชัด) และปุ่มที่ไม่เลือกจะมี Alpha = 0.4 (จางลง)
     */
    private void updateSfxButtonsUI(boolean isOn) {
        if (btnSfxOn != null && btnSfxOff != null) {
            btnSfxOn.setAlpha(isOn ? 1.0f : 0.4f);
            btnSfxOff.setAlpha(isOn ? 0.4f : 1.0f);
        }
    }

    /**
     * อัปเดตความโปร่งใส (Alpha) ของปุ่ม BGM On/Off
     * ปุ่มที่ถูกเลือกจะมี Alpha = 1.0 (สว่างชัด) และปุ่มที่ไม่เลือกจะมี Alpha = 0.4 (จางลง)
     */
    private void updateBgmButtonsUI(boolean isOn) {
        if (btnBgmOn != null && btnBgmOff != null) {
            btnBgmOn.setAlpha(isOn ? 1.0f : 0.4f);
            btnBgmOff.setAlpha(isOn ? 0.4f : 1.0f);
        }
    }

    /**
     * อ่านค่าจาก UI ไปบันทึกลง Controller
     * พร้อมอัปเดตเสียงเพลง BGM ใน BackgroundMusicManager ให้มีผลทันทีแบบ Real-time
     */
    private void applyAndSave() {
        if (settingsController != null && seekbarSfx != null && seekbarBgm != null) {
            boolean sfxOn = settingsController.isSfxOn();
            boolean bgmOn = settingsController.isBgmOn();
            int sfxVol = seekbarSfx.getProgress();
            int bgmVol = seekbarBgm.getProgress();

            // บันทึกค่าการตั้งค่า
            settingsController.saveSettings(sfxOn, sfxVol, bgmOn, bgmVol);
            // ปรับเสียง BGM ทันที
            BackgroundMusicManager.setBgmState(bgmOn, bgmVol);
        }
    }

    /**
     * เรียกเมื่อ Dialog ถูกปิดลง (เช่น การแตะนอกพื้นที่หรือกดปุ่ม Back)
     * เพื่อให้แน่ใจว่าค่าการตั้งค่าล่าสุดถูกบันทึกไว้เสมอ
     */
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        applyAndSave();
    }

}
