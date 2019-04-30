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
import com.app.muhammadgamal.swapy.SpinnersLestiners.AccountSpinnerLestiner;
import com.app.muhammadgamal.swapy.SwapData.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangeAccount extends AppCompatActivity {

    private TextView changeAccount_cancel, changeAccount_save;
    private Spinner spinnerAccountEtisalat, spinnerAccountVodafoneArabic, spinnerAccountVodafoneUK, spinnerAccountArabicAccount;
    private FirebaseAuth mAuth;
    private DatabaseReference currentUserDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_account);

        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        spinnerAccountEtisalat =findViewById(R.id.spinnerAccountEtisalat);
        spinnerAccountVodafoneArabic = findViewById(R.id.spinnerAccountVodafoneArabic);
        spinnerAccountVodafoneUK = findViewById(R.id.spinnerAccountVodafoneUK);
        spinnerAccountArabicAccount = findViewById(R.id.spinnerAccountArabicAccount);
        showSpinner();
        accountSpinnerEtisalat();
        accountSpinnerVodafoneArabic();
        accountSpinnerVodafoneUK();
        accountSpinnerArabicAccount();

        changeAccount_cancel = findViewById(R.id.changeAccount_cancel);
        changeAccount_save = findViewById(R.id.changeAccount_save);

        changeAccount_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        changeAccount_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccountSpinnerLestiner.Account.equals("Choose account")) {
                    Toast.makeText(ChangeAccount.this, "Please choose account", Toast.LENGTH_SHORT).show();
                } else {
                    currentUserDb.child("mAccount").setValue(AccountSpinnerLestiner.Account)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ChangeAccount.this, "Account changed successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ChangeAccount.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
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


    private void showSpinner(){
        currentUserDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);
                    String branch = user.getmBranch();
                    if (branch.equals("اتصالات الامارات")){
                        spinnerAccountEtisalat.setVisibility(View.VISIBLE);
                    } else if (branch.equals("Vodafone uk")){
                        spinnerAccountVodafoneUK.setVisibility(View.VISIBLE);
                    } else if (branch.equals("Vodafone Arabic")){
                        spinnerAccountVodafoneArabic.setVisibility(View.VISIBLE);
                    } else if (branch.equals("Arabic Account")){
                        spinnerAccountArabicAccount.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
