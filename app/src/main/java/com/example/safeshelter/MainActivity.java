package com.example.safeshelter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;

import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIMEOUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("SafeShelter");

        setContentView(R.layout.activity_main);
        //Mantém o splash screen durante 4 segundos
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(MainActivity.this, MainMenuActivity.class));
                    finish();
                }
            }
        }, SPLASH_TIMEOUT);
    }
}
