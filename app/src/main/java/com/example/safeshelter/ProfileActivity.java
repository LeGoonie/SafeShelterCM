package com.example.safeshelter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    TextView mProfileTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Profile");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //init
        firebaseAuth = FirebaseAuth.getInstance();

        mProfileTv = findViewById(R.id.profileTv);
    }

    private void checkUserStatus(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
            //user is signed in stay here
            //set email of logged in user
            mProfileTv.setText(user.getEmail());
        } else{
            startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
            finish();
        }
    }

    @Override
    protected void onStart(){
        checkUserStatus();
        super.onStart();
    }
}
