package com.example.interviewday.controllers;

public class PronunciationController {
    private String targetSentence;
    private String spokenText;
    private boolean isCorrect;

    public PronunciationController(){
        this.targetSentence = "";
        this.spokenText = "";
        this.isCorrect = false;
    }

    public void setTargetSentence(String targetSentence){
        this.targetSentence = targetSentence;
    }

    public void recognizeSpeech(String speechInput){
        if (speechInput != null){
            this.spokenText = speechInput.trim();
        }
    }

    public boolean compareSentence(){
        if (targetSentence == null || spokenText == null) return false;

        String cleanTarget = targetSentence.replaceAll("[^a-zA-Z0-9 ]","").trim();
        String cleanSpoken = spokenText.replaceAll("[^a-zA-Z0-9 ]", "").trim();

        this.isCorrect = cleanTarget.equalsIgnoreCase(cleanSpoken);
        return this.isCorrect;
    }
}
