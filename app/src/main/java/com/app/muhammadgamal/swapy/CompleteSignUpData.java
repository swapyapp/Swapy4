package com.app.muhammadgamal.swapy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.app.muhammadgamal.swapy.SpinnersLestiners.AccountSpinnerLestiner;
import com.app.muhammadgamal.swapy.SpinnersLestiners.BranchSpinnerLestiner;
import com.app.muhammadgamal.swapy.SpinnersLestiners.CompanySpinnerLestiner;

public class CompleteSignUpData extends AppCompatActivity {

    EditText editTextPhone;
    Spinner spinnerCompany, spinnerCompanyBranch, spinnerAccount;

    String phoneNumber ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_sign_up_data);

        editTextPhone = findViewById(R.id.editTextPhone_google);

        spinnerCompany = findViewById(R.id.spinnerCompany_google);
        spinnerCompanyBranch = findViewById(R.id.spinnerCompanyBranch_google);
        spinnerCompany = findViewById(R.id.spinnerCompany_google);

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
}
