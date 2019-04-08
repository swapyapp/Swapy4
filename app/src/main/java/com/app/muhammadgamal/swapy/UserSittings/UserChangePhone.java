package com.app.muhammadgamal.swapy.UserSittings;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.muhammadgamal.swapy.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserChangePhone extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String currentUserId;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUserPhoneReference;

    private TextView sitting_body_edit_text_title_phone;
    private EditText sitting_body_edit_text;
    private TextView saveButton;
    private TextView cancelButton;
    String newPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_change_phone);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUserPhoneReference =  mFirebaseDatabase.getReference().child("Users").child(currentUserId).child("mPhoneNumber");


        sitting_body_edit_text_title_phone =(TextView) findViewById(R.id.sittings_body_edit_text_title_phone);
        sitting_body_edit_text = (EditText) findViewById(R.id.sitting_body_edit_text_phone);
        saveButton = (TextView)findViewById(R.id.sittings_body_save_phone);
        cancelButton = (TextView)findViewById(R.id.sitting_body_cancel_phone);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPhone = sitting_body_edit_text.getText().toString().trim();
                if (!newPhone.isEmpty()){
                    changePhone(newPhone);
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

    private void changePhone (String phone){
        mUserPhoneReference.setValue(phone).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(UserChangePhone.this, "Phone changed Successfully.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserChangePhone.this, "Failed to change phone number.", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
