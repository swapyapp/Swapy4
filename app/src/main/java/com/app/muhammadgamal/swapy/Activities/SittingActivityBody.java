package com.app.muhammadgamal.swapy.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.muhammadgamal.swapy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SittingActivityBody extends AppCompatActivity {

    String getSittingType;

    private FirebaseAuth mAuth;
    private String currentUserId;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUserNameReference;

    TextView sitting_body_edit_text_title;
    EditText sitting_body_edit_text;

    int userNameIndicatorReceiver;
    int userPasswordIndicatorReceiver ;
    int userEmailIndicatorReceiver ;

    int receiver;

    String changeText;

    TextView saveButton;
    TextView cancelButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitting_body);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUserNameReference =  mFirebaseDatabase.getReference().child("Users").child(currentUserId).child("mUsername");


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



        Bundle bundle = getIntent().getExtras();

        assert bundle != null;
        userNameIndicatorReceiver = bundle.getInt("name");
        userPasswordIndicatorReceiver = bundle.getInt("password");
        userEmailIndicatorReceiver = bundle.getInt("email");

         receiver = 0;

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

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = sitting_body_edit_text.getText().toString().trim();
                        if(name.matches("")){
                            Toast.makeText(SittingActivityBody.this, "You didnt enter valid name", Toast.LENGTH_SHORT).show();
                    } else {
                            changeUserName(name);
                        }
                    }
                });

                break;
            case 2:
                sitting_body_edit_text_title.setText("What is your new Password");
                sitting_body_edit_text_title.setHint("Your password ");

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String password = sitting_body_edit_text.getText().toString().trim();
                        changeUserPassword(password);
                        FirebaseAuth.getInstance().signOut();
                        Intent signIn = new Intent(SittingActivityBody.this, SignInActivity.class);
                        startActivity(signIn);
                        finish();
                    }
                });

                break;
            case 3:
                sitting_body_edit_text_title.setText("Change your Email");
                sitting_body_edit_text_title.setHint("The new Email");

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email = sitting_body_edit_text.getText().toString().trim();
                        if(email.matches("")){
                            Toast.makeText(SittingActivityBody.this, "You didn't enter a valid email", Toast.LENGTH_SHORT).show();
                        } else {
                            changeUserEmail(email);
                        }
                    }
                });
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


    }

    private void changeUserName (String userName) {
        mUserNameReference.setValue(userName).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(SittingActivityBody.this, "Your username changed successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void changeUserPassword (String userPassword) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.updatePassword(userPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SittingActivityBody.this, "You have updated Your password successfully.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(SittingActivityBody.this, "Your password is updated successfully.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void changeUserEmail (String userEmail){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.updateEmail(userEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SittingActivityBody.this, "You have updated Your Email successfully.",
                                    Toast.LENGTH_SHORT).show();
                            Intent verifyActivity = new Intent(SittingActivityBody.this, VerifyActivity.class);
                            startActivity(verifyActivity);

                            FirebaseUser user = mAuth.getCurrentUser();
                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(SittingActivityBody.this, "Email sent.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        receiver = 0;
    }
}
