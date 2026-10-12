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
 * - [OOP Concept: Inheritance] สืบทอดจาก BaseDialog
 * - [MVC Pattern: View] หน้าต่างปรับตั้งค่าเสียง SFX/BGM ทำงานร่วมกับ SettingsController
 * - [Event-driven] ดักจับการกดปุ่มสลับเสียงและเลื่อน SeekBar
 * - [Multitasking] สั่งปรับเสียงเพลง BGM ในฉากหลังทันทีโดยไม่ขัดจังหวะ UI
 */
public class SettingsDialog extends BaseDialog {

    // [OOP Concept: Abstraction] Interface สัญญาส่งผ่านข้อมูลระดับเสียง BGM/SFX กลับไปยัง Activity
    public interface SettingsDialogListener {
        void onSaveSettings(boolean sfxOn, int sfxVol, boolean bgmOn, int bgmVol);
        void onCancelSettings();
    }

    private SettingsDialogListener listener;
    private SettingsController settingsController; // [MVC Pattern: Controller] ตัวกลางควบคุมสภาวะการตั้งค่า

    private ImageButton btnSfxOn;
    private ImageButton btnSfxOff;
    private ImageButton btnBgmOn;
    private ImageButton btnBgmOff;
    private SeekBar seekbarSfx;
    private SeekBar seekbarBgm;

    public static SettingsDialog newInstance() {
        return new SettingsDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (settingsController == null) {
            settingsController = new SettingsController();
        }
    }

    public void setListener(SettingsDialogListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_settings_dialog, container, false);

        // ปรับแต่งให้พื้นหลังป๊อปอัพโปร่งแสง
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        // ผูก View
        btnSfxOn = view.findViewById(R.id.btnSfxOn);
        btnSfxOff = view.findViewById(R.id.btnSfxOff);
        btnBgmOn = view.findViewById(R.id.btnBgmOn);
        btnBgmOff = view.findViewById(R.id.btnBgmOff);
        seekbarSfx = view.findViewById(R.id.seekbarSfx);
        seekbarBgm = view.findViewById(R.id.seekbarBgm);
        ImageButton btnCancel = view.findViewById(R.id.btnCancel);
        ImageButton btnOk = view.findViewById(R.id.btnOk);

        // โหลดค่าความดังเสียงเริ่มต้นจาก Controller
        if (settingsController != null) {
            seekbarSfx.setProgress(settingsController.getSfxVolume());
            seekbarBgm.setProgress(settingsController.getBgmVolume());
            updateSfxButtonsUI(settingsController.isSfxOn());
            updateBgmButtonsUI(settingsController.isBgmOn());
        }

        // สลับสถานะเปิด/ปิด SFX
        btnSfxOn.setOnClickListener(v -> {
            if (settingsController != null) settingsController.setSfxOn(true);
            updateSfxButtonsUI(true);
            applyAndSave();
        });
        btnSfxOff.setOnClickListener(v -> {
            if (settingsController != null) settingsController.setSfxOn(false);
            updateSfxButtonsUI(false);
            applyAndSave();
        });

        // สลับสถานะเปิด/ปิด BGM
        btnBgmOn.setOnClickListener(v -> {
            if (settingsController != null) settingsController.setBgmOn(true);
            updateBgmButtonsUI(true);
            applyAndSave();
        });
        btnBgmOff.setOnClickListener(v -> {
            if (settingsController != null) settingsController.setBgmOn(false);
            updateBgmButtonsUI(false);
            applyAndSave();
        });

        // ติดตามการเลื่อน SeekBar
        seekbarSfx.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) applyAndSave();
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        seekbarBgm.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) applyAndSave();
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // ปุ่มยกเลิก
        btnCancel.setOnClickListener(v -> {
            applyAndSave();
            if (listener != null) listener.onCancelSettings();
            dismiss();
        });

        // ปุ่มตกลง/บันทึกการตั้งค่า
        btnOk.setOnClickListener(v -> {
            applyAndSave();
            if (listener != null) {
                boolean sfxOn = settingsController != null && settingsController.isSfxOn();
                boolean bgmOn = settingsController != null && settingsController.isBgmOn();
                int sfxVol = seekbarSfx.getProgress();
                int bgmVol = seekbarBgm.getProgress();
                listener.onSaveSettings(sfxOn, sfxVol, bgmOn, bgmVol);
            }
            dismiss();
        });

        return view;
    }

    private void updateSfxButtonsUI(boolean isOn) {
        if (btnSfxOn != null && btnSfxOff != null) {
            btnSfxOn.setAlpha(isOn ? 1.0f : 0.4f);
            btnSfxOff.setAlpha(isOn ? 0.4f : 1.0f);
        }
    }

    private void updateBgmButtonsUI(boolean isOn) {
        if (btnBgmOn != null && btnBgmOff != null) {
            btnBgmOn.setAlpha(isOn ? 1.0f : 0.4f);
            btnBgmOff.setAlpha(isOn ? 0.4f : 1.0f);
        }
    }

    private void applyAndSave() {
        if (settingsController != null && seekbarSfx != null && seekbarBgm != null) {
            boolean sfxOn = settingsController.isSfxOn();
            boolean bgmOn = settingsController.isBgmOn();
            int sfxVol = seekbarSfx.getProgress();
            int bgmVol = seekbarBgm.getProgress();

            settingsController.saveSettings(sfxOn, sfxVol, bgmOn, bgmVol);
            BackgroundMusicManager.setBgmState(bgmOn, bgmVol);
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        applyAndSave();
    }

    public SettingsController getSettingsController() {
        return settingsController;
    }
}
