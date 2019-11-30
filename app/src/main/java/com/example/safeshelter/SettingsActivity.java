package com.example.safeshelter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsActivity extends AppCompatActivity {
    public Preference profile_button;
    public static final String SHARED_PREFS = "sharedPrefs";
    FirebaseAuth firebaseAuth;

    private FirebaseAuth mAuth;
    private String user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        user_email = user.getEmail();
    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(user_email, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
    }
}
