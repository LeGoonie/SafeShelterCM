package com.miniapps.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.safeshelter.MainMenuActivity;
import com.example.safeshelter.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainQuizActivity extends AppCompatActivity {

    public ImageView play_image, sair_image;

    boolean currentFocus;
    boolean isPaused;
    Handler collapseNotificationHandler;

    //Criação dos botões do menu
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        removeTitleBar();
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        currentFocus = hasFocus;

        if (!hasFocus) {
            collapseNow();
        }
    }

    public void collapseNow() {
        if (collapseNotificationHandler == null) {
            collapseNotificationHandler = new Handler();
        }

        if (!currentFocus && !isPaused) {
            collapseNotificationHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    Object statusBarService = getSystemService("statusbar");
                    Class<?> statusBarManager = null;

                    try {
                        statusBarManager = Class.forName("android.app.StatusBarManager");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    Method collapseStatusBar = null;

                    try {
                        if (Build.VERSION.SDK_INT > 16) {
                            collapseStatusBar = statusBarManager .getMethod("collapsePanels");
                        } else {
                            collapseStatusBar = statusBarManager .getMethod("collapse");
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }

                    collapseStatusBar.setAccessible(true);

                    try {
                        collapseStatusBar.invoke(statusBarService);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                    if (!currentFocus && !isPaused) {
                        collapseNotificationHandler.postDelayed(this, 100L);
                    }

                }
            }, 1L);
        }
    }

    // Pausa da app
    @Override
    protected void onPause() {
        super.onPause();
        isPaused = true;

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);

        activityManager.moveTaskToFront(getTaskId(), 0);
    }

    // Continuação app
    @Override
    protected void onResume() {
        super.onResume();
        isPaused = false;
    }

    // Remove toolbar
    protected void removeTitleBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        //Remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Do nothing or catch the keys you want to block
        return false;
    }
}
