package com.app.muhammadgamal.swapy.ResgistrationActivities;

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
import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SpinnersLestiners.AccountSpinnerLestiner;
import com.app.muhammadgamal.swapy.SpinnersLestiners.BranchSpinnerLestiner;
import com.app.muhammadgamal.swapy.SpinnersLestiners.BranchSpinnerLestinerComplete;
import com.app.muhammadgamal.swapy.SpinnersLestiners.CompanySpinnerLestiner;
import com.app.muhammadgamal.swapy.SpinnersLestiners.CompanySpinnerLestinerCompleteData;
import com.app.muhammadgamal.swapy.SwapData.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class CompleteSignUpData extends AppCompatActivity {

    EditText editTextPhone;
    public static Spinner spinnerCompany,
            spinnerCompanyBranchRaya, spinnerCompanyBranchVodafone, spinnerCompanyBranchOrange,
            spinnerAccountEtisalat, spinnerAccountVodafoneArabic, spinnerAccountVodafoneUK, spinnerAccountArabicAccount;
    private String phoneNumber, company, companyBranch, account, username, email, photoURL;
    public static int arrayBranch = R.array.branch;

    public static int COMPANY_CHOSEN_COMPLETEDATA = 0, BRANCH_CHOSEN = 0, ACCOUNT_CHOSEN = 0, CURRENT_SHIFT_CHOSEN = 0;

    private Button finishSignInBtn;
    private DatabaseReference deviceTokenRef;
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

        spinnerCompany = findViewById(R.id.spinnerCompany);
        spinnerCompanyBranchRaya = (Spinner) findViewById(R.id.spinnerCompanyBranchRaya);
        spinnerCompanyBranchVodafone = findViewById(R.id.spinnerCompanyBranchVodafone);
        spinnerCompanyBranchOrange = findViewById(R.id.spinnerCompanyBranchOrange);

        spinnerAccountEtisalat = findViewById(R.id.spinnerAccountEtisalat);
        spinnerAccountVodafoneArabic = findViewById(R.id.spinnerAccountVodafoneArabic);
        spinnerAccountVodafoneUK = findViewById(R.id.spinnerAccountVodafoneUK);
        spinnerAccountArabicAccount = findViewById(R.id.spinnerAccountArabicAccount);

        companySpinner();
        branchSpinnerRaya();
        branchSpinnerVodafone();
        branchSpinnerOrange();
        accountSpinnerEtisalat();
        accountSpinnerVodafoneArabic();
        accountSpinnerVodafoneUK();
        accountSpinnerArabicAccount();

        finishSignInBtn = findViewById(R.id.finish_ggogle_signUp);
        finishSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInfoToFirebaseDatabase();
            }
        });

    }

    private void companySpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.company, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCompany.setAdapter(adapter);
        spinnerCompany.setOnItemSelectedListener(new CompanySpinnerLestinerCompleteData());
    }

    public void branchSpinnerRaya() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.branch_raya, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCompanyBranchRaya.setAdapter(adapter);
        spinnerCompanyBranchRaya.setOnItemSelectedListener(new BranchSpinnerLestinerComplete());
    }

    public void branchSpinnerVodafone() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.branch_vodafone, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCompanyBranchVodafone.setAdapter(adapter);
        spinnerCompanyBranchVodafone.setOnItemSelectedListener(new BranchSpinnerLestinerComplete());
    }

    public void branchSpinnerOrange() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.branch_orange, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCompanyBranchOrange.setAdapter(adapter);
        spinnerCompanyBranchOrange.setOnItemSelectedListener(new BranchSpinnerLestinerComplete());
    }

    private void accountSpinnerEtisalat() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.account_Etisalat_Emarat, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAccountEtisalat.setAdapter(adapter);
        spinnerAccountEtisalat.setOnItemSelectedListener(new AccountSpinnerLestiner());
    }

    private void accountSpinnerVodafoneArabic() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.account_Vodafone_arabic, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAccountVodafoneArabic.setAdapter(adapter);
        spinnerAccountVodafoneArabic.setOnItemSelectedListener(new AccountSpinnerLestiner());
    }

    private void accountSpinnerVodafoneUK() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.account_vodafone_uk, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAccountVodafoneUK.setAdapter(adapter);
        spinnerAccountVodafoneUK.setOnItemSelectedListener(new AccountSpinnerLestiner());
    }

    private void accountSpinnerArabicAccount() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.account_arabic_account, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAccountArabicAccount.setAdapter(adapter);
        spinnerAccountArabicAccount.setOnItemSelectedListener(new AccountSpinnerLestiner());
    }

    private void saveUserInfoToFirebaseDatabase() {


        String phoneNumber = editTextPhone.getText().toString().trim();
        String userId = mAuth.getCurrentUser().getUid();
        User user;
        DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        if (photoURL != null) {
            finishSignInBtn.setVisibility(View.GONE);
            user = new User(username, email, spinnerCompany.getSelectedItem().toString(), BranchSpinnerLestinerComplete.Branch, AccountSpinnerLestiner.Account, photoURL, phoneNumber);
            currentUserDb.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    finishSignInBtn.setVisibility(View.VISIBLE);
                    //Set the user Device token if he signed in using google
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    String currentUserID = mAuth.getCurrentUser().getUid();
                    deviceTokenRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);
                    String deviceToken = FirebaseInstanceId.getInstance().getToken();
                    deviceTokenRef.child("device_token").setValue(deviceToken);

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
                    //Set the user Device token if he signed in using google
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    String currentUserID = mAuth.getCurrentUser().getUid();
                    deviceTokenRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);
                    String deviceToken = FirebaseInstanceId.getInstance().getToken();
                    deviceTokenRef.child("device_token").setValue(deviceToken);

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
