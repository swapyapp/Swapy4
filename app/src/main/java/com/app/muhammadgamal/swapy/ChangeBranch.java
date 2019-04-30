package com.app.muhammadgamal.swapy;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.muhammadgamal.swapy.SpinnersLestiners.BranchSpinnerLestiner;
import com.app.muhammadgamal.swapy.SpinnersLestiners.ChangeBranchSpinnerLestiner;
import com.app.muhammadgamal.swapy.SwapData.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangeBranch extends AppCompatActivity {

    TextView changeBranch_cancel, changeBranch_save;
    private FirebaseAuth mAuth;
    private Spinner spinnerCompanyBranchRaya, spinnerCompanyBranchVodafone, spinnerCompanyBranchOrange;
    private DatabaseReference currentUserDb;
    private static int SPINNERNUM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_branch);

        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        changeBranch_cancel = findViewById(R.id.changeBranch_cancel);
        changeBranch_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        spinnerCompanyBranchRaya = findViewById(R.id.spinnerCompanyBranchRaya);
        spinnerCompanyBranchVodafone = findViewById(R.id.spinnerCompanyBranchVodafone);
        spinnerCompanyBranchOrange =findViewById(R.id.spinnerCompanyBranchOrange);
        showSpinner();
        branchSpinnerRaya();
        branchSpinnerVodafone();
        branchSpinnerOrange();
        changeBranch_save = findViewById(R.id.changeBranch_save);
        changeBranch_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ChangeBranchSpinnerLestiner.Branch.equals("Choose branch")) {
                    Toast.makeText(ChangeBranch.this, "Please choose a branch", Toast.LENGTH_SHORT).show();
                } else {
                    currentUserDb.child("mCompany").setValue(ChangeBranchSpinnerLestiner.Branch)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ChangeBranch.this, "Branch changed successfully", Toast.LENGTH_SHORT).show();
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

    private void showSpinner(){
        currentUserDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);
                    String company = user.getmCompany();
                    if (company.equals("Raya")){
                        SPINNERNUM = 1;
                        spinnerCompanyBranchRaya.setVisibility(View.VISIBLE);
                    } else if (company.equals("Vodafone")){
                        SPINNERNUM = 2;
                        spinnerCompanyBranchVodafone.setVisibility(View.VISIBLE);
                    } else if (company.equals("Orange")){
                        SPINNERNUM = 3;
                        spinnerCompanyBranchOrange.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void branchSpinnerRaya() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.branch_raya, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCompanyBranchRaya.setAdapter(adapter);
        spinnerCompanyBranchRaya.setOnItemSelectedListener(new ChangeBranchSpinnerLestiner());
    }
    public void branchSpinnerVodafone() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.branch_vodafone, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCompanyBranchVodafone.setAdapter(adapter);
        spinnerCompanyBranchVodafone.setOnItemSelectedListener(new ChangeBranchSpinnerLestiner());
    }
    public void branchSpinnerOrange() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.branch_orange, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCompanyBranchOrange.setAdapter(adapter);
        spinnerCompanyBranchOrange.setOnItemSelectedListener(new ChangeBranchSpinnerLestiner());
    }
}
