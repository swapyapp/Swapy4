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
    private String phoneNumber, company, companyBranch, account;

    private Button finishSignInBtn;

    private FirebaseAuth mAuth;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_sign_up_data);

        editTextPhone = findViewById(R.id.editTextPhone_google);

        spinnerCompany = findViewById(R.id.spinnerCompany_google);
        spinnerCompanyBranch = findViewById(R.id.spinnerCompanyBranch_google);
        spinnerCompany = findViewById(R.id.spinnerCompany_google);

        finishSignInBtn = findViewById(R.id.finish_ggogle_signUp);

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
        spinnerAccount = findViewById(R.id.spinnerAccount);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.account, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAccount.setAdapter(adapter);
        spinnerAccount.setOnItemSelectedListener(new AccountSpinnerLestiner());
    }

    private void saveUserInfoToFirebaseDatabase() {

        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String username = firstName + " " + lastName;
        String phoneNumber = editTextPhone.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String userId = mAuth.getCurrentUser().getUid();
        User user;
        DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        if (profileImageUrl != null) {
            finishSignInBtn.setVisibility(View.GONE);
            user = new User(username, email, phoneNumber, CompanySpinnerLestiner.company, BranchSpinnerLestiner.Branch, AccountSpinnerLestiner.Account, CurrentShiftSpinnerLestiner.CurrentShift + AMorPM, profileImageUrl, 0, 0, 0);
            currentUserDb.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    signUpButton.setVisibility(View.VISIBLE);
                    USER_INFO_SAVED = 1;
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    currentUser.sendEmailVerification();
                    Intent intent = new Intent(SignUpActivity.this, VerifyActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    signUpButton.setVisibility(View.VISIBLE);
                    USER_INFO_SAVED = 0;
                    Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            finishSignInBtn.setVisibility(View.GONE);
            user = new User(firstName, email, phoneNumber, CompanySpinnerLestiner.company, BranchSpinnerLestiner.Branch, AccountSpinnerLestiner.Account, CurrentShiftSpinnerLestiner.CurrentShift, null, 0, 0, 0);
            currentUserDb.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    finishSignInBtn.setVisibility(View.VISIBLE);
                    USER_INFO_SAVED = 1;
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    currentUser.sendEmailVerification();
                    Intent intent = new Intent(SignUpActivity.this, VerifyActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    signUpButton.setVisibility(View.VISIBLE);
                    USER_INFO_SAVED = 0;
                    Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}
