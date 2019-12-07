package com.example.safeshelter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChangeParentalCode extends AppCompatActivity {
    public Button change_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_parental_code);

        change_code = findViewById(R.id.button_login);
        change_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangeParentalCode.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}
