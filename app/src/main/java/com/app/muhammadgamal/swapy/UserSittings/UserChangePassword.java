package com.app.muhammadgamal.swapy.UserSittings;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.muhammadgamal.swapy.Activities.SignInActivity;
import com.app.muhammadgamal.swapy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserChangePassword extends AppCompatActivity {

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
        setContentView(R.layout.activity_user_change_password);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUserPhoneReference =  mFirebaseDatabase.getReference().child("Users").child(currentUserId);

        sitting_body_edit_text_title_phone =(TextView) findViewById(R.id.sittings_body_edit_text_title_password);
        sitting_body_edit_text = (EditText) findViewById(R.id.sitting_body_edit_text_password);
        saveButton = (TextView)findViewById(R.id.sittings_body_save_password);
        cancelButton = (TextView)findViewById(R.id.sitting_body_cancel_password);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = sitting_body_edit_text.getText().toString().trim();
                changeUserPassword(password);
                FirebaseAuth.getInstance().signOut();
                Intent signIn = new Intent(UserChangePassword.this, SignInActivity.class);
                startActivity(signIn);
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            Toast.makeText(UserChangePassword.this, "You have updated Your password successfully.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(UserChangePassword.this, "Your password is updated successfully.", Toast.LENGTH_SHORT).show();
            }
        });

    }
    }

