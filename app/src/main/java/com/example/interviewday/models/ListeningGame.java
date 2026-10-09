package com.example.interviewday.models;

/**
 * คลาสลูก: มินิเกมฝึกฟังภาษาอังกฤษและตรวจสอบคำตอบจากการฟัง
 * OOP: Inheritance + Polymorphism (override checkAnswer() ด้วยวิธีตรวจแบบฟัง)
 */
public class ListeningGame extends MiniGame {

    private String correctTranscript;

    public ListeningGame(int gameID, String gameName, String correctTranscript) {
        super(gameID, gameName);
        this.correctTranscript = correctTranscript;
    }

    public String getCorrectTranscript() {
        return correctTranscript;
    }

    public void setCorrectTranscript(String correctTranscript) {
        this.correctTranscript = correctTranscript;
    }

    @Override
    public boolean checkAnswer(String playerInput) {
        if (playerInput == null || correctTranscript == null) return false;
        boolean isCorrect = playerInput.trim().equalsIgnoreCase(correctTranscript.trim());
        System.out.println("[Listening] ตรวจคำตอบจากการฟัง: " + (isCorrect ? "ถูกต้อง" : "ผิด"));
        return isCorrect;
    }
}
