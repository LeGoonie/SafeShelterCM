package com.example.safeshelter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {
    public Preference profile_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
