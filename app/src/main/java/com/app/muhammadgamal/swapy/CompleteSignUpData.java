package com.app.muhammadgamal.swapy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.muhammadgamal.swapy.Activities.NavDrawerActivity;
import com.app.muhammadgamal.swapy.Activities.SignUpActivity;
import com.app.muhammadgamal.swapy.Activities.VerifyActivity;
import com.app.muhammadgamal.swapy.SpinnersLestiners.AccountSpinnerLestiner;
import com.app.muhammadgamal.swapy.SpinnersLestiners.BranchSpinnerLestiner;
import com.app.muhammadgamal.swapy.SpinnersLestiners.CompanySpinnerLestiner;
import com.app.muhammadgamal.swapy.SpinnersLestiners.CurrentShiftSpinnerLestiner;
import com.app.muhammadgamal.swapy.SwapData.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CompleteSignUpData extends AppCompatActivity {

    EditText editTextPhone;
    Spinner spinnerCompany, spinnerCompanyBranch, spinnerAccount;
    private String phoneNumber, company, companyBranch, account, username, email, photoURL;

    private Button finishSignInBtn;

    private FirebaseAuth mAuth;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_sign_up_data);

        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        username = intent.getExtras().getString("Username");
        photoURL = intent.getExtras().getString("PhotoURL");
        email = intent.getExtras().getString("Email");

        editTextPhone = findViewById(R.id.editTextPhone_google);

        spinnerCompany = findViewById(R.id.spinnerCompany_google);
        spinnerCompanyBranch = findViewById(R.id.spinnerCompanyBranch_google);
        spinnerAccount = findViewById(R.id.spinnerAccount_google);

        finishSignInBtn = findViewById(R.id.finish_ggogle_signUp);
        finishSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInfoToFirebaseDatabase();
            }
        });

        companySpinner();
        accountSpinner();
        branchSpinner();


    }

    private void companySpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.company, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCompany.setAdapter(adapter);
        spinnerCompany.setOnItemSelectedListener(new CompanySpinnerLestiner());
    }

    private void branchSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.branch, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCompanyBranch.setAdapter(adapter);
        spinnerCompanyBranch.setOnItemSelectedListener(new BranchSpinnerLestiner());
    }

    private void accountSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.account, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAccount.setAdapter(adapter);
        spinnerAccount.setOnItemSelectedListener(new AccountSpinnerLestiner());
    }

    private void saveUserInfoToFirebaseDatabase() {


        String phoneNumber = editTextPhone.getText().toString().trim();
        String userId = mAuth.getCurrentUser().getUid();
        User user;
        DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        if (photoURL != null) {
            finishSignInBtn.setVisibility(View.GONE);
            user = new User(username, email, CompanySpinnerLestiner.company, BranchSpinnerLestiner.Branch, AccountSpinnerLestiner.Account, photoURL, phoneNumber);
            currentUserDb.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    finishSignInBtn.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(CompleteSignUpData.this, NavDrawerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    finishSignInBtn.setVisibility(View.VISIBLE);
                    Toast.makeText(CompleteSignUpData.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            finishSignInBtn.setVisibility(View.GONE);
            user = new User(username, email, CompanySpinnerLestiner.company, BranchSpinnerLestiner.Branch, AccountSpinnerLestiner.Account, null, phoneNumber);
            currentUserDb.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    finishSignInBtn.setVisibility(View.VISIBLE);
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    currentUser.sendEmailVerification();
                    Intent intent = new Intent(CompleteSignUpData.this, NavDrawerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    finishSignInBtn.setVisibility(View.VISIBLE);
                    Toast.makeText(CompleteSignUpData.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}
