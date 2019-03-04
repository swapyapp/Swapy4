package com.app.muhammadgamal.swapy.SpinnersLestiners;

import android.view.View;
import android.widget.AdapterView;

import com.app.muhammadgamal.swapy.Activities.SignUpActivity;

public class BranchSpinnerLestiner implements AdapterView.OnItemSelectedListener {

    public static String Branch;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getItemAtPosition(position).equals("Choose branch")){
            //do nothing
            SignUpActivity.BRANCH_CHOSEN = 1;
            Branch = parent.getItemAtPosition(position).toString();
        } else {
            SignUpActivity.BRANCH_CHOSEN = 0;
            Branch = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}