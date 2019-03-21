package com.app.muhammadgamal.swapy.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SpinnersLestiners.BranchSpinnerLestiner;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangeBranch extends AppCompatActivity {

    TextView changeBranch_cancel, changeBranch_save;
    Spinner changeBranchSpinner;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_branch);

        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        final DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        changeBranch_cancel = findViewById(R.id.changeBranch_cancel);
        changeBranch_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        changeBranchSpinner = findViewById(R.id.changeBranchSpinner);
        branchSpinner();
        changeBranch_save = findViewById(R.id.changeBranch_save);
        changeBranch_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BranchSpinnerLestiner.Branch.equals("Choose branch")) {
                    Toast.makeText(ChangeBranch.this, "Please choose a branch", Toast.LENGTH_SHORT).show();
                } else {
                    currentUserDb.child("mCompany").setValue(BranchSpinnerLestiner.Branch)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ChangeBranch.this, "Company changed successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ChangeBranch.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    private void branchSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.branch, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        changeBranchSpinner.setAdapter(adapter);
        changeBranchSpinner.setOnItemSelectedListener(new BranchSpinnerLestiner());
    }
}
