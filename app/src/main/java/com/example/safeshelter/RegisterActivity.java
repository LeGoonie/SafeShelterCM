package com.example.safeshelter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    EditText mTextUsername;
    EditText mTextPassword;
    EditText mTextCnfPassword;
    Button mButtonRegister;
    TextView mTextViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("SafeShelter");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTextUsername = (EditText)findViewById(R.id.editText_username);
        mTextPassword = (EditText)findViewById(R.id.editText_password);
        mTextCnfPassword = (EditText)findViewById(R.id.editText_cnf_password);
        mButtonRegister = (Button)findViewById(R.id.button_login);
        mTextViewLogin = (TextView)findViewById(R.id.textView_register);
        mTextViewLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(RegisterActivity.this, HomeActivity.class);
                startActivity(registerIntent);
            }
        });
    }
}
