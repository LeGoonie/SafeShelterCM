package com.miniapps.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.safeshelter.MainMenuActivity;
import com.example.safeshelter.R;

public class MainQuizActivity extends AppCompatActivity {

    public ImageView play_image, sair_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_quiz);

        play_image = (ImageView) findViewById(R.id.playButton);
        play_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainQuizActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });

        sair_image = (ImageView) findViewById(R.id.quitButton);
        sair_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainQuizActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });
    }
}
