package com.example.interviewday.models;

public class Question {

    // ===== Attributes =====
    private int questionId;
    private int stageNumber;
    private int questionIndex;
    private String instructionText;
    private int badgeResId;
    private String questionText;
    private String correctAnswer;
    private String questionType;
    private String audioFile;
    private int audioResId;
    private boolean notepadVisible;

    // ===== Constructors =====
    public Question(
            int questionId,
            String questionText,
            String correctAnswer,
            String questionType,
            String audioFile) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.questionType = questionType;
        this.audioFile = audioFile;
    }

    public Question(
            int questionId,
            int stageNumber,
            int questionIndex,
            String instructionText,
            int badgeResId,
            String questionText,
            String correctAnswer,
            String questionType,
            int audioResId,
            boolean notepadVisible) {
        this.questionId = questionId;
        this.stageNumber = stageNumber;
        this.questionIndex = questionIndex;
        this.instructionText = instructionText;
        this.badgeResId = badgeResId;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.questionType = questionType;
        this.audioResId = audioResId;
        this.notepadVisible = notepadVisible;
    }

    // ===== Getters & Setters =====

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getStageNumber() {
        return stageNumber;
    }

    public void setStageNumber(int stageNumber) {
        this.stageNumber = stageNumber;
    }

    public int getQuestionIndex() {
        return questionIndex;
    }

    public void setQuestionIndex(int questionIndex) {
        this.questionIndex = questionIndex;
    }

    public String getInstructionText() {
        return instructionText;
    }

    public void setInstructionText(String instructionText) {
        this.instructionText = instructionText;
    }

    public int getBadgeResId() {
        return badgeResId;
    }

    public void setBadgeResId(int badgeResId) {
        this.badgeResId = badgeResId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(String audioFile) {
        this.audioFile = audioFile;
    }

    public int getAudioResId() {
        return audioResId;
    }

    public void setAudioResId(int audioResId) {
        this.audioResId = audioResId;
    }

    public boolean isNotepadVisible() {
        return notepadVisible;
    }

    public void setNotepadVisible(boolean notepadVisible) {
        this.notepadVisible = notepadVisible;
    }

    // ===== Check Answer =====

    public boolean checkAnswer(String answer) {
        if (answer == null) {
            return false;
        }
        return correctAnswer.equalsIgnoreCase(answer.trim());
    }

    // ===== Check Question Type =====

    public boolean isListeningQuestion() {
        return questionType != null && questionType.equalsIgnoreCase("Listening");
    }

    public boolean isSpeakingQuestion() {
        return questionType != null && questionType.equalsIgnoreCase("Speaking");
    }
}
