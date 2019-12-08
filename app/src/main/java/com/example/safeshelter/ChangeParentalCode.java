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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ChangeParentalCode extends AppCompatActivity {
    public Button change_code;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

    //init
    EditText firstCode, secondCode;
    FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_parental_code);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        change_code = findViewById(R.id.button_login);
        change_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstCode = findViewById(R.id.editText_firstCode);
                secondCode = findViewById(R.id.editText_secondCode);

                Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //check until required data get
                        for (DataSnapshot ds : dataSnapshot.getChildren()){
                            if(TextUtils.isEmpty(firstCode.getText())){
                                firstCode.setError("Enter your parental code");
                                firstCode.setFocusable(true);
                            } else if(TextUtils.isEmpty(secondCode.getText())){
                                secondCode.setError("Enter new parental code");
                                secondCode.setFocusable(true);
                            } else if(!firstCode.getText().toString().equals(secondCode.getText().toString()) && ds.child("parentalCode").getValue().toString().equals(firstCode.getText().toString())){
                                HashMap<String, Object> result = new HashMap<>();
                                result.put("parentalCode", secondCode.getText().toString().trim());

                                databaseReference.child(user.getUid()).updateChildren(result)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(ChangeParentalCode.this, "Parental Code Changed...", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(ChangeParentalCode.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                startActivity(new Intent(ChangeParentalCode.this, MainMenuActivity.class));
                            } else{
                                Toast.makeText(ChangeParentalCode.this, "The new code is the same as the current one", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }
                });
            }
        });
    }
}
