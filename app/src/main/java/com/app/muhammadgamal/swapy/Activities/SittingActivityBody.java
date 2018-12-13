package com.app.muhammadgamal.swapy.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

    TextView sitting_body_edit_text_title;
    EditText sitting_body_edit_text;

    int userNameIndicatorReceiver;
    int userPasswordIndicatorReceiver ;
    int userEmailIndicatorReceiver ;

    String changeText;

    TextView saveButton;
    TextView cancelButton;

    String name;
    String password;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitting_body);

        sitting_body_edit_text_title =(TextView) findViewById(R.id.sittings_body_edit_text_title);
        sitting_body_edit_text = (EditText) findViewById(R.id.sitting_body_edit_text);

        saveButton = (TextView)findViewById(R.id.sittings_body_save);
        cancelButton = (TextView)findViewById(R.id.sitting_body_cancel);


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sitting_body_edit_text.setTextColor(Color.WHITE);

        Bundle bundle = getIntent().getExtras();

        assert bundle != null;
        userNameIndicatorReceiver = bundle.getInt("name");
        userPasswordIndicatorReceiver = bundle.getInt("password");
        userEmailIndicatorReceiver = bundle.getInt("email");

        int receiver = 0;

        if (userNameIndicatorReceiver == 1){
            receiver = userNameIndicatorReceiver;

        }else if (userPasswordIndicatorReceiver == 2){
            receiver = userPasswordIndicatorReceiver;

        }else if (userEmailIndicatorReceiver == 3){
            receiver = userEmailIndicatorReceiver;

        }

        switch (receiver){
            case 1:
                sitting_body_edit_text_title.setText("What do you want to be called");
                sitting_body_edit_text_title.setHint("Your name ");

                changeText = sitting_body_edit_text.getText().toString();
                name = changeText;

                receiver = 0;
                break;
            case 2:
                sitting_body_edit_text_title.setText("What is your new Password");
                sitting_body_edit_text_title.setHint("Your password ");

                changeText = sitting_body_edit_text.getText().toString();
                password = changeText;

                receiver = 0;
                break;
            case 3:
                sitting_body_edit_text_title.setText("Change your Email");
                sitting_body_edit_text_title.setHint("The new Email");

                changeText = sitting_body_edit_text.getText().toString();
                email = changeText;

                receiver = 0;
                break;
        }


//        if (userNameIndicatorReceiver == 1){
//            sitting_body_edit_text_title.setText("What do you want to be called");
//            sitting_body_edit_text_title.setHint("Your name ");
//        }
//        if (userPasswordIndicatorReceiver == 2){
//            sitting_body_edit_text_title.setText("What is your new Password");
//            sitting_body_edit_text_title.setHint("Your password ");
//        }
//
//        if (userEmailIndicatorReceiver == 3){
//            sitting_body_edit_text_title.setText("Change your Email");
//            sitting_body_edit_text_title.setHint("The new Email");
//        }

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

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
