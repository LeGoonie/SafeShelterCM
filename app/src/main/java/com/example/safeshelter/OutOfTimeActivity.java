package com.example.safeshelter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class OutOfTimeActivity extends AppCompatActivity {
    private TextView mTextView;
    private EditText mEditTextInput;
    private TextView mTextViewCountDown;
    private Button mButtonSet;
    private Button mButtonStartPause;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_of_time);

        setTitle("SafeShelter");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mTextView = findViewById(R.id.title);
        mEditTextInput = findViewById(R.id.edit_text_input);
        mTextViewCountDown = findViewById(R.id.text_view_countdown);

        mButtonSet = findViewById(R.id.button_set);
        mButtonStartPause = findViewById(R.id.button_start_pause);

        mButtonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = mEditTextInput.getText().toString();
                if (input.length() == 0) {
                    Toast.makeText(OutOfTimeActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                long millisInput = Long.parseLong(input) * 60000;
                if (millisInput == 0) {
                    Toast.makeText(OutOfTimeActivity.this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
                    return;
                }

                setTime(millisInput);
                mEditTextInput.setText("");
            }
        });

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {

                } else {
                    startTimer();
                    Toast.makeText(OutOfTimeActivity.this, "Tempo começou", Toast.LENGTH_SHORT).show();
                    int secondsDelayed = (int) (mTimeLeftInMillis / 1000) % 3600;
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            startActivity(new Intent(OutOfTimeActivity.this, LockedOutActivity.class));
                            finish();
                        }
                    }, secondsDelayed * 1000);
                    mButtonStartPause.setEnabled(false);

                }
            }
        });
    }

    private void setTime(long milliseconds) {
        mStartTimeInMillis = milliseconds;
        resetTimer();
        closeKeyboard();
    }

    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                updateWatchInterface();
            }
        }.start();

        mTimerRunning = true;
        updateWatchInterface();
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        updateWatchInterface();
    }

    private void resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
        updateWatchInterface();
    }

    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }

        mTextViewCountDown.setText(timeLeftFormatted);
    }

    private void updateWatchInterface() {
        if (mTimerRunning) {
            mEditTextInput.setVisibility(View.INVISIBLE);
            mButtonSet.setVisibility(View.INVISIBLE);
        } else {
            mEditTextInput.setVisibility(View.VISIBLE);
            mButtonSet.setVisibility(View.VISIBLE);
        }
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("startTimeInMillis", mStartTimeInMillis);
        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);

        editor.apply();

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        mStartTimeInMillis = prefs.getLong("startTimeInMillis", 600000);
        mTimeLeftInMillis = prefs.getLong("millisLeft", mStartTimeInMillis);
        mTimerRunning = prefs.getBoolean("timerRunning", false);

        updateCountDownText();
        updateWatchInterface();

        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();

            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
                updateWatchInterface();
            } else {
                startTimer();
            }
        }
    }
}
