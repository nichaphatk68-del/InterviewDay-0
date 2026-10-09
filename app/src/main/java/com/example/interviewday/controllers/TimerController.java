package com.example.interviewday.controllers;

import android.os.CountDownTimer;

public class TimerController {

    public interface OnTimerTickListener {
        void onTick(String formattedTime);
        void onFinish();
    }

    // --Attributes--
    private int timeLimit;
    private int remainingTime;
    private boolean isRunning;
    private CountDownTimer countDownTimer;
    private OnTimerTickListener timerTickListener;

    // --Constructor--
    public TimerController(){
        this.timeLimit = 360; // 360 วินาที (6 นาที)
        this.remainingTime = timeLimit;
        this.isRunning = false;
    }

    public void setOnTimerTickListener(OnTimerTickListener listener) {
        this.timerTickListener = listener;
    }

    // --Methods--
    public void startTimer(){
        if (isRunning) return;

        isRunning = true;
        countDownTimer = new CountDownTimer(remainingTime * 1000L, 1000) {
            @Override
            public void onFinish() {
                remainingTime = 0;
                isRunning = false;
                if (timerTickListener != null) {
                    timerTickListener.onFinish();
                }
            }
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime = (int) (millisUntilFinished / 1000);
                updateTimer();
                if (timerTickListener != null) {
                    timerTickListener.onTick(getFormattedTime());
                }
            }
        }.start();
    }

    public void pauseTimer(){
        if (countDownTimer != null){
            countDownTimer.cancel();
        }
        isRunning = false;
    }

    public void resumeTimer(){
        startTimer();
    }

    public void stopTimer(){
        if (countDownTimer != null){
            countDownTimer.cancel();
        }
        isRunning = false;
    }

    public void resetTimer(){
        stopTimer();
        remainingTime = timeLimit;
    }

    public void updateTimer(){
    }

    public boolean checkTimeOut(){
        return remainingTime <= 0;
    }

    // --Getters & Setters--
    public int getTimeLimit(){return timeLimit;}
    public void setTimeLimit(int timeLimit){
        this.timeLimit = timeLimit;
        this.remainingTime = timeLimit;
    }
    public int getRemainingTime() {return remainingTime;}
    public void setRemainingTime(int remainingTime) {this.remainingTime = remainingTime;}
    public boolean isRunning(){return isRunning;}

    public String getFormattedTime() {
        int minutes = remainingTime / 60;
        int seconds = remainingTime % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
