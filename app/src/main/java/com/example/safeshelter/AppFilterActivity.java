package com.example.safeshelter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class AppFilterActivity extends AppCompatActivity {
    private Button filter_button;
    private CheckBox youtube_kids, quiz, maze, tic_tac_toe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_filter);

        setTitle("SafeShelter");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        youtube_kids = findViewById(R.id.youtubeKids);
        quiz = findViewById(R.id.quiz);
        maze = findViewById(R.id.maze);
        tic_tac_toe = findViewById(R.id.tic_tac_toe);

        updateBoxes();

        filter_button = findViewById(R.id.button_filter);
        filter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Guarda as variaveis boolean de que apps querem ser filtradas para serem acedidas no MainMenuActivity
                SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                if(youtube_kids.isChecked()){
                    editor.putBoolean("YoutubeKids", true).apply();
                } else {
                    editor.putBoolean("YoutubeKids", false).apply();
                }

                if(quiz.isChecked()){
                    editor.putBoolean("Quiz", true).apply();
                } else {
                    editor.putBoolean("Quiz", false).apply();
                }

                if(maze.isChecked()){
                    editor.putBoolean("Maze", true).apply();
                } else {
                    editor.putBoolean("Maze", false).apply();
                }

                if(tic_tac_toe.isChecked()){
                    editor.putBoolean("TicTacToe", true).apply();
                } else {
                    editor.putBoolean("TicTacToe", false).apply();
                }

                Intent intent = new Intent(AppFilterActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateBoxes(){
        boolean isYoutubeKidsSelected, isQuizSelected, isMazeSelected, isTicTacToeSelected;

        //Da update as boxes com as escolhas atuais
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        isYoutubeKidsSelected = prefs.getBoolean("YoutubeKids", false);
        isQuizSelected = prefs.getBoolean("Quiz", false);
        isMazeSelected = prefs.getBoolean("Maze", false);
        isTicTacToeSelected = prefs.getBoolean("TicTacToe", false);

        if(isYoutubeKidsSelected){
            youtube_kids.setChecked(true);
        } else {
            youtube_kids.setChecked(false);
        }

        if(isQuizSelected){
            quiz.setChecked(true);
        } else {
            quiz.setChecked(false);
        }

        if(isMazeSelected){
            maze.setChecked(true);
        } else {
            maze.setChecked(false);
        }

        if(isTicTacToeSelected){
            tic_tac_toe.setChecked(true);
        } else {
            tic_tac_toe.setChecked(false);
        }
    }
}
