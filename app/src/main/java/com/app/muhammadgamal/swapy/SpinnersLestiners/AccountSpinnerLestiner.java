package com.app.muhammadgamal.swapy.SpinnersLestiners;

import android.view.View;
import android.widget.AdapterView;

import com.app.muhammadgamal.swapy.ResgistrationActivities.SignUpActivity;

public class AccountSpinnerLestiner implements AdapterView.OnItemSelectedListener {

    public static String Account;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getItemAtPosition(position).equals("Choose account")){
            //do nothing
            SignUpActivity.ACCOUNT_CHOSEN = 1;
        } else {
            SignUpActivity.ACCOUNT_CHOSEN = 0;
            Account = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}