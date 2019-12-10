package com.example.safeshelter;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //views from xml
    TextView nameTv, childrenNameTv, emailTv;
    FloatingActionButton fab;

    //progress dialog
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

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
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        //init values
        nameTv = findViewById(R.id.profileTv);
        childrenNameTv = findViewById(R.id.TextView_childrenName);
        emailTv = findViewById(R.id.TextView_email);
        fab = findViewById(R.id.fab);

        //init ProgressDialog
        pd = new ProgressDialog(this);

        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //check until required data get
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    if(ds.child("email").getValue().toString().equals(user.getEmail())){
                        //get data
                        String name = "" + ds.child("parentName").getValue().toString();
                        String childName = "Children Name: " + ds.child("childrenName").getValue().toString();
                        String email = "Email: " + ds.child("email").getValue().toString();

                        //set data
                        nameTv.setText(name);
                        childrenNameTv.setText(childName);
                        emailTv.setText(email);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                showEditProfileDialog();
            }
        });
    }

    private void showEditProfileDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Action");
        String options[] = {"Edit Parent Name","Edit Children Name","Edit email", "Edit Password"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0){
                    //Edit Parent Name
                    pd.setMessage("Updating Parent Name");
                    showNameEmailDialog("parentName");
                } else if(i == 1){
                    //Edit Children Name
                    pd.setMessage("Updating Children Name");
                    showNameEmailDialog("childrenName");

                } else if(i == 2){
                    //Edit email
                    pd.setMessage("Updating Email");
                    showNameEmailDialog("email");

                } else if(i == 3){
                    //Edit Password
                    pd.setMessage("Updating Password");
                    showChangePasswordDialog();
                }
            }
        });
        builder.create().show();
    }

    private void showNameEmailDialog(final String key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Update " + key);
        //set Layout of dialog
        LinearLayout linearLayout = new LinearLayout(ProfileActivity.this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10,10,10,10);
        //Add edit text
        final EditText editText = new EditText(ProfileActivity.this);
        editText.setHint("Enter " + key);
        linearLayout.addView(editText);

        builder.setView(linearLayout);

        //add update button
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //input text
                String value = editText.getText().toString().trim();
                if(!TextUtils.isEmpty(value)){
                    pd.show();
                    HashMap<String, Object> result = new HashMap<>();
                    result.put(key, value);

                    databaseReference.child(user.getUid()).updateChildren(result)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    pd.dismiss();
                                    Toast.makeText(ProfileActivity.this, "Updated...", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(ProfileActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } else{
                    Toast.makeText(ProfileActivity.this, "Please enter " + key, Toast.LENGTH_SHORT).show();
                }
            }
        });
        //add cancel button
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        //create and show dialog
        builder.create().show();
    }

    private void showChangePasswordDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Change password");
        //set Layout of dialog
        LinearLayout linearLayout = new LinearLayout(ProfileActivity.this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10,10,10,10);
        //Add edit text
        final EditText editText1 = new EditText(ProfileActivity.this);
        editText1.setHint("Enter Old Password");
        linearLayout.addView(editText1);
        final EditText editText2 = new EditText(ProfileActivity.this);
        editText2.setHint("Enter New Password");
        linearLayout.addView(editText2);

        builder.setView(linearLayout);

        //add update button
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //input text
                String oldPass = editText1.getText().toString();
                final String newPass = editText2.getText().toString();

                if(oldPass.equals(newPass)){
                    Toast.makeText(ProfileActivity.this, "New password cannot be the same as the previous", Toast.LENGTH_SHORT).show();
                } else if(oldPass.equals("")){
                    editText2.setError("Please enter old password");
                    editText2.setFocusable(true);
                } else if(newPass.equals("")){
                    editText2.setError("Please enter new password");
                    editText2.setFocusable(true);
                } else if(newPass.length() < 6){
                    editText2.setError("Password length at least 6 characters");
                    editText2.setFocusable(true);
                } else {
                    pd.show();
                    AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(),oldPass);

                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            pd.dismiss();
                                            Toast.makeText(ProfileActivity.this, "Password Changed", Toast.LENGTH_SHORT).show();
                                        }else {
                                            pd.dismiss();
                                            Toast.makeText(ProfileActivity.this, "Password Not Changed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else {
                                pd.dismiss();
                                Toast.makeText(ProfileActivity.this, "Password Not Changed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        //add cancel button
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        //create and show dialog
        builder.create().show();
    }
}
