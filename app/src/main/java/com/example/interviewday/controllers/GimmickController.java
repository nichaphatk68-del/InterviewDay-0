package com.example.interviewday.controllers;

import android.content.Context;
import com.example.interviewday.R;
import com.example.interviewday.models.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * คลาส GimmickController
 * หน้าที่: เป็นตัวกลางควบคุมลอจิกมินิเกม เชื่อมต่อระหว่าง GimmickDialog (View) กับ Question/Audio/Pronunciation (Model & Service)
 */
public class GimmickController {

    // ===== Attributes =====
    private int currentStage;                          // ด่านปัจจุบัน (1, 2, 3)
    private int currentQuestion;                       // ข้อปัจจุบัน (0, 1, 2)

    private final List<Question> questionList;         // รายการ Model เก็บโจทย์และคำตอบทั้งหมด
    private final AudioController audioController;    // Controller สำหรับจัดการเสียงอ่านโจทย์
    private final PronunciationController pronunciationController; // Controller สำหรับตรวจเสียงพูด

    // ===== Constructors =====
    public GimmickController() {
        this(1, 0);
    }

    public GimmickController(int currentStage, int currentQuestion) {
        this.currentStage = currentStage;
        this.currentQuestion = currentQuestion;
        this.audioController = new AudioController();
        this.pronunciationController = new PronunciationController();
        this.questionList = new ArrayList<>();
        loadQuestions(); // โหลดข้อมูล Model โจทย์
    }

    // โหลดชุดข้อมูลโจทย์ คำตอบ เสียงอ่าน และ UI res ของแต่ละด่านเข้าสู่ Model
    private void loadQuestions() {
        questionList.clear();

        // --- ชุดโจทย์ด่านที่ 1 (OOP Concepts) ---
        questionList.add(new Question(
                1, 1, 0,
                "ด่านที่ 1: ฟังเสียงโจทย์ แล้วกดปุ่มพูดเพื่อตอบคำถาม",
                R.drawable.ic_logo_1,
                "What is the OOP concept that hides internal details?",
                "Encapsulation",
                "Listening",
                R.raw.level1_1,
                false
        ));
        questionList.add(new Question(
                2, 1, 1,
                "ด่านที่ 1: ฟังเสียงโจทย์ แล้วกดปุ่มพูดเพื่อตอบคำถาม",
                R.drawable.ic_logo_2,
                "What is the mechanism where a class acquires properties of another class?",
                "Inheritance",
                "Listening",
                R.raw.level1_1,
                false
        ));
        questionList.add(new Question(
                3, 1, 2,
                "ด่านที่ 1: ฟังเสียงโจทย์ แล้วกดปุ่มพูดเพื่อตอบคำถาม",
                R.drawable.ic_logo_3,
                "What concept allows objects to take many forms?",
                "Polymorphism",
                "Listening",
                R.raw.level1_1,
                false
        ));

        // --- ชุดโจทย์ด่านที่ 2 (Hardware Tools) ---
        questionList.add(new Question(
                4, 2, 0,
                "ด่านที่ 2: อ่านโจทย์ แล้วกดปุ่มพูดตอบคำถามให้ถูกต้อง",
                R.drawable.ic_logo_1,
                "Read the hardware tool question and speak the correct answer.",
                "Ethernet Cable",
                "Speaking",
                0,
                false
        ));
        questionList.add(new Question(
                5, 2, 1,
                "ด่านที่ 2: อ่านโจทย์ แล้วกดปุ่มพูดตอบคำถามให้ถูกต้อง",
                R.drawable.ic_logo_2,
                "Read the hardware tool question and speak the correct answer.",
                "External HDD",
                "Speaking",
                0,
                false
        ));
        questionList.add(new Question(
                6, 2, 2,
                "ด่านที่ 2: อ่านโจทย์ แล้วกดปุ่มพูดตอบคำถามให้ถูกต้อง",
                R.drawable.ic_logo_3,
                "Read the hardware tool question and speak the correct answer.",
                "Algorithm",
                "Speaking",
                0,
                false
        ));

        // --- ชุดโจทย์ด่านที่ 3 (Interview Summary) ---
        questionList.add(new Question(
                7, 3, 0,
                "ด่านที่ 3: ดูข้อมูลสมุดโน้ตทางขวา แล้วพูดสรุปคำตอบ",
                R.drawable.ic_logo_1,
                "Summarize your software answer from the notebook.",
                "Software Engineering",
                "Speaking",
                0,
                true
        ));
        questionList.add(new Question(
                8, 3, 1,
                "ด่านที่ 3: ดูข้อมูลสมุดโน้ตทางขวา แล้วพูดสรุปคำตอบ",
                R.drawable.ic_logo_2,
                "Summarize your architecture answer from the notebook.",
                "Architecture Diagram",
                "Speaking",
                0,
                true
        ));
        questionList.add(new Question(
                9, 3, 2,
                "ด่านที่ 3: ดูข้อมูลสมุดโน้ตทางขวา แล้วพูดสรุปคำตอบ",
                R.drawable.ic_logo_3,
                "Summarize your system design answer from the notebook.",
                "System Design",
                "Speaking",
                0,
                true
        ));
    }

