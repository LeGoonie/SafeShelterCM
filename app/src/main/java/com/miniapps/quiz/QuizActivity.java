package com.miniapps.quiz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.safeshelter.MainMenuActivity;
import com.example.safeshelter.R;

import org.w3c.dom.Text;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class QuizActivity extends AppCompatActivity {

        TextView mTextViewQuestionNumber;
        TextView mTextViewQuestion;
        TextView textView;
        ImageView mImageViewQuestion;
        FrameLayout mFrameLayoutAnswersArea;
        Button mButtonSubmit;
        EditText mEditText;
        ScrollView scroll;
        RadioGroup mRadioGroup;
        RadioButton mRadioButton1;
        RadioButton mRadioButton2;
        RadioButton mRadioButton3;
        RadioButton mRadioButton4;
        CheckBox mCheckBox1;
        CheckBox mCheckBox2;
        CheckBox mCheckBox3;
        CheckBox mCheckBox4;
        List<Question> mQuestions;
        ImageView imView;
        int questionNumber = 0;
        int mUserScore = 0;

        boolean currentFocus;
        boolean isPaused;
        Handler collapseNotificationHandler;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_quiz);
            scroll = (ScrollView) findViewById(R.id.scrollView);
            scroll.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scroll.fullScroll(ScrollView.FOCUS_UP);
                }
            }, 600);

            // Find UI
            mTextViewQuestionNumber = (TextView) findViewById(R.id.text_view_question_number);
            mTextViewQuestion = (TextView) findViewById(R.id.text_view_question);
            mImageViewQuestion = (ImageView) findViewById(R.id.image_view_question_image);
            mFrameLayoutAnswersArea = (FrameLayout) findViewById(R.id.frame_answer_area);
            mButtonSubmit = (Button) findViewById(R.id.btn_submit);

            //get questions
            mQuestions = Question.getQuestions();

            Collections.shuffle(mQuestions);

            // update ui with first question
            if (questionNumber < mQuestions.size()) {
                updateUiWithQuestion(mQuestions.get(questionNumber));
            }

            mButtonSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (questionNumber < mQuestions.size()) {
                        // grade current question
                        gradeQuestion(mQuestions.get(questionNumber));
                        // update ui with next question
                        if(questionNumber < mQuestions.size())
                            updateUiWithQuestion(mQuestions.get(questionNumber));
                    } else {
                        Toast.makeText(QuizActivity.this, "A tua pontuação é " + mUserScore + "/" +mQuestions.size(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


        public void updateUiWithQuestion(Question mQuestion) {
            // update UI with question name and image
            mTextViewQuestionNumber.setText((questionNumber + 1) + "/9");
            mTextViewQuestion.setText(mQuestion.getString());
            mImageViewQuestion.setImageResource(mQuestion.getImage());

            //layout inflater
            LayoutInflater inflater = LayoutInflater.from(this);
            mFrameLayoutAnswersArea.removeAllViews();

            // Switch on question type to display the correct answers area
            switch (mQuestion.getType()) {
                case TEXT:
                    View inflatedLayoutText = inflater.inflate(R.layout.answer_area_text, null, false);
                    mFrameLayoutAnswersArea.addView(inflatedLayoutText);
                    mEditText = (EditText) findViewById(R.id.edit_text);
                    break;
                case RADIO:
                    View inflatedLayoutRadio = inflater.inflate(R.layout.answer_area_radio, null, false);
                    mFrameLayoutAnswersArea.addView(inflatedLayoutRadio);
                    mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);
                    mRadioButton1 = (RadioButton) findViewById(R.id.radio_1);
                    mRadioButton2 = (RadioButton) findViewById(R.id.radio_2);
                    mRadioButton3 = (RadioButton) findViewById(R.id.radio_3);
                    mRadioButton4 = (RadioButton) findViewById(R.id.radio_4);
                    mRadioButton1.setText(mQuestion.getAnswers()[0]);
                    mRadioButton2.setText(mQuestion.getAnswers()[1]);
                    mRadioButton3.setText(mQuestion.getAnswers()[2]);
                    mRadioButton4.setText(mQuestion.getAnswers()[3]);
                    break;
                case CHECK:
                    View inflatedLayoutCheck = inflater.inflate(R.layout.answer_area_check, null, false);
                    mFrameLayoutAnswersArea.addView(inflatedLayoutCheck);
                    mCheckBox1 = (CheckBox) findViewById(R.id.checkbox_1);
                    mCheckBox2 = (CheckBox) findViewById(R.id.checkbox_2);
                    mCheckBox3 = (CheckBox) findViewById(R.id.checkbox_3);
                    mCheckBox4 = (CheckBox) findViewById(R.id.checkbox_4);
                    mCheckBox1.setText(mQuestion.getAnswers()[0]);
                    mCheckBox2.setText(mQuestion.getAnswers()[1]);
                    mCheckBox3.setText(mQuestion.getAnswers()[2]);
                    mCheckBox4.setText(mQuestion.getAnswers()[3]);
                    break;
                default:
                    break;
            }
        }

        public void gradeQuestion(Question mQuestion) {
            String tempVar = "errada";

            // Switch on question type to display the correct answers area
            switch (mQuestion.getType()) {
                case TEXT:
                    String userAnswer = mEditText.getText().toString().toLowerCase();
                    userAnswer = userAnswer.replaceAll("\\s", "");
                    Log.d("bla", "userAnswer: "+ userAnswer);
                    if (userAnswer.equals("")) {
                        Toast.makeText(this, "Introduz uma resposta", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (userAnswer.equals(mQuestion.getCorrectAnswers()[0].toLowerCase())) {
                        //Correct answer increment user score
                        Toast.makeText(this, "Resposta correta", Toast.LENGTH_SHORT).show();
                        mUserScore++;
                        tempVar = "certa";
                    }
                    break;
                case RADIO:
                    RadioButton radioButtonUserAnswer;
                    if (mRadioGroup.getCheckedRadioButtonId() != -1) {
                        radioButtonUserAnswer = (RadioButton) findViewById(mRadioGroup.getCheckedRadioButtonId());
                        Log.d("Check", ""+radioButtonUserAnswer.getText().toString());
                        if (radioButtonUserAnswer.getText().toString().toLowerCase().equals(mQuestion.getCorrectAnswers()[0].toLowerCase())) {
                            mUserScore++;
                            tempVar = "certa";
                        }
                    } else {
                        Toast.makeText(this, "Seleciona uma resposta", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    break;
                case CHECK:
                    boolean correctAnswer = false;
                    List<CheckBox> MyCheckBoxes = new ArrayList<>();
                    MyCheckBoxes.add(mCheckBox1);
                    MyCheckBoxes.add(mCheckBox2);
                    MyCheckBoxes.add(mCheckBox3);
                    MyCheckBoxes.add(mCheckBox4);
                    MyCheckBoxes.size();
                    List<String> userAnswers = new ArrayList<>();
                    for (CheckBox box : MyCheckBoxes) {
                        if (box.isChecked()) {
                            userAnswers.add(box.getText().toString().toLowerCase());
                        }
                    }
                    if(userAnswers.contains(mQuestion.getCorrectAnswers()[0].toLowerCase()) && userAnswers.contains(mQuestion.getCorrectAnswers()[1].toLowerCase())) {
                        correctAnswer = true;
                    }
                    else {
                        correctAnswer = false;
                    }

                    if (correctAnswer) {
                        mUserScore++;
                        tempVar = "certa";
                    }
                    break;
                default:
                    //Correct answer increment user score
                    Toast.makeText(this, "Erro pergunta não definida", Toast.LENGTH_SHORT).show();
                    break;
            }
            Toast.makeText(this, "A resposta está " + tempVar, Toast.LENGTH_SHORT).show();
            questionNumber++;

            if(questionNumber == mQuestions.size()){
                mFrameLayoutAnswersArea.removeAllViews();
                mImageViewQuestion.setVisibility(View.GONE);
                mButtonSubmit.setVisibility(View.GONE);
                mTextViewQuestion.setVisibility(View.GONE);
                textView = new TextView(this);
                String scoreString = "Score: " + mUserScore;
                textView.setText(scoreString);
                textView.setTextSize(48);
                textView.setTextColor(Color.parseColor("#ED2939"));
                textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
                FrameLayout.LayoutParams paramsText = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                paramsText.setMargins(310, 400, 0, 200);
                textView.setLayoutParams(paramsText);
                imView = new ImageView(this);
                imView.setImageResource(R.drawable.sair_kids);
                imView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(QuizActivity.this, MainQuizActivity.class);
                        startActivity(intent);
                    }
                });
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(100, 900, 100, 152);
                imView.setLayoutParams(params);
                mFrameLayoutAnswersArea.addView(textView);
                mFrameLayoutAnswersArea.addView(imView);

            }
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

    @Override
    protected void onPause() {
        super.onPause();
        isPaused = true;

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);

        activityManager.moveTaskToFront(getTaskId(), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPaused = false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Do nothing or catch the keys you want to block
        return false;
    }
}

