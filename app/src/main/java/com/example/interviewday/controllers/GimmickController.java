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

    private int currentStage;
    private int currentQuestion;

    private final List<Question> questionList;
    private final AudioController audioController;
    private final PronunciationController pronunciationController;

    public GimmickController(int currentStage, int currentQuestion) {
        this.currentStage = currentStage;
        this.currentQuestion = currentQuestion;
        this.audioController = new AudioController();
        this.pronunciationController = new PronunciationController();
        this.questionList = new ArrayList<>();
        loadQuestions();
    }

    private void loadQuestions() {
        questionList.clear();

        // --- ชุดโจทย์ด่านที่ 1 (OOP Concepts) ---
        questionList.add(new Question(
                1, 1, 0,
                "Level 1: Listen to the question, then press the speak button to answer.",
                R.drawable.ic_logo_1,
                "What is the OOP concept that hides internal details?",
                "Encapsulation",
                "Listening",
                R.raw.level1_1,
                false
        ));
        questionList.add(new Question(
                2, 1, 1,
                "Level 1: Listen to the question, then press the speak button to answer.",
                R.drawable.ic_logo_2,
                "What is the mechanism where a class acquires properties of another class?",
                "Inheritance",
                "Listening",
                R.raw.level1_2,
                false
        ));
        questionList.add(new Question(
                3, 1, 2,
                "Level 1: Listen to the question, then press the speak button to answer.",
                R.drawable.ic_logo_3,
                "What concept allows objects to take many forms?",
                "Polymorphism",
                "Listening",
                R.raw.level1_3,
                false
        ));

        // --- ชุดโจทย์ด่านที่ 2 (Hardware Tools) ---
        questionList.add(new Question(
                4, 2, 0,
                "Level 2: Listen to the question, then press the speak button to answer.",
                R.drawable.ic_logo_1,
                "What hardware component is a compact integrated circuit designed to govern a specific operation in an embedded system?",
                "Microcontroller",
                "Listening",
                R.raw.level2_1,
                false
        ));
        questionList.add(new Question(
                5, 2, 1,
                "Level 2: Listen to the question, then press the speak button to answer.",
                R.drawable.ic_logo_2,
                "What electronic test instrument allows observation of varying signal voltages?",
                "Oscilloscope",
                "Listening",
                R.raw.level2_2,
                false
        ));
        questionList.add(new Question(
                6, 2, 2,
                "Level 2: Listen to the question, then press the speak button to answer.",
                R.drawable.ic_logo_3,
                "What electronic measuring instrument combines several measurement functions in one unit?",
                "Multimeter",
                "Listening",
                R.raw.level2_3,
                false
        ));

        // --- ชุดโจทย์ด่านที่ 3 (Interview Summary) ---
        questionList.add(new Question(
                7, 3, 0,
                "Level 3: Listen to the question, then press the speak button to answer.",
                R.drawable.ic_logo_1,
                "Tell me about yourself and your background.",
                "I am a Computer Engineering graduate passionate about software development and system architecture",
                "Listening",
                R.raw.level3_1,
                true
        ));
        questionList.add(new Question(
                8, 3, 1,
                "Level 3: Listen to the question, then press the speak button to answer.",
                R.drawable.ic_logo_2,
                "What is your greatest strength?",
                "my greatest strength is my problem solving ability and quick adaptability to new technologies",
                "Listening",
                R.raw.level3_2,
                true
        ));
        questionList.add(new Question(
                9, 3, 2,
                "Level 3: Listen to the question, then press the speak button to answer.",
                R.drawable.ic_logo_3,
                "What are your career goals or why do you want to work here?",
                "I want to apply my technical skills to build scalable software solutions and learn from expert engineers",
                "Listening",
                R.raw.level3_3,
                true
        ));
    }

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

    public String getInstructionText() {
        return getCurrentQuestionModel().getInstructionText();
    }

    public int getBadgeResId() {
        return getCurrentQuestionModel().getBadgeResId();
    }

    public String getTargetSentence() {
        return getCurrentQuestionModel().getCorrectAnswer();
    }

    public String getVocabularyForCurrentQuestion() {
        Question q = getCurrentQuestionModel();
        if (q == null) return "";
        String answer = q.getCorrectAnswer();
        if ("Encapsulation".equalsIgnoreCase(answer)) {
            return "1. Encapsulation - การซ่อนรายละเอียดภายใน";
        } else if ("Inheritance".equalsIgnoreCase(answer)) {
            return "2. Inheritance - การสืบทอดคุณสมบัติ";
        } else if ("Polymorphism".equalsIgnoreCase(answer)) {
            return "3. Polymorphism - การมีได้หลายรูปแบบ";
        } else if ("Microcontroller".equalsIgnoreCase(answer)) {
            return "4. Microcontroller - ไมโครคอนโทรลเลอร์";
        } else if ("Oscilloscope".equalsIgnoreCase(answer)) {
            return "5. Oscilloscope - ออสซิลโลสโคป";
        } else if ("Multimeter".equalsIgnoreCase(answer)) {
            return "6. Multimeter - มัลติมิเตอร์";
        } else if (answer.startsWith("I am a Computer Engineering graduate")) {
            return "7. Self Introduction - " + answer;
        } else if (answer.startsWith("My greatest strength")) {
            return "8. Greatest Strength - " + answer;
        } else if (answer.startsWith("I want to apply my technical skills")) {
            return "9. Career Goals - " + answer;
        }
        return (q.getQuestionIndex() + 1) + ". " + answer;
    }

    public boolean isNotepadVisible() {
        return getCurrentQuestionModel().isNotepadVisible();
    }

    public void playQuestionAudio(Context context) {
        int resId = getCurrentQuestionModel().getAudioResId();
        if (resId != 0 && audioController != null) {
            audioController.playSentenceAudio(context, resId);
        }
    }

    public boolean checkAnswer(String spokenText) {
        String targetWord = getTargetSentence();
        pronunciationController.setTargetSentence(targetWord);
        pronunciationController.recognizeSpeech(spokenText);
        boolean isCorrect = pronunciationController.compareSentence();
        if (isCorrect) {
            NotebookController.addUnlockedVocabulary(getVocabularyForCurrentQuestion());
        }
        return isCorrect;
    }

    public boolean nextQuestion() {
        if (currentQuestion < 2) {
            currentQuestion++;
            return true;
        } else {
            return false;
        }
    }


    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public void setStageAndQuestion(int stage, int question) {
        this.currentStage = stage;
        this.currentQuestion = question;
    }
}
