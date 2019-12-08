package com.example.safeshelter;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ChooseParentalCode extends AppCompatActivity {
    public Button define_code;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

    //init
    EditText firstCode, secondCode;
    FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("SafeShelter");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_parental_code);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        define_code = findViewById(R.id.button_login);
        define_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstCode = findViewById(R.id.editText_firstCode);
                secondCode = findViewById(R.id.editText_secondCode);
                if(TextUtils.isEmpty(firstCode.getText())){
                    firstCode.setError("Enter parental code");
                    firstCode.setFocusable(true);
                } else if(TextUtils.isEmpty(secondCode.getText())){
                    secondCode.setError("Enter confirmation parental code");
                    secondCode.setFocusable(true);
                } else if(firstCode.getText().toString().equals(secondCode.getText().toString())){
                    HashMap<String, Object> result = new HashMap<>();
                    result.put("parentalCode", firstCode.getText().toString().trim());

                    databaseReference.child(user.getUid()).updateChildren(result)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ChooseParentalCode.this, "Parental Code defined...", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ChooseParentalCode.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                    startActivity(new Intent(ChooseParentalCode.this, MainMenuActivity.class));
                } else{
                    Toast.makeText(ChooseParentalCode.this, "Codes do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