    // ค้นหาและดึง Question Model ประจำด่านและข้อปัจจุบัน
    public Question getCurrentQuestionModel() {
        for (Question q : questionList) {
            if (q.getStageNumber() == currentStage && q.getQuestionIndex() == currentQuestion) {
                return q;
            }
        }
        return new Question(0, currentStage, currentQuestion,
                "คำถามด่านที่ " + currentStage, R.drawable.ic_logo_1,
                "Question", "Encapsulation", "Speaking", 0, false);
    }

    // Getter ส่งข้อมูลคำสั่งสอนให้ View
    public String getInstructionText() {
        return getCurrentQuestionModel().getInstructionText();
    }

    // Getter ส่งรูปภาพ Badge ให้ View
    public int getBadgeResId() {
        return getCurrentQuestionModel().getBadgeResId();
    }

    // Getter ส่งคำตอบเฉลยภาษาอังกฤษให้ View
    public String getTargetSentence() {
        return getCurrentQuestionModel().getCorrectAnswer();
    }

    // Getter เช็กสถานะการมองเห็นกระดาษโน้ต
    public boolean isNotepadVisible() {
        return getCurrentQuestionModel().isNotepadVisible();
    }

    // สั่งการ AudioController ให้เล่นเสียงโจทย์ประจำข้อ
    public void playQuestionAudio(Context context) {
        int resId = getCurrentQuestionModel().getAudioResId();
        if (resId != 0 && audioController != null) {
            audioController.playSentenceAudio(context, resId);
        }
    }

    // ส่งข้อความที่พูดเข้า PronunciationController เพื่อตรวจเทียบคำตอบ
    public boolean checkAnswer(String spokenText) {
        String targetWord = getTargetSentence();
        pronunciationController.setTargetSentence(targetWord);
        pronunciationController.recognizeSpeech(spokenText);
        return pronunciationController.compareSentence();
    }

    // เปลี่ยนข้อคำถาม ถัดไป (0 -> 1 -> 2) หรือเลื่อนด่าน
    public boolean nextQuestion() {
        if (currentQuestion < 2) {
            currentQuestion++;
            return true;
        } else {
            if (currentStage < 3) {
                currentStage++;
                currentQuestion = 0;
                return true;
            } else {
                return false; // เล่นครบ 3 ด่านแล้ว
            }
        }
    }

    // ===== Getters & Setters =====
    public int getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(int currentStage) {
        this.currentStage = currentStage;
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(int currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public void setStageAndQuestion(int stage, int question) {
        this.currentStage = stage;
        this.currentQuestion = question;
    }

    public AudioController getAudioController() {
        return audioController;
    }

    public PronunciationController getPronunciationController() {
        return pronunciationController;
    }
}
