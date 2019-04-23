package com.app.muhammadgamal.swapy.UserSittings;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.muhammadgamal.swapy.ResgistrationActivities.VerifyActivity;
import com.app.muhammadgamal.swapy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserChangeEmail extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String currentUserId;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUserPhoneReference;

    private TextView sitting_body_edit_text_title_phone;
    private EditText sitting_body_edit_text;
    private TextView saveButton;
    private TextView cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_change_email);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUserPhoneReference =  mFirebaseDatabase.getReference().child("Users").child(currentUserId);


        sitting_body_edit_text_title_phone =(TextView) findViewById(R.id.sittings_body_edit_text_title_email);
        sitting_body_edit_text = (EditText) findViewById(R.id.sitting_body_edit_text_email);
        saveButton = (TextView)findViewById(R.id.sittings_body_save_email);
        cancelButton = (TextView)findViewById(R.id.sitting_body_cancel_email);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = sitting_body_edit_text.getText().toString().trim();
                if (email.isEmpty()) {
                    sitting_body_edit_text.setError("Email is required");
                    sitting_body_edit_text.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    sitting_body_edit_text.setError("Valid email  is required");
                    sitting_body_edit_text.requestFocus();
                    return;
                }
                else {
                    changeUserEmail(email);
                }
            }
        });


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                            Toast.makeText(UserChangeEmail.this, "You have updated Your Email successfully.",
                                    Toast.LENGTH_SHORT).show();
                            Intent verifyActivity = new Intent(UserChangeEmail.this, VerifyActivity.class);
                            startActivity(verifyActivity);

                            FirebaseUser user = mAuth.getCurrentUser();
                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(UserChangeEmail.this, "Email sent.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                });
    }
}
