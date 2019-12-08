package com.example.safeshelter;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class InsertParentalCode extends AppCompatActivity {
    private Button insert_button;
    private EditText password_field;
    private TextView back, clear;
    private TextView one, two, three, four, five, six, seven, eight, nine, zero;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("SafeShelter");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_parental_code);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        password_field = findViewById(R.id.password_field);

        insert_button = findViewById(R.id.button_login);
        insert_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //check until required data get
                        for (DataSnapshot ds : dataSnapshot.getChildren()){
                            if(ds.child("parentalCode").getValue().toString().equals(password_field.getText().toString())){
                                Intent intent = new Intent(InsertParentalCode.this, SettingsActivity.class);
                                startActivity(intent);
                            } else{
                                Toast.makeText(InsertParentalCode.this,"Incorrect Parental Code",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }
                });
            }
        });

        back = findViewById(R.id.t9_key_backspace);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editable editable = password_field.getText();
                int charCount = editable.length();
                if (charCount > 0) {
                    editable.delete(charCount - 1, charCount);
                }
            }
        });

        clear = findViewById(R.id.t9_key_clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password_field.setText(null);
            }
        });

        one = findViewById(R.id.t9_key_1);
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password_field.append("1");
            }
        });

        two = findViewById(R.id.t9_key_2);
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password_field.append("2");
            }
        });

        three = findViewById(R.id.t9_key_3);
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password_field.append("3");
            }
        });

        four = findViewById(R.id.t9_key_4);
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password_field.append("4");
            }
        });

        five = findViewById(R.id.t9_key_5);
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password_field.append("5");
            }
        });

        six = findViewById(R.id.t9_key_6);
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password_field.append("6");
            }
        });

        seven = findViewById(R.id.t9_key_7);
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password_field.append("7");
            }
        });

        eight = findViewById(R.id.t9_key_8);
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password_field.append("8");
            }
        });

        nine = findViewById(R.id.t9_key_9);
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password_field.append("9");
            }
        });

        zero = findViewById(R.id.t9_key_0);
        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password_field.append("0");
            }
        });
    }
}
