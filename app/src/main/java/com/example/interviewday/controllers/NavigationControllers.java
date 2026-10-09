package com.example.interviewday.controllers;

import android.content.Context;
import android.content.Intent;
import android.app.Activity;
import com.example.interviewday.activities.MainMenuActivity;
import com.example.interviewday.activities.StageSelectionActivity;
import com.example.interviewday.activities.GameStageActivity;

public class NavigationControllers {
    // --Attributes--
    private String currentScreen;
    private String previousScreen;
    private Context context;

    // --Constructor--
    public NavigationControllers(){
        this.currentScreen = "MainMenu";
        this.previousScreen = "";
    }

    public NavigationControllers(Context context){
        this.context = context;
        this.currentScreen = "MainMenu";
        this.previousScreen = "";
    }

    // --Methods--
    public void goToMainMenu(){
        this.previousScreen = currentScreen;
        this.currentScreen = "MainMenu";

        if (context != null) {
            Intent intent = new Intent(context, MainMenuActivity.class);
            context.startActivity(intent);
        }
    }

    public void goToStageSelect(){
        this.previousScreen = currentScreen;
        this.currentScreen = "StageSelect";

        if (context != null){
            Intent intent = new Intent(context, StageSelectionActivity.class);
            context.startActivity(intent);
        }
    }

    public void goToGameStage(int stageNumber){
        this.previousScreen = currentScreen;
        this.currentScreen = "GameStage";

        if (context != null){
            Intent intent = new Intent(context, GameStageActivity.class);
            intent.putExtra("STAGE_NUMBER", stageNumber);
            context.startActivity(intent);
        }
    }

    public void goBack() {
        if (context instanceof Activity){
            ((Activity) context).finish();
        }
    }

    // --Getters & Setters--
    public String getCurrentScreen(){return currentScreen;}
    public void setCurrentScreen(String currentScreen){this.currentScreen = currentScreen;}
    public String getPreviousScreen(){return previousScreen;}
    public void setPreviousScreen(String previousScreen){this.previousScreen = previousScreen;}
    public Context getContext() {return context;}
    public void setContext(Context context) {this.context = context;}
}
