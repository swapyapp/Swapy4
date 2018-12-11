package com.app.muhammadgamal.swapy.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.app.muhammadgamal.swapy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SittingActivityBody extends AppCompatActivity {

    String getSittingType;

    private FirebaseAuth mAuth;
    private String currentUserId;
    private DatabaseReference mDatabaseReference;

    private TextView sitting_body_edit_text_title;
    private EditText sitting_body_edit_text;

    String userNameIndicatorReceiver = "Change name";
    String userPasswordIndicatorReceiver = "Change password";
    String userEmailIndicatorReceiver = "Change email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitting_body);

        sitting_body_edit_text = findViewById(R.id.sitting_body_edit_text);
        sitting_body_edit_text_title.findViewById(R.id.sittings_body_edit_text_title);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);


        Intent intent = getIntent();



    }

    private void changeUserName (String userName) {

        mDatabaseReference.child("mUsername");
        mDatabaseReference.setValue(userName);
    }

    private void changeUserPassword (String userPassword) {
        mAuth.getCurrentUser().updatePassword(userPassword);
    }

    private void changeUseEmail (String userEmail){
        mAuth.getCurrentUser().updateEmail(userEmail);
    }

}
