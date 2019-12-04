package com.miniapps.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

import com.example.safeshelter.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

        TextView mTextViewQuestionNumber;
        TextView mTextViewQuestion;
        ImageView mImageViewQuestion;
        FrameLayout mFrameLayoutAnswersArea;
        Button mButtonSubmit;
        EditText mEditText;
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
        int questionNumber = 0;
        int mUserScore = 0;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_quiz);

            // Find UI
            mTextViewQuestionNumber = (TextView) findViewById(R.id.text_view_question_number);
            mTextViewQuestion = (TextView) findViewById(R.id.text_view_question);
            mImageViewQuestion = (ImageView) findViewById(R.id.image_view_question_image);
            mFrameLayoutAnswersArea = (FrameLayout) findViewById(R.id.frame_answer_area);
            mButtonSubmit = (Button) findViewById(R.id.btn_submit);

            //get questions
            mQuestions = Question.getQuestions();

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
                        Toast.makeText(QuizActivity.this, "Your Score is " + mUserScore + "/9", Toast.LENGTH_SHORT).show();
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

            // Switch on question type to display the correct answers area
            switch (mQuestion.getType()) {
                case TEXT:
                    String userAnswer = mEditText.getText().toString().toLowerCase();
                    if (userAnswer.equals("")) {
                        Toast.makeText(this, "please enter an answer", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (userAnswer.equals(mQuestion.getCorrectAnswers()[0])) {
                        //Correct answer increment user score
                        Toast.makeText(this, "Correct Answer", Toast.LENGTH_SHORT).show();
                        mUserScore++;
                    }
                    break;
                case RADIO:
                    RadioButton radioButtonUserAnswer;
                    if (mRadioGroup.getCheckedRadioButtonId() != -1) {
                        radioButtonUserAnswer = (RadioButton) findViewById(mRadioGroup.getCheckedRadioButtonId());
                        if (radioButtonUserAnswer.getText().toString().toLowerCase().equals(mQuestion.getCorrectAnswers()[0]))
                            mUserScore++;
                    } else {
                        Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
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
                    for (CheckBox box : MyCheckBoxes) {
                        if (box.isChecked()) {
                            if (Arrays.asList(mQuestion.getCorrectAnswers()).contains(box.getText().toString().toLowerCase())) {
                                correctAnswer = true;
                            } else {
                                correctAnswer = false;
                                break;
                            }
                        }
                    }
                    if (correctAnswer)
                        mUserScore++;
                    break;
                default:
                    //Correct answer increment user score
                    Toast.makeText(this, "Error Undefined Question", Toast.LENGTH_SHORT).show();
                    break;
            }
            Toast.makeText(this, "score : " + mUserScore, Toast.LENGTH_SHORT).show();
            questionNumber++;
        }
}

