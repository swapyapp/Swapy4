package com.app.muhammadgamal.swapy.UserSittings;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SpinnersLestiners.CompanyListinerAccount;
import com.app.muhammadgamal.swapy.SpinnersLestiners.CompanySpinnerLestiner;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangeCompany extends AppCompatActivity {

    TextView changeCompany_cancel, changeCompany_save;
    Spinner changeCompanySpinner;
    private FirebaseAuth mAuth;
    public static int COMPANY_CHOSEN = 0, BRANCH_CHOSEN = 0, ACCOUNT_CHOSEN = 0, CURRENT_SHIFT_CHOSEN = 0;
    public static int arrayBranch = R.array.branch;
    public static Spinner spinnerCompany,
            spinnerCompanyBranchRaya, spinnerCompanyBranchVodafone, spinnerCompanyBranchOrange,
            spinnerAccountEtisalat, spinnerAccountVodafoneArabic, spinnerAccountVodafoneUK, spinnerAccountArabicAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_company);

        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        final DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        changeCompany_cancel = findViewById(R.id.changeCompany_cancel);
        changeCompany_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        changeCompanySpinner = findViewById(R.id.changeCompanySpinner);
        companySpinner();
        changeCompany_save = findViewById(R.id.changeCompany_save);
        changeCompany_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CompanySpinnerLestiner.company.equals("Choose company")) {
                    Toast.makeText(ChangeCompany.this, "Please choose a company", Toast.LENGTH_SHORT).show();
                } else {
                    currentUserDb.child("mCompany").setValue(CompanySpinnerLestiner.company)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ChangeCompany.this, "Company changed successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ChangeCompany.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    private void companySpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.company, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        changeCompanySpinner.setAdapter(adapter);
        changeCompanySpinner.setOnItemSelectedListener(new CompanyListinerAccount());
    }
}
