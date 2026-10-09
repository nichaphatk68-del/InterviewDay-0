package com.example.interviewday.dialogs;

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
import com.example.interviewday.controllers.SettingsController;

/**
 * คลาส SettingsDialog
 * หน้าที่: หน้าต่างป๊อปอัพปรับตั้งค่าเสียง BGM / SFX
 * หลักการ MVC: ส่งผ่านข้อมูลและบันทึกสภาวะตั้งค่าไปยัง Setting Model ผ่าน SettingsController
 */
public class SettingsDialog extends BaseDialog {

    // ===== Interface Contract =====
    public interface SettingsDialogListener {
        void onSaveSettings(boolean sfxOn, int sfxVol, boolean bgmOn, int bgmVol);
        void onCancelSettings();
    }

    private SettingsDialogListener listener;
    private SettingsController settingsController; // Controller จัดการการตั้งค่า

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
        ImageButton btnSfxOn = view.findViewById(R.id.btnSfxOn);
        ImageButton btnSfxOff = view.findViewById(R.id.btnSfxOff);
        ImageButton btnBgmOn = view.findViewById(R.id.btnBgmOn);
        ImageButton btnBgmOff = view.findViewById(R.id.btnBgmOff);
        SeekBar seekbarSfx = view.findViewById(R.id.seekbarSfx);
        SeekBar seekbarBgm = view.findViewById(R.id.seekbarBgm);
        ImageButton btnCancel = view.findViewById(R.id.btnCancel);
        ImageButton btnOk = view.findViewById(R.id.btnOk);

        // โหลดค่าความดังเสียงเริ่มต้นจาก Controller
        if (settingsController != null) {
            seekbarSfx.setProgress(settingsController.getSfxVolume());
            seekbarBgm.setProgress(settingsController.getBgmVolume());
        }

        // สลับสถานะเปิด/ปิด SFX
        btnSfxOn.setOnClickListener(v -> {
            if (settingsController != null) settingsController.setSfxOn(true);
        });
        btnSfxOff.setOnClickListener(v -> {
            if (settingsController != null) settingsController.setSfxOn(false);
        });

        // สลับสถานะเปิด/ปิด BGM
        btnBgmOn.setOnClickListener(v -> {
            if (settingsController != null) settingsController.setBgmOn(true);
        });
        btnBgmOff.setOnClickListener(v -> {
            if (settingsController != null) settingsController.setBgmOn(false);
        });

        // ปุ่มยกเลิก
        btnCancel.setOnClickListener(v -> {
            if (listener != null) listener.onCancelSettings();
            dismiss();
        });

        // ปุ่มตกลง/บันทึกการตั้งค่า
        btnOk.setOnClickListener(v -> {
            boolean sfxOn = settingsController != null && settingsController.isSfxOn();
            boolean bgmOn = settingsController != null && settingsController.isBgmOn();
            int sfxVol = seekbarSfx.getProgress();
            int bgmVol = seekbarBgm.getProgress();

            // บันทึกลง Model ผ่าน Controller
            if (settingsController != null) {
                settingsController.saveSettings(sfxOn, sfxVol, bgmOn, bgmVol);
            }

            if (listener != null) {
                listener.onSaveSettings(sfxOn, sfxVol, bgmOn, bgmVol);
            }
            dismiss();
        });

        return view;
    }

    public SettingsController getSettingsController() {
        return settingsController;
    }
}
